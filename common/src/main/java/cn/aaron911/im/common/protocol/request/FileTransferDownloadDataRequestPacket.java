package cn.aaron911.im.common.protocol.request;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_DATA_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadDataRequestPacket extends ImPacket {

    /**
     * 客户端文件地址
     */
    private String fileUrl;

    /**
     * 文件名称
     */
    private String fileName;

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
    private Integer status;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_DATA_REQUEST;
    }
}
