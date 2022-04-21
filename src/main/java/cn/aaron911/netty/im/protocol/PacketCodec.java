package cn.aaron911.netty.im.protocol;

import cn.aaron911.netty.im.serialize.ImSerializer;
import cn.aaron911.netty.im.serialize.SerializerAlgorithm;
import cn.aaron911.netty.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * <pre>
 * **********************************************************************
 *                                Protocol
 * +-------+----------+------------+----------+---------+---------------+
 * |       |          |            |          |         |               |
 * |   4   |     1    |     1      |    1     |    4    |       N       |
 * +--------------------------------------------------------------------+
 * |       |          |            |          |         |               |
 * | magic |  version | serializer | command  |  length |      body     |
 * |       |          |            |          |         |               |
 * +-------+----------+------------+----------+---------+---------------+
 * 消息头11个字节定长
 * = 4 // 魔数,magic = (int) 0x12345678
 * + 1 // 版本号,通常情况下时预留字段,用于协议升级的时候用到.
 * + 1 // 序列化算法,如何把Java对象转换二进制数据已经二进制数据如何转换回Java对象
 * + 1 // 指令
 * + 4 // 数据部分的长度,int类型
 * </pre>
 *
 */
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap = new HashMap<>();
    private final Map<Byte, ImSerializer> serializerMap = new HashMap<>();



    private PacketCodec() {
        Reflections reflections = new Reflections("cn.aaron911.netty.im.protocol.request");
        handleReflectionss(reflections);

        reflections = new Reflections("cn.aaron911.netty.im.protocol.response");
        handleReflectionss(reflections);

        ImSerializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    private void handleReflectionss(Reflections reflections) {
        Set<Class<? extends Packet>> subTypes = reflections.getSubTypesOf(Packet.class);
        for (Class<? extends Packet> clazz : subTypes) {
            try {
                Packet instance = clazz.newInstance();
                Byte command = instance.getCommand();
                packetTypeMap.put(command, clazz);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte serializerAlgorithm = SerializerAlgorithm.JSON;
        ImSerializer serializer = serializerMap.get(serializerAlgorithm);
        // 1. 序列化 java 对象
        byte[] bytes = serializer.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(serializerAlgorithm);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // magic number
        byte[] magicNumber = new byte[4];
        byteBuf.readBytes(magicNumber);
        // 版本号
        byte version = byteBuf.readByte();
        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> responseClazz = getRequestType(command);
        ImSerializer serializer = getSerializer(serializeAlgorithm);

        if (responseClazz != null && serializer != null) {
            return serializer.deserialize(responseClazz, bytes);
        }

        return null;
    }

    private ImSerializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }


    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
