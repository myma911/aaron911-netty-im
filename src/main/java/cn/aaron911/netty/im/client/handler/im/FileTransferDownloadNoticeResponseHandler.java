package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.response.FileTransferDownloadNoticeResponsePacket;
import cn.aaron911.netty.im.util.persistence.ImFileSession;
import cn.aaron911.netty.im.util.session.Session;
import cn.aaron911.netty.im.util.session.SessionUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_RESPONSE;


public class FileTransferDownloadNoticeResponseHandler extends SimpleChannelInboundHandler<FileTransferDownloadNoticeResponsePacket> implements ICommand {

    public static final FileTransferDownloadNoticeResponseHandler INSTANCE = new FileTransferDownloadNoticeResponseHandler();

    protected FileTransferDownloadNoticeResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferDownloadNoticeResponsePacket fileTransferDownloadNoticeResponsePacket) throws Exception {
        Session fromSession = fileTransferDownloadNoticeResponsePacket.getFromSession();
        String fileName = fileTransferDownloadNoticeResponsePacket.getFileName();
        String md5Hex = fileTransferDownloadNoticeResponsePacket.getMd5Hex();
        Long fileSize = fileTransferDownloadNoticeResponsePacket.getFileSize();
        System.out.println(StrUtil.format("用户【{}】给您发来文件【{}】大小【{}】md5【{}】", fromSession.getUserName(), fileName, md5Hex, fileSize));
        Session session = SessionUtil.getSession(ctx.channel());
        ImFileSession imFileSession = ImFileSession.builder()
                .md5Hex(md5Hex)
                .fileSize(fileSize)
                .fileName(fileName)
                .build();
        session.getFileMap().put(md5Hex, imFileSession);
    }



    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_RESPONSE;
    }
}
