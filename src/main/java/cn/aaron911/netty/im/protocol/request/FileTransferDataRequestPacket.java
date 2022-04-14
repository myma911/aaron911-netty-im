package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DATA_REQUEST;
import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_INSTRUCT_REQUEST;

public class FileTransferDataRequestPacket extends Packet {



    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DATA_REQUEST;
    }
}
