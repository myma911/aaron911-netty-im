package cn.aaron911.netty.im.protocol.response;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.session.Session;
import lombok.Data;

import static cn.aaron911.netty.im.protocol.command.Command.LOGIN_RESPONSE;


@Data
public class LoginResponsePacket extends Packet {

    private Session session;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
