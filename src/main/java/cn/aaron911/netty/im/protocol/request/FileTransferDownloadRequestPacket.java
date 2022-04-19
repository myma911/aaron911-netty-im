package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.session.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadRequestPacket extends Packet {

    private String md5Hex;

    /**
     * 客户端文件路径（目录）
     */
    private String clientFileUrl;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_REQUEST;
    }
}
