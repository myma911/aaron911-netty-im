package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_UPLOAD_REQUEST;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferUploadRequestPacket  extends Packet {

    private String toUserId;

    /**
     * 客户端文件地址
     */
    private String fileUrl;

    private String fileName;

    private Long fileSize;

    private String md5Hex;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_REQUEST;
    }
}
