package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.session.Session;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.MESSAGE_RESPONSE;


@Data
public class MessageResponsePacket extends ImPacket {

    private Session from;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
