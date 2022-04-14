package cn.aaron911.netty.im.client.console;


import cn.aaron911.netty.im.util.SessionUtil;
import cn.hutool.core.util.ClassUtil;
import io.netty.channel.Channel;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();

    public ConsoleCommandManager() {
        Reflections reflections = new Reflections("cn.aaron911.netty.im.client.console");
        Set<Class<? extends ConsoleCommand>> subTypes = reflections.getSubTypesOf(ConsoleCommand.class);
        subTypes.forEach(x -> {
            if ("ConsoleCommandManager".equals(x.getSimpleName())) {
                return;
            }
            try {
                ConsoleCommand consoleCommand = x.newInstance();
                consoleCommandMap.put(consoleCommand.getCommand().toString(), consoleCommand);
                consoleCommandMap.put(x.getSimpleName(), consoleCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        //  获取第一个指令
        String command = scanner.next();

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }
        String nextLine = scanner.nextLine();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }

    @Override
    public Byte getCommand() {
        return null;
    }
}
