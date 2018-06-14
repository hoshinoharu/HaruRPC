package com.reharu.rpc.serialization.impl.nio;

import com.reharu.rpc.serialization.impl.JsonSerializer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BufferJsonSerializer<O> extends JsonSerializer<O> {

    public BufferJsonSerializer(Type paramType) {
        super(paramType);
    }

    /***
     从buffer中读取对象
     */
    public O readFromBuffer(ByteBuffer buffer) {
        String content = StandardCharsets.UTF_8
                .decode(buffer).toString();
        return parse(content);
    }

    /**
     * 将对象写入Buffer中
     */
    public ByteBuffer writeToBuffer(O obj) {
        String json = serialize(obj);
        ByteBuffer buffer = null;
        try {
            buffer = ByteBuffer.wrap(json.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
