package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.QUIT_GROUP_RESPONSE;


@Data
public class QuitGroupResponsePacket extends ImPacket {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
