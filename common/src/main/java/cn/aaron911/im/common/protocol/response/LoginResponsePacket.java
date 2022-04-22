package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.session.Session;
import lombok.Data;

import static cn.aaron911.im.common.protocol.command.Command.LOGIN_RESPONSE;


@Data
public class LoginResponsePacket extends ImPacket {

    private Session session;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
