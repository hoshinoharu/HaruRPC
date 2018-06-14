package com.reharu.rpc.dto;

/**
 * RPC调用结果
 */
public class RPCResult {

    /**
     * 返回的对象
     */
    private RPCObjectWrapper result;

    public RPCResult(Object object) {
        this.result = new RPCObjectWrapper(object);
    }

    public RPCObjectWrapper getResult() {
        return result;
    }

    public void setResult(RPCObjectWrapper result) {
        this.result = result;
    }
}
