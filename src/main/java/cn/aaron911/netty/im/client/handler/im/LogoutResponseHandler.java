package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.response.LogoutResponsePacket;
import cn.aaron911.netty.im.util.session.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.LOGOUT_RESPONSE;


public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> implements ICommand {

    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();

    private LogoutResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        if (logoutResponsePacket.isSuccess()) {
            SessionUtil.unBindSession(ctx.channel());
            // 退出结束客户端
            System.exit(0);
        }
        else{
            System.out.println("退出失败，原因：" + logoutResponsePacket.getReason());
        }
    }

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
