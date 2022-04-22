package cn.aaron911.im.common.protocol.response;


import cn.aaron911.im.common.protocol.ImPacket;

import static cn.aaron911.im.common.protocol.command.Command.HEARTBEAT_RESPONSE;


public class HeartBeatResponsePacket extends ImPacket {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
