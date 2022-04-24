package cn.aaron911.im.server.handler.im;


import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.request.FileTransferUploadDataRequestPacket;
import cn.aaron911.im.common.protocol.response.FileTransferDownloadNoticeResponsePacket;
import cn.aaron911.im.common.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.im.common.util.persistence.*;
import cn.aaron911.im.common.util.session.Session;
import cn.aaron911.im.common.util.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_UPLOAD_DATA_REQUEST;

@ChannelHandler.Sharable
public class FileTransferUploadDataRequestHandler extends SimpleChannelInboundHandler<FileTransferUploadDataRequestPacket> implements ICommand {
    public static final FileTransferUploadDataRequestHandler INSTANCE = new FileTransferUploadDataRequestHandler();

    private FileTransferUploadDataRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferUploadDataRequestPacket requestPacket) {
        Channel channel = ctx.channel();

        ImFileState status = requestPacket.getStatus();
        if (null == status){
            System.out.println("状态不能为null");
            return;
        }
        String md5Hex = requestPacket.getMd5Hex();
        Session session = SessionUtil.getSession(channel);
        ImFileSession imFileSession = session.getFileMap().get(md5Hex);
        Integer beginPos = requestPacket.getBeginPos();
        byte[] bytes = requestPacket.getBytes();

        FileTransferUploadResponsePacket responsePacket = new FileTransferUploadResponsePacket();
        responsePacket.setMd5Hex(imFileSession.getMd5Hex());
        responsePacket.setClientFileDir(imFileSession.getClientFileDir());
        responsePacket.setFileName(imFileSession.getFileName());

        ImFileGlobal imFileGlobal;
        try {
            switch (status){
                case BEGIN:
                case CENTER:
                    // 服务端写文件
                    ImFileUtil.writeFile(imFileSession.getServerFileUrl(), beginPos, bytes);
                    // 更新会话
                    imFileSession.setStatus(ImFileState.CENTER);
                    imFileSession.setReadPosition(requestPacket.getEndPos() + 1);
                    session.getFileMap().put(md5Hex, imFileSession);
                    // 给客户端响应
                    responsePacket.setStatus(imFileSession.getStatus());
                    responsePacket.setReadPosition(imFileSession.getReadPosition());
                    break;
                case END:
                    // 服务端写文件
                    ImFileUtil.writeFile(imFileSession.getServerFileUrl(), beginPos, bytes);

                    // 写入全局文件
                    imFileGlobal = ImFileCacheUtil.get(md5Hex);
                    if (null == imFileGlobal){
                        imFileGlobal = new ImFileGlobal();
                        imFileGlobal.setFileSize(imFileSession.getFileSize());
                        imFileGlobal.setServerFileUrl(imFileSession.getServerFileUrl());
                        imFileGlobal.setMd5Hex(md5Hex);
                        ImFileCacheUtil.set(md5Hex, imFileGlobal);
                    }

                    // 更新会话
                    imFileSession.setStatus(ImFileState.COMPLETE);
                    imFileSession.setReadPosition(null);
                    // 给客户端响应
                    responsePacket.setStatus(ImFileState.COMPLETE);
                    break;
                case COMPLETE:
                    String toUserId = imFileSession.getToUserId();
                    Channel toUserChannel = SessionUtil.getChannel(toUserId);
                    if (null == toUserChannel) {
                        System.out.println("用户不在线");
                        return;
                    }
                    imFileGlobal = ImFileCacheUtil.get(md5Hex);
                    if (null == imFileGlobal) {
                        System.out.println("全局文件丢失，结束");
                        return;
                    }
                    Session toUserSession = SessionUtil.getSession(toUserChannel);
                    toUserSession.getFileMap().put(md5Hex, imFileSession);
                    // 给用户发文件源信息
                    FileTransferDownloadNoticeResponsePacket noticeResponsePacket = new FileTransferDownloadNoticeResponsePacket();
                    noticeResponsePacket.setMd5Hex(md5Hex);
                    noticeResponsePacket.setFileName(imFileSession.getFileName());
                    noticeResponsePacket.setFileSize(imFileGlobal.getFileSize());
                    noticeResponsePacket.setFromSession(session);
                    toUserChannel.writeAndFlush(noticeResponsePacket);
                    return;
                default:
                    System.out.println("不支持的status:" + status);
                    return;
            }
            channel.writeAndFlush(responsePacket);
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
