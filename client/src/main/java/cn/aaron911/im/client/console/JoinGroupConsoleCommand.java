package cn.aaron911.im.client.console;

import cn.aaron911.im.common.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

import static cn.aaron911.im.common.protocol.command.Command.JOIN_GROUP_REQUEST;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        System.out.print("输入 groupId，加入群聊：");
        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
