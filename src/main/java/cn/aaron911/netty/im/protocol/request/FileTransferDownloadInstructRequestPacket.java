package cn.aaron911.netty.im.protocol.request;

import cn.aaron911.netty.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_INSTRUCT_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadInstructRequestPacket extends Packet {

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
        return FILE_TRANSFER_DOWNLOAD_INSTRUCT_REQUEST;
    }
}
