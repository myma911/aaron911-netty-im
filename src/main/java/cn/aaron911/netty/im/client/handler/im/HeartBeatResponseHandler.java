package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.HEARTBEAT_RESPONSE;


public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> implements ICommand {

    public static final HeartBeatResponseHandler INSTANCE = new HeartBeatResponseHandler();

    private HeartBeatResponseHandler() {}


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket heartBeatResponsePacket) {

    }

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}