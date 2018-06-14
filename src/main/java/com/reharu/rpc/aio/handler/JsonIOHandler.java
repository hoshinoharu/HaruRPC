package com.reharu.rpc.aio.handler;

import com.reharu.rpc.aio.callback.IOCallback;
import com.reharu.rpc.serialization.impl.RPCInvokeSerizlizer;
import com.reharu.rpc.serialization.impl.nio.BufferJsonSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public abstract class JsonIOHandler<I, O> extends IOHandler<I, O> {

    private BufferJsonSerializer<I> inputSerializer;

    private BufferJsonSerializer<O> outputSerializer;

    private Type inputCls;

    private Type outputCls;

    public JsonIOHandler(AsynchronousSocketChannel channel, IOCallback<I, O> callback) {
        super(channel, callback);
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] arguments = parameterizedType.getActualTypeArguments();
        inputCls = arguments[0];
        outputCls = arguments[1];

        inputSerializer = new RPCInvokeSerizlizer<>(inputCls);

        outputSerializer = new RPCInvokeSerizlizer<>(outputCls);
    }

    @Override
    protected I readFromBuffer(ByteBuffer buffer) {
        return inputSerializer.readFromBuffer(buffer);
    }

    @Override
    protected ByteBuffer writeToBuffer(O output) {
        return outputSerializer.writeToBuffer(output);
    }
}
