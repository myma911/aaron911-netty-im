package cn.aaron911.im.common.protocol.request;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.LOGOUT_REQUEST;


@Data
public class LogoutRequestPacket extends ImPacket {

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
