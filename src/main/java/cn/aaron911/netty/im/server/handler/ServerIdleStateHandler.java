package cn.aaron911.netty.im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 服务空闲处理
 */
public class ServerIdleStateHandler extends IdleStateHandler {

    /**
     * 读超时时间
     */
    private static final int READER_IDLE_TIME = 15;


    public ServerIdleStateHandler() {
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        // 如果读通道处于空闲状态，说明没有接收到心跳命令
        System.out.println("服务端：" + READER_IDLE_TIME + "秒内未读到数据");
        //ctx.channel().close();
    }
}
