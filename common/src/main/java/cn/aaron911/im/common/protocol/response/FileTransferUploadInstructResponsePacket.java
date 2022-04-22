package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_UPLOAD_INSTRUCT_RESPONSE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferUploadInstructResponsePacket extends ImPacket {

    /**
     * 0开始、1中间、2结尾、3完成
     */
    private Integer status;

    /**
     * 客户端文件URL
     */
    private String clientFileUrl;

    /**
     * 读取位置
     */
    private Integer readPosition;


    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_INSTRUCT_RESPONSE;
    }
}
