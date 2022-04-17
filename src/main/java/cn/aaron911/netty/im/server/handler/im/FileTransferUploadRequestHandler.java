package cn.aaron911.netty.im.server.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.netty.im.session.Session;
import cn.aaron911.netty.im.util.persistence.ImFileCacheUtil;
import cn.aaron911.netty.im.util.SessionUtil;
import cn.aaron911.netty.im.util.persistence.ImFileSession;
import cn.aaron911.netty.im.util.persistence.ImFileGlobal;
import cn.aaron911.netty.im.util.persistence.ImFileState;
import cn.hutool.core.io.FileUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_REQUEST;

@ChannelHandler.Sharable
public class FileTransferUploadRequestHandler extends SimpleChannelInboundHandler<FileTransferUploadRequestPacket> implements ICommand {
    public static final FileTransferUploadRequestHandler INSTANCE = new FileTransferUploadRequestHandler();

    private FileTransferUploadRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferUploadRequestPacket fileTransferUploadRequestPacket) {
        //
        Channel channel = ctx.channel();
        // 要上传的文件的md5
        String md5Hex = fileTransferUploadRequestPacket.getMd5Hex();

        // 查看全局服务端是否存在该文件
        ImFileGlobal imFileGlobal = ImFileCacheUtil.get(md5Hex);

        if (null != imFileGlobal){
            // 给客户端响应
            FileTransferUploadResponsePacket fileTransferUploadResponsePacket = FileTransferUploadResponsePacket.builder()
                    .status(ImFileState.COMPLETE)
                    .md5Hex(md5Hex)
                    .build();
            channel.writeAndFlush(fileTransferUploadResponsePacket);
            return;
        }

        // 查看会话中文件是否存在
        Session session = SessionUtil.getSession(channel);
        ImFileSession imFileSessionInSession = session.getFileMap().get(md5Hex);
        if (null == imFileSessionInSession){
            // 存储在会话中
            // TODO... 服务器端存储路径
            String serverFileUrl = "/xxx/" + md5Hex + ".xxx";
            FileUtil.newFile(serverFileUrl);
            imFileSessionInSession = ImFileSession.builder()
                    .status(ImFileState.BEGIN)
                    .readPosition(0)
                    .fileName(fileTransferUploadRequestPacket.getFileName())
                    .fileSize(fileTransferUploadRequestPacket.getFileSize())
                    .fileUrl(fileTransferUploadRequestPacket.getFileUrl())
                    .serverFileUrl(serverFileUrl)
                    .md5Hex(fileTransferUploadRequestPacket.getMd5Hex())
                    .build();
            session.getFileMap().put(md5Hex, imFileSessionInSession);

            // 给客户端响应
            FileTransferUploadResponsePacket fileTransferUploadResponsePacket = FileTransferUploadResponsePacket.builder()
                    .status(ImFileState.BEGIN)
                    .readPosition(0)
                    .fileUrl(fileTransferUploadRequestPacket.getFileUrl())
                    .build();
            channel.writeAndFlush(fileTransferUploadResponsePacket);
            return;
        }

        // 给客户端响应
        FileTransferUploadResponsePacket fileTransferUploadResponsePacket = FileTransferUploadResponsePacket.builder()
                .status(ImFileState.CENTER)
                .fileUrl(imFileSessionInSession.getFileUrl())
                .status(imFileSessionInSession.getStatus())
                .readPosition(imFileSessionInSession.getReadPosition())
                .md5Hex(imFileSessionInSession.getMd5Hex())
                .build();
        channel.writeAndFlush(fileTransferUploadResponsePacket);
        return;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
