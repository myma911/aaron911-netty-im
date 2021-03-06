package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.response.GroupMessageResponsePacket;
import cn.aaron911.netty.im.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    private GroupMessageResponseHandler() {
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket responsePacket) {
        String fromGroupId = responsePacket.getFromGroupId();
        Session fromSession = responsePacket.getFromSession();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromSession.getUserName() + "]发来的消息：" + responsePacket.getMessage());
    }
}
