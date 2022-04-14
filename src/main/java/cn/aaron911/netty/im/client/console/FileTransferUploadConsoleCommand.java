package cn.aaron911.netty.im.client.console;

import cn.aaron911.netty.im.protocol.request.FileTransferUploadRequestPacket;
import cn.aaron911.netty.im.protocol.request.MessageRequestPacket;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.netty.channel.Channel;

import java.io.File;
import java.util.Scanner;

import static cn.aaron911.netty.im.protocol.command.Command.MESSAGE_REQUEST;

public class FileTransferUploadConsoleCommand implements ConsoleCommand {
    
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送文件给某个用户，请输入用户id：");
        String toUserId = scanner.next();
        System.out.print("发送文件给某个用户,请输入本地文件完整路径：");
        String fileUrl = scanner.next();
        boolean exist = FileUtil.exist(fileUrl);
        if (!exist) {
            System.out.println(StrUtil.format("本地文件【{}】不存在，结束。", fileUrl));
            return;
        }
        File file = FileUtil.file(fileUrl);
        if (file.isDirectory()) {
            System.out.println("暂不支持发送文件目录，结束。");
            return;
        }
        String md5Hex = DigestUtil.md5Hex(file);
        FileTransferUploadRequestPacket requestPacket = FileTransferUploadRequestPacket.builder().toUserId(toUserId).fileName(file.getName()).fileSize(file.length())
                .fileUrl(fileUrl).md5Hex(md5Hex).build();
        channel.writeAndFlush(requestPacket);
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
