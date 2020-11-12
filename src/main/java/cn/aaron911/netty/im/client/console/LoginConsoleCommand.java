package cn.aaron911.netty.im.client.console;

import cn.aaron911.netty.im.protocol.request.LoginRequestPacket;
import cn.aaron911.netty.im.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

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

    private static void waitForLoginResponse(Channel channel) {
        while (!SessionUtil.hasLogin(channel)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
