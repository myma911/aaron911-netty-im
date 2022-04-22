package cn.aaron911.im.common.protocol.request;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.persistence.ImFileState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadRequestPacket extends ImPacket {

    private String md5Hex;

    /**
     * 客户端文件路径（目录）
     */
    private String clientFileUrl;

    /**
     * 0开始、1中间、2结尾、3完成
     */
    private ImFileState status;

    /**
     * 读取位置
     */
    private Integer readPosition;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_REQUEST;
    }
}
