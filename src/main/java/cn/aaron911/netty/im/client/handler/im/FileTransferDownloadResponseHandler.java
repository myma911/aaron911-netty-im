package cn.aaron911.netty.im.client.handler.im;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferDownloadRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferDownloadResponsePacket;
import cn.aaron911.netty.im.util.Constant;
import cn.aaron911.netty.im.util.persistence.ImFileSession;
import cn.aaron911.netty.im.util.persistence.ImFileState;
import cn.aaron911.netty.im.util.persistence.ImFileUtil;
import cn.aaron911.netty.im.util.session.Session;
import cn.aaron911.netty.im.util.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;

import static cn.aaron911.netty.im.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_RESPONSE;


public class FileTransferDownloadResponseHandler extends SimpleChannelInboundHandler<FileTransferDownloadResponsePacket> implements ICommand {

    public static final FileTransferDownloadResponseHandler INSTANCE = new FileTransferDownloadResponseHandler();

    protected FileTransferDownloadResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileTransferDownloadResponsePacket fileTransferDownloadResponsePacket) throws Exception {
        Boolean fileExist = fileTransferDownloadResponsePacket.getFileExist();

        if (!fileExist) {
            System.out.println("服务端文件异常了，下载不下来了, 结束");
            return;
        }

        ImFileState status = fileTransferDownloadResponsePacket.getStatus();
        if (null == status) {
            System.out.println("状态为null, 结束");
            return;
        }
        String md5Hex = fileTransferDownloadResponsePacket.getMd5Hex();
        Integer beginPos = fileTransferDownloadResponsePacket.getBeginPos();
        Integer endPos = fileTransferDownloadResponsePacket.getEndPos();
        int bytesLength = endPos - beginPos;
        byte[] bytes = fileTransferDownloadResponsePacket.getBytes();

        Channel channel = ctx.channel();
        Session session = SessionUtil.getSession(channel);
        ImFileSession imFileSession = session.getFileMap().get(md5Hex);
        File file = new File(imFileSession.getClientFileDir() + imFileSession.getFileName());
        FileTransferDownloadRequestPacket requestPacket = new FileTransferDownloadRequestPacket();
        requestPacket.setMd5Hex(md5Hex);
        switch (status){
            case BEGIN:
            case CENTER:
            case END:
                ImFileUtil.writeFile(file, beginPos, bytes);
                if (bytesLength == Constant.BUFF_SIZE) {
                    requestPacket.setReadPosition(endPos + 1);
                    requestPacket.setStatus(ImFileState.CENTER);
                } else if (bytesLength < Constant.BUFF_SIZE) {
                    requestPacket.setStatus(ImFileState.COMPLETE);
                }
                channel.writeAndFlush(requestPacket);
                break;
            case COMPLETE:
                System.out.println("文件下载完毕");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
    }

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_RESPONSE;
    }
}
