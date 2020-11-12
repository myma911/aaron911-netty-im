package cn.aaron911.netty.im.codec;


import cn.aaron911.netty.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 基于长度域拆包器LengthFieldBasedFrameDecoder:最通用的一种拆包器,只要自定义协议包含长度域字段均使用此拆包器实现应用层拆包.
 * 使用LengthFieldBasedFrameDecoder需要长度域相对整个数据包的偏移量和长度域的长度构造拆包器,Pipeline最前面添加此拆包器
 *
 * 拒绝非本协议连接:数据包的开头加上魔数尽早屏蔽非本协议的客户端,
 * 通常放在第一个Handler处理此逻辑,每个客户端发过来的数据包都做一次快速判断,判断当前发来的数据包是否是满足自定义协议,
 * 需要继承LengthFieldBasedFrameDecoder覆盖decode()方法,调用decode()方法之前判断前四个字节是否是等于魔数0x12345678.
 * 基于 Netty自带的拆包器在拆包之前判断当前连上来的客户端是否是支持自定义协议的客户端,如果不支持尽早关闭连接节省资源.
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
