package cn.aaron911.netty.im.client.console;

import cn.aaron911.netty.im.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_REQUEST;

public class MessageConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个用户，请输入用户id：");
        String toUserId = scanner.next();
        System.out.print("发送消息给某个用户,请输入消息内容：");
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
