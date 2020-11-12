package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.response.LogoutResponsePacket;
import cn.aaron911.netty.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();

    private LogoutResponseHandler() {
    }

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
}
