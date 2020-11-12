package cn.aaron911.netty.im.client;


import cn.aaron911.netty.im.client.console.ConsoleCommandManager;
import cn.aaron911.netty.im.client.console.LoginConsoleCommand;
import cn.aaron911.netty.im.client.property.ClientProperty;
import cn.aaron911.netty.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class NettyClient {


    public static void main(String[] args) {
        final ClientProperty cilentProperty = new ClientProperty();
        connect(new Bootstrap(), new NioEventLoopGroup(), cilentProperty, cilentProperty.getMax_retry());
    }


    public static void connect(Bootstrap bootstrap, EventLoopGroup workerGroup, ClientProperty clientProperty, int retry) {
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new NettyClientInitializer());

        final String host = clientProperty.getHost();
        final int port = clientProperty.getPort();
        final int max_retry = clientProperty.getMax_retry();
        bootstrap.connect(host, port).addListener((ChannelFuture channelFuture) -> {
            if (channelFuture.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = channelFuture.channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (max_retry - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                final EventLoop eventLoop = channelFuture.channel().eventLoop();
                bootstrap.config().group().schedule(() -> connect(new Bootstrap(), eventLoop, clientProperty, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    public static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
            LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
            Scanner scanner = new Scanner(System.in);
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}


