package cn.aaron911.im.client.handler.im;

import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.im.common.protocol.command.Command.JOIN_GROUP_RESPONSE;


public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> implements ICommand {

    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    protected JoinGroupResponseHandler() {
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket responsePacket) {
        if (responsePacket.isSuccess()) {
            System.out.println("加入群[" + responsePacket.getGroupId() + "]成功!");
        } else {
            System.err.println("加入群[" + responsePacket.getGroupId() + "]失败，原因为：" + responsePacket.getReason());
        }
    }

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
