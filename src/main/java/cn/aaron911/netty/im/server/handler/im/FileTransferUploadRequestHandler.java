package cn.aaron911.netty.im.server.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadRequestPacket;
import cn.aaron911.netty.im.protocol.response.FileTransferUploadResponsePacket;
import cn.aaron911.netty.im.session.Session;
import cn.aaron911.netty.im.util.SessionUtil;
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
        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        String toUserId = fileTransferUploadRequestPacket.getToUserId();

        Session toUserSession = SessionUtil.getSession(toUserId);

        if (null == toUserSession) {
            // 2. 要发送的用户不在线，通过消息发送方的会话信息构造要发送的消息
            FileTransferUploadResponsePacket fileTransferUploadResponsePacket = new FileTransferUploadResponsePacket();

            // 3.拿到消息接收方的 channel
            Channel channel = ctx.channel();
            // 4.将消息发送给消息接收方
            channel.writeAndFlush(fileTransferUploadResponsePacket);

            return;
        }
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
