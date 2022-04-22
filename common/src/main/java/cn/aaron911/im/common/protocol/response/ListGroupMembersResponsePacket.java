package cn.aaron911.im.common.protocol.response;


import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.session.Session;
import lombok.Data;

import java.util.List;

import static cn.aaron911.im.common.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;



@Data
public class ListGroupMembersResponsePacket extends ImPacket {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
