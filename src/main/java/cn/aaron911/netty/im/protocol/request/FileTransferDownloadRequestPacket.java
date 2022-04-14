package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.session.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadRequestPacket extends Packet {

    private String fromUserId;

    private Session fromSession;

    private String fileUrl;

    private String fileName;

    private Long fileSize;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_REQUEST;
    }
}
