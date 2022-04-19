package cn.aaron911.netty.im.protocol.response;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.session.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_RESPONSE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadNoticeResponsePacket extends Packet {

    private Session fromSession;

    private String md5Hex;

    private String fileName;

    private Long fileSize;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_RESPONSE;
    }
}
