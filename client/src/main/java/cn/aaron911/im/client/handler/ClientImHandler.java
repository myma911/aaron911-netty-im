package cn.aaron911.im.client.handler;

import cn.aaron911.im.common.protocol.ICommand;
import cn.aaron911.im.common.protocol.ImPacket;
import cn.aaron911.im.common.util.Constant;
import cn.aaron911.im.common.util.session.SessionUtil;
import cn.hutool.core.util.ReflectUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@ChannelHandler.Sharable
public class ClientImHandler extends SimpleChannelInboundHandler<ImPacket> {

    public static final ClientImHandler INSTANCE = new ClientImHandler();

    private Map<Class, SimpleChannelInboundHandler<? extends ImPacket>> handlerMap = new HashMap<>();


    private ClientImHandler() {
        Reflections reflections = new Reflections(Constant.PROTOCOL_RESPONSE_PACKAGE);
        Set<Class<? extends ImPacket>> responsePacketClassSet = reflections.getSubTypesOf(ImPacket.class);

        reflections = new Reflections(Constant.CLIENT_HANDLER_PACKAGE);
        Set<Class<? extends ICommand>> responseHandlerClassSet = reflections.getSubTypesOf(ICommand.class);

        for (Class<? extends ImPacket> responsePacketClass : responsePacketClassSet) {
            Byte command;
            try {
                ImPacket instance = responsePacketClass.newInstance();
                command = instance.getCommand();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (null == command){
                continue;
            }
            Iterator<Class<? extends ICommand>> responseHandlerClassIterator = responseHandlerClassSet.iterator();
            while (responseHandlerClassIterator.hasNext()){
                Class<? extends ICommand> responseHandlerClass = responseHandlerClassIterator.next();
                try {
                    Field field = ReflectUtil.getField(responseHandlerClass, "INSTANCE");
                    Object responseHandlerObject = field.get(null);
                    if (null == responseHandlerObject || !(responseHandlerObject instanceof SimpleChannelInboundHandler)){
                        continue;
                    }

                    Method getCommandMethod = ReflectUtil.getMethodByName(responseHandlerClass, "getCommand");
                    Object getCommand = ReflectUtil.invoke(responseHandlerObject, getCommandMethod);
                    if (null != getCommand && getCommand instanceof Byte && command.equals(getCommand)) {
                        handlerMap.put(responsePacketClass, (SimpleChannelInboundHandler<? extends ImPacket>) responseHandlerObject);
                        responseHandlerClassIterator.remove();
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        handlerMap.put(HeartBeatResponsePacket.class, HeartBeatResponseHandler.INSTANCE);

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImPacket packet) throws Exception {
        final Class<? extends ImPacket> packetClass = packet.getClass();
        final SimpleChannelInboundHandler<? extends ImPacket> simpleChannelInboundHandler = handlerMap.get(packetClass);
        if (null == simpleChannelInboundHandler) {
            System.out.println("暂未实现的数据包处理");
            return;
        }
        simpleChannelInboundHandler.channelRead(ctx, packet);
    }

    /**
     * 客户端溢出退出
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
            SessionUtil.unBindSession(channel);
        }
    }
}
