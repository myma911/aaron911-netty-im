package cn.aaron911.im.common.protocol.request;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.JOIN_GROUP_REQUEST;


@Data
public class JoinGroupRequestPacket extends ImPacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
