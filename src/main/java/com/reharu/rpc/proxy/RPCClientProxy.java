package com.reharu.rpc.proxy;

import com.reharu.rpc.aio.callback.RPCInvokeCallback;
import com.reharu.rpc.client.RPCClient;
import com.reharu.rpc.dto.RPCCommand;
import com.reharu.rpc.dto.RPCResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RPCClientProxy implements InvocationHandler, RPCInvokeCallback {

    private RPCClient rpcClient;

    private Class invokeCls;

    private RPCResult result;

    public RPCClientProxy(RPCClient rpcClient, Class invokeCls) {
        this.rpcClient = rpcClient;
        this.invokeCls = invokeCls;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //同步锁
        synchronized (rpcClient) {
            rpcClient.connect();
            rpcClient.invoke(new RPCCommand(invokeCls, method, args), this);
            rpcClient.wait();
        }
        return result.getResult().getValue();
    }

    @Override
    public void onResult(RPCResult result) {
        synchronized (rpcClient) {
            this.result = result;
            rpcClient.notifyAll();
        }
    }
}
