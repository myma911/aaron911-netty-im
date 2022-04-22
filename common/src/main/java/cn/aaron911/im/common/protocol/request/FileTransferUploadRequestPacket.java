package cn.aaron911.im.common.protocol.request;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_UPLOAD_REQUEST;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferUploadRequestPacket  extends ImPacket {

    private String toUserId;

    /**
     * 客户端文件地址目录
     */
    private String clientFileDir;

    private String fileName;

    private Long fileSize;

    private String md5Hex;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_REQUEST;
    }
}
