package cn.aaron911.im.common.protocol.response;


import cn.aaron911.im.common.protocol.ImPacket;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends ImPacket {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
