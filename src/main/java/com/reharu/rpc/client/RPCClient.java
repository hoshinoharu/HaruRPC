package com.reharu.rpc.client;

import com.reharu.rpc.aio.callback.IOCallback;
import com.reharu.rpc.aio.callback.RPCInvokeCallback;
import com.reharu.rpc.dto.RPCCommand;
import com.reharu.rpc.dto.RPCInvoke;
import com.reharu.rpc.dto.RPCResult;
import com.reharu.rpc.aio.handler.ClientIOHandler;
import com.reharu.rpc.aio.handler.IOHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCClient implements IOCallback<RPCResult, RPCCommand> {

    private String ip;

    private int port;

    private ClientIOHandler ioHandler;

    private boolean connected = false;

    /**
     * 调用队列
     */
    private ConcurrentLinkedQueue<RPCInvoke> invokeQueue = new ConcurrentLinkedQueue<>();

    /**
     * 当前的调用
     */
    private RPCInvoke curInvoke;


    public RPCClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /***
     *连接服务器
     */
    public void connect() throws IOException, ExecutionException, InterruptedException {
        if (!connected) {

            AsynchronousSocketChannel clientChannel;
            // 创建一个线程池
            ExecutorService executor = Executors.newFixedThreadPool(80);
            // 以指定线程池来创建一个AsynchronousChannelGroup
            AsynchronousChannelGroup channelGroup =
                    AsynchronousChannelGroup.withThreadPool(executor);
            // 以channelGroup作为组管理器来创建AsynchronousSocketChannel
            clientChannel = AsynchronousSocketChannel.open(channelGroup);
            // 让AsynchronousSocketChannel连接到指定IP、指定端口
            clientChannel.connect(new InetSocketAddress(ip, port)).get();

            ioHandler = new ClientIOHandler(clientChannel, this);

            connected = true;
        }
    }

    @Override
    public void onReadCompleted(IOHandler<RPCResult, RPCCommand> handler) {
        RPCResult readData = handler.getReadData();
        curInvoke.callback.onResult(readData);
        invokeQueue.poll();
        //如果队列中还有调用请求 继续发送
        if (!invokeQueue.isEmpty()) {
            sendInvoke();
        }
    }

    public void invoke(RPCCommand command, RPCInvokeCallback callback) {
        RPCInvoke invoke = new RPCInvoke();
        invoke.command = command;
        invoke.callback = callback;
        //添加进入调用队列
        if (invokeQueue.add(invoke)) {
            //如果当前调用队列只有一个
            if (invokeQueue.size() <= 1) {
                sendInvoke();
            }
        }
    }

    /**
     * 发送队列的第一个调用请求
     */
    private void sendInvoke() {
        curInvoke = invokeQueue.peek();
        sendCmd(curInvoke.command);
    }

    private void sendCmd(RPCCommand command) {
        ioHandler.write(command);
        ioHandler.read();
    }

}
