package cn.aaron911.im.server.handler.im;


import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.request.LogoutRequestPacket;
import cn.aaron911.im.common.protocol.response.LogoutResponsePacket;
import cn.aaron911.im.common.util.session.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.im.common.protocol.command.Command.LOGOUT_REQUEST;

@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> implements ICommand {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.writeAndFlush(logoutResponsePacket);
    }

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
