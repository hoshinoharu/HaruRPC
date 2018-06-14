package com.reharu.rpc.serialization.base;

/**
 * 序列化器
 *
 * @param <Out> 序列化后的对象类型
 */
public interface Serializer<S, O> {

    /***
     * 序列化
     * @param obj 需要序列化的对象
     * @return 返回序列化后的对象
     */
    S serialize(O obj);

    /**
     * 反序列化
     *
     * @param s 序列化后对象
     * @return 反序列化后的对象
     */

    O parse(S s);
}
