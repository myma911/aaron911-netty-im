package cn.aaron911.netty.im.serialize.impl;

import cn.aaron911.netty.im.serialize.Serializer;
import cn.aaron911.netty.im.serialize.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;



public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
