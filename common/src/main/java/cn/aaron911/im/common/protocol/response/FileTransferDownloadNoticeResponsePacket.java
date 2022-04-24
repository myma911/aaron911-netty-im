package cn.aaron911.im.common.protocol.response;

import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.session.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_NOTICE_RESPONSE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTransferDownloadNoticeResponsePacket extends ImPacket {

    private Session fromSession;

    private String md5Hex;

    private String fileName;

    private Long fileSize;

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_NOTICE_RESPONSE;
    }
}
