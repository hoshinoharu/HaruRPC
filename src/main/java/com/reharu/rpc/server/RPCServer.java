package com.reharu.rpc.server;

import com.reharu.rpc.aio.callback.IOCallback;
import com.reharu.rpc.dto.RPCCommand;
import com.reharu.rpc.dto.RPCObjectWrapper;
import com.reharu.rpc.dto.RPCResult;
import com.reharu.rpc.aio.handler.IOHandler;
import com.reharu.rpc.aio.handler.ServerIOHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * RPC服务端
 */
public class RPCServer implements IOCallback<RPCCommand, RPCResult> {
    //监听端口
    private int port;


//    private ServerIOHandler ioHandler;

    public RPCServer(int port) {
        this.port = port;
    }

    /**
     * 绑定本地端口开始监听
     */
    public void startListen() throws IOException {
        // 创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(20);
        // 以指定线程池来创建一个AsynchronousChannelGroup
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
                .withThreadPool(executor);
        // 以指定线程池来创建一个AsynchronousServerSocketChannel
        AsynchronousServerSocketChannel serverChannel
                = AsynchronousServerSocketChannel.open(channelGroup)
                // 指定监听本机的PORT端口
                .bind(new InetSocketAddress(port));
        // 使用CompletionHandler接受来自客户端的连接请求
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                ServerIOHandler serverIOHandler = new ServerIOHandler(result, RPCServer.this);
                serverIOHandler.read();
                //继续接受客户端调用
                serverChannel.accept(null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });  //①
    }

    //缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(1024);


    @Override
    public void onReadCompleted(IOHandler<RPCCommand, RPCResult> handler) {
        RPCCommand command;
        command = handler.getReadData();
        Class invokeCls = RPCMappingFactory.classByName(command.getInvokeClsName());
        List<RPCObjectWrapper> argList = command.getArgs();

        RPCObjectWrapper[] args = new RPCObjectWrapper[argList == null ? 0 : argList.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = argList.get(i);
        }

        Object result = RPCMappingFactory.invoke(RPCMappingFactory.objectByClass(invokeCls), invokeCls, command.getMethodName(), args);

        handler.write(new RPCResult(result));

        handler.read();
    }
}
