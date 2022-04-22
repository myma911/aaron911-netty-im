package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.persistence.ImFileState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_UPLOAD_RESPONSE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferUploadResponsePacket extends ImPacket {

    /**
     * 0开始、1中间、2结尾、3完成
     */
    private ImFileState status;

    /**
     * 客户端文件目录
     */
    private String clientFileDir;

    private String fileName;

    /**
     * 读取位置
     */
    private Integer readPosition;

    private String md5Hex;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_RESPONSE;
    }
}
