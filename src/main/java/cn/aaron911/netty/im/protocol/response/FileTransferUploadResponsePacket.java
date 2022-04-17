package cn.aaron911.netty.im.protocol.response;

import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.session.Session;
import cn.aaron911.netty.im.util.persistence.ImFileState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_UPLOAD_RESPONSE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferUploadResponsePacket extends Packet {

    /**
     * 0开始、1中间、2结尾、3完成
     */
    private ImFileState status;

    /**
     * 文件路径
     */
    private String fileUrl;

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
