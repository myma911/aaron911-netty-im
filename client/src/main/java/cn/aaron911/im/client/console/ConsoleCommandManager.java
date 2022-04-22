package cn.aaron911.im.client.console;


import cn.aaron911.im.common.protocol.command.Command;
import cn.aaron911.im.common.util.Constant;
import cn.aaron911.im.common.util.session.SessionUtil;
import io.netty.channel.Channel;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ConsoleCommandManager {
    private Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();

    public ConsoleCommandManager() {
        Reflections reflections = new Reflections(Constant.CILENT_CONSOLE_PACKAGE);
        Set<Class<? extends ConsoleCommand>> subTypes = reflections.getSubTypesOf(ConsoleCommand.class);
        subTypes.forEach(x -> {
            try {
                ConsoleCommand consoleCommand = x.newInstance();
                consoleCommandMap.put(consoleCommand.getCommand().toString(), consoleCommand);
                consoleCommandMap.put(x.getSimpleName(), consoleCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void exec(Scanner scanner, Channel channel) {
        printCommand();
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
            printCommand();
        }
    }

    private static void printCommand() {
        try {
            System.out.println("英文指令#数字指令，输入哪个都行");
            Field[] fields = Command.class.getFields();
            for (Field f : fields) {
                Object o = f.get(Command.class);
                if (o instanceof Byte) {
                    byte b = (byte) o;
                    if (b % 2 == 1) {
                        System.out.println(f.getName() + "#" + o);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
