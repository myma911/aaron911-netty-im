package cn.aaron911.netty.im.server.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.netty.im.util.session.Session;
import cn.aaron911.netty.im.util.persistence.ImFileCacheUtil;
import cn.aaron911.netty.im.util.session.SessionUtil;
import cn.aaron911.netty.im.util.persistence.ImFileSession;
import cn.aaron911.netty.im.util.persistence.ImFileGlobal;
import cn.aaron911.netty.im.util.persistence.ImFileState;
import cn.hutool.core.io.FileUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_UPLOAD_REQUEST;
import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_REQUEST;

@ChannelHandler.Sharable
public class FileTransferUploadRequestHandler extends SimpleChannelInboundHandler<FileTransferUploadRequestPacket> implements ICommand {
    public static final FileTransferUploadRequestHandler INSTANCE = new FileTransferUploadRequestHandler();

    private FileTransferUploadRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferUploadRequestPacket requestPacket) {
        //
        Channel channel = ctx.channel();
        // 要上传的文件的md5
        String md5Hex = requestPacket.getMd5Hex();

        // 查看全局服务端是否存在该文件
        ImFileGlobal imFileGlobal = ImFileCacheUtil.get(md5Hex);

        FileTransferUploadResponsePacket responsePacket = new FileTransferUploadResponsePacket();
        responsePacket.setMd5Hex(md5Hex);
        if (null != imFileGlobal){
            // 给客户端响应
            responsePacket.setStatus(ImFileState.COMPLETE);
            channel.writeAndFlush(responsePacket);
            return;
        }

        // 查看会话中文件是否存在
        Session session = SessionUtil.getSession(channel);
        ImFileSession imFileSession = session.getFileMap().get(md5Hex);
        if (null == imFileSession){
            // 存储在会话中
            // 服务器端存储路径
            String extName = FileUtil.extName(requestPacket.getFileName());
            String serverFullFilePath = "/xxx/" + md5Hex + "." + extName;
            FileUtil.touch(serverFullFilePath);
            imFileSession = ImFileSession.builder()
                    .toUserId(requestPacket.getToUserId())
                    .status(ImFileState.BEGIN)
                    .readPosition(0)
                    .fileName(requestPacket.getFileName())
                    .fileSize(requestPacket.getFileSize())
                    .clientFileDir(requestPacket.getClientFileDir())
                    .serverFileUrl(serverFullFilePath)
                    .md5Hex(requestPacket.getMd5Hex())
                    .build();
            session.getFileMap().put(md5Hex, imFileSession);

            // 给客户端响应
            responsePacket.setStatus(ImFileState.BEGIN);
            responsePacket.setReadPosition(0);
            responsePacket.setClientFileDir(requestPacket.getClientFileDir());
            responsePacket.setFileName(requestPacket.getFileName());
            channel.writeAndFlush(responsePacket);
            return;
        }

        // 给客户端响应
        responsePacket.setStatus(ImFileState.CENTER);    //???? imFileSession.getStatus()
        responsePacket.setClientFileDir(imFileSession.getClientFileDir());
        responsePacket.setFileName(imFileSession.getFileName());
        responsePacket.setReadPosition(imFileSession.getReadPosition());
        channel.writeAndFlush(responsePacket);
    }

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_REQUEST;
    }
}
