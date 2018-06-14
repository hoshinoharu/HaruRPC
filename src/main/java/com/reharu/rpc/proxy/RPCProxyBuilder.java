package com.reharu.rpc.proxy;

import com.reharu.rpc.client.RPCClient;

import java.lang.reflect.Proxy;

public class RPCProxyBuilder {
    public static <T> T createProxy(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new RPCClientProxy(new RPCClient("127.0.0.1", 3839), cls));
    }
}
