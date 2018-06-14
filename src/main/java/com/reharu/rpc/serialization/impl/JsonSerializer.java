package com.reharu.rpc.serialization.impl;

import com.google.gson.Gson;
import com.reharu.rpc.serialization.base.StringSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonSerializer<O> extends StringSerializer<O> {

    private Gson gson = getGson();

    private Type paramType ;

    public JsonSerializer() {
        paramType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0] ;
    }

    public JsonSerializer(Type paramType) {
        this.paramType = paramType;
    }

    //将对象序列化成Json
    @Override
    public String serialize(O obj) {
        return gson.toJson(obj);
    }

    //将Json序列化为对象
    @Override
    public O parse(String s) {
        return gson.fromJson(s, paramType);
    }

    protected Gson getGson() {
        return new Gson() ;
    }
}
