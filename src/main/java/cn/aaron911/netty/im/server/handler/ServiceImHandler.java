package cn.aaron911.netty.im.server.handler;

import cn.aaron911.netty.im.protocol.ICommand;
import cn.aaron911.netty.im.protocol.Packet;
import cn.aaron911.netty.im.util.SessionUtil;
import cn.hutool.core.util.ReflectUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@ChannelHandler.Sharable
public class ServiceImHandler extends SimpleChannelInboundHandler<Packet> {
    public static final ServiceImHandler INSTANCE = new ServiceImHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<>();

    private ServiceImHandler() {
        Reflections reflections = new Reflections("cn.aaron911.netty.im.server.handler.im");
        Set<Class<? extends ICommand>> classSet = reflections.getSubTypesOf(ICommand.class);
        classSet.forEach(x -> {
            try {
                Field field = ReflectUtil.getField(x, "INSTANCE");
                Object instance = field.get(null);
                if (null == instance || !(instance instanceof SimpleChannelInboundHandler)) {
                    return;
                }
                Method getCommandMethod = ReflectUtil.getMethodByName(x, "getCommand");
                Object getCommand = ReflectUtil.invoke(instance, getCommandMethod);
                if (null != getCommand && getCommand instanceof Byte) {
                    handlerMap.put((Byte) getCommand, (SimpleChannelInboundHandler<? extends Packet>) instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        System.out.println(cause.getMessage());
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
            SessionUtil.unBindSession(channel);
        }
    }
}
