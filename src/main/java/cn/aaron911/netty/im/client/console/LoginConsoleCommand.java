package cn.aaron911.netty.im.client.console;

import cn.aaron911.netty.im.protocol.request.LoginRequestPacket;
import cn.aaron911.netty.im.util.session.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

import static cn.aaron911.netty.im.protocol.command.Command.LOGIN_REQUEST;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse(channel);
    }

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

    private static void waitForLoginResponse(Channel channel) {
        while (!SessionUtil.hasLogin(channel)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
