package cn.aaron911.netty.im.protocol.response;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.session.Session;
import lombok.Data;

import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_RESPONSE;


@Data
public class MessageResponsePacket extends Packet {

    private Session from;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
