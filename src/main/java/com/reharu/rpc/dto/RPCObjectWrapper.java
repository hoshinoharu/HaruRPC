package com.reharu.rpc.dto;

public class RPCObjectWrapper {

    private Class argCls;

    private Object value;

    public RPCObjectWrapper() {
    }

    public RPCObjectWrapper(Class argCls, Object value) {
        this.argCls = argCls;
        this.value = value;
    }

    public RPCObjectWrapper(Object value) {
        if(value == null){
            this.argCls = Void.class ;
        }else {
            this.argCls = value.getClass();
        }
            this.value = value;
    }

    public Class getArgCls() {
        return argCls;
    }

    public void setArgCls(Class argCls) {
        this.argCls = argCls;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
