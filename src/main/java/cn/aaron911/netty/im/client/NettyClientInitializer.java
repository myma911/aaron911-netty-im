package cn.aaron911.netty.im.client;

import cn.aaron911.netty.im.client.handler.*;
import cn.aaron911.netty.im.codec.PacketCodecHandler;
import cn.aaron911.netty.im.codec.Spliter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClientInitializer   extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {

        ChannelPipeline channelPipeline = ch.pipeline();
        // 空闲检测
        //channelPipeline.addLast(new ClientIdleStateHandler());
        // 数据格式校验
        channelPipeline.addLast(new Spliter());
        // 编码解码
        channelPipeline.addLast(PacketCodecHandler.INSTANCE);
        // 自定义消息处理
        channelPipeline.addLast(ClientImHandler.INSTANCE);
        // 心跳定时器
        //channelPipeline.addLast(new HeartBeatTimerHandler());
    }
}
