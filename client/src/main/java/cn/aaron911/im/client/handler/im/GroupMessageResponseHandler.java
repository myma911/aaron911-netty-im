package cn.aaron911.im.client.handler.im;

import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.response.GroupMessageResponsePacket;
import cn.aaron911.im.common.util.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.im.common.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> implements ICommand {

    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    private GroupMessageResponseHandler() {}


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket responsePacket) {
        String fromGroupId = responsePacket.getFromGroupId();
        Session fromSession = responsePacket.getFromSession();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromSession.getUserName() + "]发来的消息：" + responsePacket.getMessage());
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
