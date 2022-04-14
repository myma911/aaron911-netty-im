package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_INSTRUCT_REQUEST;

public class FileTransferInstructRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_INSTRUCT_REQUEST;
    }
}
