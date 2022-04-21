package cn.aaron911.netty.im.server.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadDataRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferDownloadResponsePacket;
import cn.aaron911.netty.im.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.netty.im.util.session.Session;
import cn.aaron911.netty.im.util.session.SessionUtil;
import cn.aaron911.netty.im.util.persistence.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_UPLOAD_DATA_REQUEST;
import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_REQUEST;

@ChannelHandler.Sharable
public class FileTransferUploadDataRequestHandler extends SimpleChannelInboundHandler<FileTransferUploadDataRequestPacket> implements ICommand {
    public static final FileTransferUploadDataRequestHandler INSTANCE = new FileTransferUploadDataRequestHandler();

    private FileTransferUploadDataRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferUploadDataRequestPacket fileTransferUploadDataRequestPacket) {
        Channel channel = ctx.channel();

        ImFileState status = fileTransferUploadDataRequestPacket.getStatus();
        if (null == status){
            System.out.println("状态不能为null");
            return;
        }
        String md5Hex = fileTransferUploadDataRequestPacket.getMd5Hex();
        Session session = SessionUtil.getSession(channel);
        ImFileSession imFileSession = session.getFileMap().get(md5Hex);
        Integer beginPos = fileTransferUploadDataRequestPacket.getBeginPos();
        byte[] bytes = fileTransferUploadDataRequestPacket.getBytes();

        try {
            switch (status){
                case BEGIN:
                case CENTER:
                    // 服务端写文件
                    ImFileUtil.writeFile(imFileSession.getServerFileUrl(), beginPos, bytes);

                    // 更新会话
                    imFileSession.setStatus(ImFileState.CENTER);
                    imFileSession.setReadPosition(fileTransferUploadDataRequestPacket.getEndPos() + 1);
                    session.getFileMap().put(md5Hex, imFileSession);

                    // 给客户端响应
                    FileTransferUploadResponsePacket fileTransferUploadResponsePacket = FileTransferUploadResponsePacket.builder()
                            .status(ImFileState.CENTER)
                            .clientFileDir(imFileSession.getClientFileDir())
                            .fileName(imFileSession.getFileName())
                            .status(imFileSession.getStatus())
                            .readPosition(imFileSession.getReadPosition())
                            .md5Hex(imFileSession.getMd5Hex())
                            .build();
                    channel.writeAndFlush(fileTransferUploadResponsePacket);
                    break;
                case END:
                    // 服务端写文件
                    ImFileUtil.writeFile(imFileSession.getServerFileUrl(), beginPos, bytes);

                    // 更新会话
                    imFileSession.setStatus(ImFileState.COMPLETE);
                    imFileSession.setReadPosition(null);
                    session.getFileMap().put(md5Hex, imFileSession);

                    // 给客户端响应
                    fileTransferUploadResponsePacket = FileTransferUploadResponsePacket.builder()
                            .status(ImFileState.COMPLETE)
                            .md5Hex(imFileSession.getMd5Hex())
                            .build();
                    channel.writeAndFlush(fileTransferUploadResponsePacket);
                    break;
                case COMPLETE:
                    // 写入全局文件
                    ImFileGlobal imFileGlobal = ImFileCacheUtil.get(md5Hex);
                    if (null == imFileGlobal){
                        imFileGlobal = new ImFileGlobal();
                        imFileGlobal.setFileSize(imFileSession.getFileSize());
                        imFileGlobal.setServerFileUrl(imFileSession.getServerFileUrl());
                        imFileGlobal.setMd5Hex(md5Hex);
                        ImFileCacheUtil.set(md5Hex, imFileGlobal);
                    }

                    String toUserId = imFileSession.getToUserId();
                    Channel toUserChannel = SessionUtil.getChannel(toUserId);
                    if (null == toUserChannel) {
                        System.out.println("用户不在线");
                    }

                    // 给用户发文件源信息
                    FileTransferDownloadResponsePacket fileTransferDownloadResponsePacket = FileTransferDownloadResponsePacket.builder()
                            .md5Hex(md5Hex)
                            .fileName(imFileSession.getFileName())
                            .fileSize(imFileGlobal.getFileSize())
                            .fromSession(session)
                            .build();
                    toUserChannel.writeAndFlush(fileTransferDownloadResponsePacket);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + status);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // TODO ... 暂不支持的状态
        } catch (Exception e) {
            // TODO。。。 系统异常了
            e.printStackTrace();
        }
    }

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_DATA_REQUEST;
    }
}
