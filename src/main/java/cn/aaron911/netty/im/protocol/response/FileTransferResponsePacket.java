package cn.aaron911.netty.im.protocol.response;

import cn.aaron911.netty.im.protocol.Packet;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_REQUEST;
import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_RESPONSE;

public class FileTransferResponsePacket extends Packet {

    

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_RESPONSE;
    }
}
