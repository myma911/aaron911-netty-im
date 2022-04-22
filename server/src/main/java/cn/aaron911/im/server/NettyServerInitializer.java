package cn.aaron911.im.server;

import cn.aaron911.im.common.codec.PacketCodecHandler;
import cn.aaron911.im.common.codec.Spliter;
import cn.aaron911.im.server.handler.AuthHandler;
import cn.aaron911.im.server.handler.HeartBeatRequestHandler;
import cn.aaron911.im.server.handler.ServerIdleStateHandler;
import cn.aaron911.im.server.handler.ServiceImHandler;
import cn.aaron911.im.server.handler.im.LoginRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        // 空闲检测
        pipeline.addLast(new ServerIdleStateHandler());

        pipeline.addLast(new Spliter());

        pipeline.addLast(PacketCodecHandler.INSTANCE);
        // 心跳响应
        pipeline.addLast(HeartBeatRequestHandler.INSTANCE);
        // 必须先登录
        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
        // 再检测登录, 未登录后面不再执行
        pipeline.addLast(AuthHandler.INSTANCE);
        // 自定义消息处理
        pipeline.addLast(ServiceImHandler.INSTANCE);
    }
}
