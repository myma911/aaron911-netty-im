package cn.aaron911.netty.im.server.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferDownloadRequestPacket;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferDownloadResponsePacket;
import cn.aaron911.netty.im.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.netty.im.util.Constant;
import cn.aaron911.netty.im.util.persistence.*;
import cn.aaron911.netty.im.util.session.Session;
import cn.aaron911.netty.im.util.session.SessionUtil;
import cn.hutool.core.io.FileUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_REQUEST;
import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_REQUEST;

@ChannelHandler.Sharable
public class FileTransferDownloadRequestHandler extends SimpleChannelInboundHandler<FileTransferDownloadRequestPacket> implements ICommand {
    public static final FileTransferDownloadRequestHandler INSTANCE = new FileTransferDownloadRequestHandler();

    private FileTransferDownloadRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferDownloadRequestPacket fileTransferDownloadRequestPacket) {

        try {
            Channel channel = ctx.channel();
            Session session = SessionUtil.getSession(channel);

            // 要下载的文件的md5
            String md5Hex = fileTransferDownloadRequestPacket.getMd5Hex();

            ImFileSession imFileSession = session.getFileMap().get(md5Hex);

            // 查看全局服务端是否存在该文件
            ImFileGlobal imFileGlobal = ImFileCacheUtil.get(md5Hex);
            FileTransferDownloadResponsePacket fileTransferDownloadResponsePacket = new FileTransferDownloadResponsePacket();
            if (null == imFileGlobal || null == imFileSession) {
                // 文件不存在，不能下载
                fileTransferDownloadResponsePacket.setFileExist(false);
                channel.writeAndFlush(fileTransferDownloadResponsePacket);
                return;
            }

            ImFileState status = fileTransferDownloadRequestPacket.getStatus();
            if (null == status) {
                System.out.println("状态异常");
                return;
            }

            fileTransferDownloadResponsePacket.setMd5Hex(md5Hex);
            fileTransferDownloadResponsePacket.setFileName(imFileSession.getFileName());
            fileTransferDownloadResponsePacket.setFileSize(imFileGlobal.getFileSize());

            switch (status) {
                case BEGIN:
                case CENTER:
                case END:
                    // 读文件
                    Integer beginPos = fileTransferDownloadRequestPacket.getReadPosition();
                    byte[] bytes = new byte[Constant.BUFF_SIZE];
                    String serverFileUrl = imFileGlobal.getServerFileUrl();
                    int readSize = ImFileUtil.readFile(serverFileUrl, beginPos, bytes);

                    if (readSize <= 0) {
                        fileTransferDownloadResponsePacket.setStatus(ImFileState.COMPLETE);
                    } else if (readSize == Constant.BUFF_SIZE) {
                        fileTransferDownloadResponsePacket.setBeginPos(beginPos);
                        fileTransferDownloadResponsePacket.setEndPos(beginPos + readSize);
                        fileTransferDownloadResponsePacket.setBytes(bytes);
                        fileTransferDownloadResponsePacket.setStatus(ImFileState.CENTER);
                    } else if (readSize < Constant.BUFF_SIZE) {
                        // 不足buff_size尺寸需要拷贝去掉空字节
                        byte[] copy = new byte[readSize];
                        System.arraycopy(bytes, 0, copy, 0, readSize);
                        fileTransferDownloadResponsePacket.setBeginPos(beginPos);
                        fileTransferDownloadResponsePacket.setEndPos(beginPos + readSize);
                        fileTransferDownloadResponsePacket.setBytes(copy);
                        fileTransferDownloadResponsePacket.setStatus(ImFileState.END);
                    } else {
                        System.out.println("什么情况？");
                    }
                    break;
                case COMPLETE:
                    fileTransferDownloadResponsePacket.setStatus(ImFileState.COMPLETE);

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + status);
            }

            channel.writeAndFlush(fileTransferDownloadResponsePacket);
            return;
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_REQUEST;
    }
}
