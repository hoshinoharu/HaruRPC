package com.reharu.rpc.server;

import com.reharu.rpc.dto.RPCObjectWrapper;
import com.reharu.rpc.test.service.UserService;
import com.reharu.rpc.test.service.UserServiceImpl;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 类名映射工厂
 */
public class RPCMappingFactory {

    private static HashMap<String, Object> mapper = new HashMap<>();

    static {
        mapper.put(UserService.class.getName(), new UserServiceImpl());
    }

    /**
     * 根据接口类名返回实现接口的对象
     */
    public static Object objectByClass(Class cls) {
        return mapper.get(cls.getName());
    }

    public static Class classByName(String clsName) {
        try {
            return Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用方法
     *
     * @param executor    真正执行方法的对象
     * @param invokeClass 调用的类
     * @param methodName  方法名
     * @param args        方法执行参数
     * @return 返回调用结果
     */
    public static Object invoke(Object executor, Class invokeClass, String methodName, RPCObjectWrapper... args) {
        Class[] argTypes = new Class[args == null ? 0 : args.length];
        Object[] argValues = new Object[args == null ? 0 : args.length];
        //将参数的类型和值提取出来
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                argTypes[i] = args[i].getArgCls();
                argValues[i] = args[i].getValue();
            }
        }
        try {
            Method method = invokeClass.getMethod(methodName, argTypes);
            return method.invoke(executor, argValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
