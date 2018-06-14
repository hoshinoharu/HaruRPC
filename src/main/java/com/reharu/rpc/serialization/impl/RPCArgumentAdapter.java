package com.reharu.rpc.serialization.impl;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.reharu.rpc.dto.RPCObjectWrapper;

import java.io.IOException;

/**
 * 自定义的 RPC参数 json 转换器
 */
public class RPCArgumentAdapter extends TypeAdapter<RPCObjectWrapper> {
    private Gson gson = new Gson();

    //写入时 将类名 和对象json写入
    @Override
    public void write(JsonWriter out, RPCObjectWrapper value) throws IOException {
        out.beginObject();
        out.name("argCls").value(value.getArgCls().getName());
        out.name("value").value(gson.toJson(value.getValue()));
        out.endObject();
    }

    //读取时 反射获取类名 拿到value的json 进行转换
    @Override
    public RPCObjectWrapper read(JsonReader in) throws IOException {
        Class argCls = null;
        String argVal = null;
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "argCls":
                    String clsName = in.nextString();
                    //针对基础数据类型做处理
                    switch (clsName) {
                        case "byte":
                            argCls = byte.class;
                            break;
                        case "short":
                            argCls = short.class;
                            break;
                        case "int":
                            argCls = int.class;
                            break;
                        case "long":
                            argCls = long.class;
                            break;
                        case "float":
                            argCls = float.class;
                            break;
                        case "double":
                            argCls = double.class;
                            break;
                        case "boolean":
                            argCls = boolean.class;
                            break;
                        default:
                            try {
                                argCls = Class.forName(clsName);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();

                            }
                    }
                    break;
                case "value":
                    argVal = in.nextString();
                    break;
            }
        }
        RPCObjectWrapper argument = null;
        if (argCls != null && argVal != null) {
            argument = new RPCObjectWrapper();
            argument.setArgCls(argCls);
            argument.setValue(gson.fromJson(argVal, argCls));
        }
        in.endObject();
        return argument;
    }
}
