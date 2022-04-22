package cn.aaron911.im.client.handler.im;


import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.response.LoginResponsePacket;
import cn.aaron911.im.common.util.session.Session;
import cn.aaron911.im.common.util.session.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.im.common.protocol.command.Command.LOGIN_RESPONSE;


public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> implements ICommand {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    protected LoginResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        String userId = loginResponsePacket.getSession().getUserId();
        String userName = loginResponsePacket.getSession().getUserName();
        if (loginResponsePacket.isSuccess()) {
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
