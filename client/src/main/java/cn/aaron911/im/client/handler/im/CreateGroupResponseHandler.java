package cn.aaron911.im.client.handler.im;

import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.im.common.protocol.command.Command.CREATE_GROUP_RESPONSE;


public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> implements ICommand {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    protected CreateGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) {
        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());
    }

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
