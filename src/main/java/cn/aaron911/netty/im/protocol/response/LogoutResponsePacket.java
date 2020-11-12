package cn.aaron911.netty.im.protocol.response;


import cn.aaron911.netty.im.protocol.Packet;
import lombok.Data;

import static cn.aaron911.netty.im.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
