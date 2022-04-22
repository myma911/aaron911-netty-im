package cn.aaron911.im.server.handler.im;


import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.request.JoinGroupRequestPacket;
import cn.aaron911.im.common.protocol.response.JoinGroupResponsePacket;
import cn.aaron911.im.common.util.session.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import static cn.aaron911.im.common.protocol.command.Command.JOIN_GROUP_REQUEST;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> implements ICommand {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.writeAndFlush(responsePacket);
    }

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
