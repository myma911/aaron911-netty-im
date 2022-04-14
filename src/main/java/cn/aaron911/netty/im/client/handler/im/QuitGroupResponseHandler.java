package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.QUIT_GROUP_RESPONSE;


public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> implements ICommand {

    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    private QuitGroupResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket responsePacket) {
        if (responsePacket.isSuccess()) {
            System.out.println("退出群聊[" + responsePacket.getGroupId() + "]成功！");
        } else {
            System.out.println("退出群聊[" + responsePacket.getGroupId() + "]失败！");
        }

    }

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
