package cn.aaron911.im.common.protocol.request;


import cn.aaron911.im.common.protocol.ImPacket;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.GROUP_MESSAGE_REQUEST;


@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends ImPacket {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
