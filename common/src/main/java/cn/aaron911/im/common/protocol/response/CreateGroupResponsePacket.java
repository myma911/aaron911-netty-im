package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static cn.aaron911.im.common.protocol.command.Command.CREATE_GROUP_RESPONSE;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupResponsePacket extends ImPacket {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
