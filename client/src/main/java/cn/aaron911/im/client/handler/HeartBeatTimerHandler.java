package cn.aaron911.im.client.handler;

import cn.aaron911.im.client.NettyClient;
import cn.aaron911.im.client.property.ClientProperty;
import cn.aaron911.im.common.protocol.request.HeartBeatRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭! 重连");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        ClientProperty clientProperty = new ClientProperty();
        NettyClient.connect(new Bootstrap(), eventLoop, clientProperty, clientProperty.getMax_retry());
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
