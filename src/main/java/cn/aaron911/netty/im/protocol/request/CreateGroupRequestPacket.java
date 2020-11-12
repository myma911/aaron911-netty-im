package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;

import lombok.Data;

import java.util.List;

import static cn.aaron911.netty.im.protocol.command.Command.CREATE_GROUP_REQUEST;


@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
