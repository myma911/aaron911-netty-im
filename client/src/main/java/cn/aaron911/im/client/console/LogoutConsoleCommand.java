package cn.aaron911.im.client.console;

import cn.aaron911.im.common.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

import static cn.aaron911.im.common.protocol.command.Command.LOGOUT_REQUEST;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
