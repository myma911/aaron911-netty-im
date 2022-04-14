package cn.aaron911.netty.im.server.handler.im;


import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.request.FileTransferUploadRequestPacket;
import cn.aaron911.netty.im.session.Session;
import cn.aaron911.netty.im.util.SessionUtil;
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

        // 2.通过消息发送方的会话信息构造要发送的消息

//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        messageResponsePacket.setFrom(session);
//        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
//
//        // 3.拿到消息接收方的 channel
//        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
//
//        // 4.将消息发送给消息接收方
//        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
//            toUserChannel.writeAndFlush(messageResponsePacket).addListener(future -> {
//                if (future.isDone()) {
//
//                }
//            });
//        } else {
//            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
//        }
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
