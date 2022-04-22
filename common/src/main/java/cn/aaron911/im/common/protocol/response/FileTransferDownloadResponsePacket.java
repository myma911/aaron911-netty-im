package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.persistence.ImFileState;
import cn.aaron911.im.common.util.session.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_RESPONSE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadResponsePacket extends ImPacket {

    private Boolean fileExist = true;

    private Session fromSession;

    private String md5Hex;

    private String fileName;

    private Long fileSize;

    /**
     * 开始位置
     */
    private Integer beginPos;

    /**
     * 结束位置
     */
    private Integer endPos;

    /**
     * 文件字节；再实际应用中可以使用非对称加密，以保证传输信息安全
     */
    private byte[] bytes;

    /**
     * 0开始、1中间、2结尾、3完成
     */
    private ImFileState status;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_RESPONSE;
    }
}
