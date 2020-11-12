package cn.aaron911.netty.im.client.handler;

import cn.aaron911.netty.im.client.handler.im.*;
import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.protocol.response.*;
import cn.aaron911.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class ClientImHandler extends SimpleChannelInboundHandler<Packet> {

    public static final ClientImHandler INSTANCE = new ClientImHandler();

    private Map<Class, SimpleChannelInboundHandler<? extends Packet>> handlerMap;


    private ClientImHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(HeartBeatResponsePacket.class, HeartBeatResponseHandler.INSTANCE);
        handlerMap.put(CreateGroupResponsePacket.class, CreateGroupResponseHandler.INSTANCE);
        handlerMap.put(LoginResponsePacket.class, LoginResponseHandler.INSTANCE);
        handlerMap.put(MessageResponsePacket.class, MessageResponseHandler.INSTANCE);
        handlerMap.put(JoinGroupResponsePacket.class, JoinGroupResponseHandler.INSTANCE);
        handlerMap.put(QuitGroupResponsePacket.class, QuitGroupResponseHandler.INSTANCE);
        handlerMap.put(ListGroupMembersResponsePacket.class, ListGroupMembersResponseHandler.INSTANCE);
        handlerMap.put(GroupMessageResponsePacket.class, GroupMessageResponseHandler.INSTANCE);
        handlerMap.put(LogoutResponsePacket.class, LogoutResponseHandler.INSTANCE);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        final Class<? extends Packet> packetClass = packet.getClass();
        final SimpleChannelInboundHandler<? extends Packet> simpleChannelInboundHandler = handlerMap.get(packetClass);
        if (null == simpleChannelInboundHandler) {
            System.out.println("暂未实现的数据包处理");
            return;
        }
        simpleChannelInboundHandler.channelRead(ctx, packet);
    }

    /**
     * 客户端溢出退出
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
            SessionUtil.unBindSession(channel);
        }
    }
}
