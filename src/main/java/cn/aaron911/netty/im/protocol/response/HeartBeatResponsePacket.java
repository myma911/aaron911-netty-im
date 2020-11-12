package cn.aaron911.netty.im.protocol.response;


import cn.aaron911.netty.im.protocol.Packet;


import static cn.aaron911.netty.im.protocol.command.Command.HEARTBEAT_RESPONSE;


public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
