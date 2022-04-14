package cn.aaron911.netty.im.client.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.response.MessageResponsePacket;
import cn.aaron911.netty.im.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_RESPONSE;


public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> implements ICommand {
    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();

    protected MessageResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
        final Session fromSession = messageResponsePacket.getFrom();
        String fromUserId = fromSession.getUserId();
        String fromUserName = fromSession.getUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + messageResponsePacket.getMessage());
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
