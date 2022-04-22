package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.JOIN_GROUP_RESPONSE;


@Data
public class JoinGroupResponsePacket extends ImPacket {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
