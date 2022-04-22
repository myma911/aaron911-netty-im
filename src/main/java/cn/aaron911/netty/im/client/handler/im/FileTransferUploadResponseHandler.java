package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadDataRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.netty.im.util.Constant;
import cn.aaron911.netty.im.util.persistence.ImFileState;
import cn.aaron911.netty.im.util.persistence.ImFileUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_UPLOAD_RESPONSE;


public class FileTransferUploadResponseHandler extends SimpleChannelInboundHandler<FileTransferUploadResponsePacket> implements ICommand {

    public static final FileTransferUploadResponseHandler INSTANCE = new FileTransferUploadResponseHandler();

    protected FileTransferUploadResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferUploadResponsePacket fileTransferUploadResponse) throws Exception {
        ImFileState status = fileTransferUploadResponse.getStatus();
        if (null == status) {
            System.out.println("异常了");
            return;
        }
        Channel channel = ctx.channel();
        FileTransferUploadDataRequestPacket fileTransferUploadDataRequestPacket = new FileTransferUploadDataRequestPacket();
        String clientFileDir = fileTransferUploadResponse.getClientFileDir();
        String fileName = fileTransferUploadResponse.getFileName();
        String clientFullFilePath = clientFileDir + fileName;
        fileTransferUploadDataRequestPacket.setMd5Hex(fileTransferUploadResponse.getMd5Hex());
        fileTransferUploadDataRequestPacket.setFileName(fileName);

        switch (status){
            case BEGIN:
            case CENTER:
            case END:
                boolean exist = FileUtil.exist(clientFullFilePath);
                if (!exist) {
                    System.out.println(StrUtil.format("本地文件【{}】不存在，结束。", clientFullFilePath));
                    return;
                }
                File file = FileUtil.file(clientFullFilePath);
                if (file.isDirectory()) {
                    System.out.println("暂不支持发送文件目录，结束。");
                    return;
                }
                Integer beginPos = fileTransferUploadResponse.getReadPosition();
                byte[] bytes = new byte[Constant.BUFF_SIZE];
                int readSize = ImFileUtil.readFile(file, beginPos, bytes);

                if (readSize <= 0) {
                    fileTransferUploadDataRequestPacket.setStatus(ImFileState.COMPLETE);
                } else if (readSize == Constant.BUFF_SIZE){
                    fileTransferUploadDataRequestPacket.setBeginPos(beginPos);
                    fileTransferUploadDataRequestPacket.setEndPos(beginPos + readSize);
                    fileTransferUploadDataRequestPacket.setBytes(bytes);
                    fileTransferUploadDataRequestPacket.setStatus(ImFileState.CENTER);
                } else {
                    // 不足buff_size尺寸需要拷贝去掉空字节
                    byte[] copy = new byte[readSize];
                    System.arraycopy(bytes, 0, copy, 0, readSize);
                    fileTransferUploadDataRequestPacket.setBeginPos(beginPos);
                    fileTransferUploadDataRequestPacket.setEndPos(beginPos + readSize);
                    fileTransferUploadDataRequestPacket.setBytes(copy);
                    fileTransferUploadDataRequestPacket.setStatus(ImFileState.END);
                }
                break;
            case COMPLETE:
                fileTransferUploadDataRequestPacket.setStatus(ImFileState.COMPLETE);
                System.out.println("上传文件完成，结束");
                channel.writeAndFlush(fileTransferUploadDataRequestPacket);
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
        channel.writeAndFlush(fileTransferUploadDataRequestPacket);
    }

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_RESPONSE;
    }
}
