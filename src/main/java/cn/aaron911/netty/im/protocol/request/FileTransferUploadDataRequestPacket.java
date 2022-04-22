package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.persistence.ImFileState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_UPLOAD_DATA_REQUEST;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferUploadDataRequestPacket extends Packet {

    private String md5Hex;

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
    private ImFileState status;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_DATA_REQUEST;
    }
}
