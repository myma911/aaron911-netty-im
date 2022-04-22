package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.session.Session;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.GROUP_MESSAGE_RESPONSE;


@Data
public class GroupMessageResponsePacket extends ImPacket {

    private String fromGroupId;

    private Session fromSession;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
