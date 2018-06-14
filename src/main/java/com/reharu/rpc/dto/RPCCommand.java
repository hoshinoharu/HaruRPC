package com.reharu.rpc.dto;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RPCCommand {

    private String methodName;

    private List<RPCObjectWrapper> args;

    private String invokeClsName;

    /**
     * 根据调用方法和参数来创建RPC调用命令
     *
     * @param method
     * @param args
     */
    public RPCCommand(Class invokeCls, Method method, Object[] args) {
        this.invokeClsName = invokeCls.getName();
        this.methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        /**
         * 将参数列表的类型赋值上
         */
        if (args != null && args.length == parameterTypes.length) {
            this.args = new ArrayList<>();
            for(int i = 0; i < args.length; i++){
                this.args.add(new RPCObjectWrapper(parameterTypes[i], args[i]));
            }
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<RPCObjectWrapper> getArgs() {
        return args;
    }

    public void setArgs(List<RPCObjectWrapper> args) {
        this.args = args;
    }

    public String getInvokeClsName() {
        return invokeClsName;
    }

    public void setInvokeClsName(String invokeClsName) {
        this.invokeClsName = invokeClsName;
    }
}
