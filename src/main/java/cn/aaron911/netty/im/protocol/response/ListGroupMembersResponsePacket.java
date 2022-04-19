package cn.aaron911.netty.im.protocol.response;


import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.session.Session;
import lombok.Data;
import java.util.List;

import static cn.aaron911.netty.im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;



@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
