package cn.aaron911.netty.im.server;

import cn.aaron911.netty.im.server.property.ServerProperty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;

import java.util.Date;

/**
 * @description:
 * @author:
 * @time: 2020/11/10 14:15
 */
public class NettyServer {

    public static void main(String[] args) {
        ServerProperty serverProperty = new ServerProperty();
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(serverProperty.getLeak_detector_level().toUpperCase()));
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(serverProperty.getBoss_group_thread_count());
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(serverProperty.getWorker_group_thread_count());
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new NettyServerInitializer());

            final Integer bind_port = serverProperty.getBind_port();
            final Channel channel = serverBootstrap.bind(bind_port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 端口[" + bind_port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + bind_port + "]绑定失败!");
                }
            }).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
