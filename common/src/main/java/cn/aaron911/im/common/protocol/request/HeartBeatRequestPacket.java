package cn.aaron911.im.common.protocol.request;


import cn.aaron911.im.common.protocol.ImPacket;

import static cn.aaron911.im.common.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends ImPacket {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
