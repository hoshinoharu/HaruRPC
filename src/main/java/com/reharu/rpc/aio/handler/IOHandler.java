package com.reharu.rpc.aio.handler;

import com.reharu.rpc.aio.callback.IOCallback;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * 读取频道输入输出的处理器
 *
 * @param <I> 输入对象类型
 * @param <O> 输出对象类型
 */
public abstract class IOHandler<I, O> implements CompletionHandler<Integer, Object> {
    /**
     * 操作的频道
     */
    private AsynchronousSocketChannel channel;

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    /**
     * io时的回调
     */
    private IOCallback<I, O> callback;

    public IOHandler(AsynchronousSocketChannel channel, IOCallback<I, O> callback) {
        this.channel = channel;
        this.callback = callback;
    }

    @Override
    public void completed(Integer result, Object attachment) {

        if (callback != null) {

            callback.onReadCompleted(this);
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {

    }

    /**
     * 开始从频道中读取数据到缓存区中
     */
    public void read() {
        buffer.clear() ;
        channel.read(buffer, null, this);
    }

    /**
     * 向缓冲区中写入数据
     *
     * @param output
     */
    public void write(O output) {
        try {
            channel.write(writeToBuffer(output)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从缓冲区获取数据
     *
     * @return
     */
    public I getReadData() {
        buffer.flip();
        return readFromBuffer(buffer);
    }

    protected abstract I readFromBuffer(ByteBuffer buffer);

    protected abstract ByteBuffer writeToBuffer(O output);
}
