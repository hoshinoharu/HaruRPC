package com.reharu.rpc.serialization.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reharu.rpc.dto.RPCObjectWrapper;
import com.reharu.rpc.serialization.impl.nio.BufferJsonSerializer;

import java.lang.reflect.Type;

public class RPCInvokeSerizlizer<O> extends BufferJsonSerializer<O> {

    public RPCInvokeSerizlizer(Type paramType) {
        super(paramType);
    }

    @Override
    protected Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(RPCObjectWrapper.class, new RPCArgumentAdapter()).create();
    }
}
