package cn.aaron911.im.client.console;

import cn.aaron911.im.common.protocol.request.FileTransferUploadRequestPacket;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.netty.channel.Channel;

import java.io.File;
import java.util.Scanner;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_UPLOAD_REQUEST;

public class FileTransferUploadConsoleCommand implements ConsoleCommand {
    
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送文件给某个用户，请输入用户id：");
        String toUserId = scanner.next();
        System.out.print("发送文件给某个用户,请输入本地文件完整路径：");
        String fileFullPath = scanner.next();
        boolean exist = FileUtil.exist(fileFullPath);
        if (!exist) {
            System.out.println(StrUtil.format("本地文件【{}】不存在，结束。", fileFullPath));
            return;
        }
        File file = FileUtil.file(fileFullPath);
        if (file.isDirectory()) {
            System.out.println("暂不支持发送文件目录，结束。");
            return;
        }
        String fileName = FileUtil.getName(fileFullPath);
        String fileDir = fileFullPath.substring(0, StrUtil.indexOfIgnoreCase(fileFullPath, fileName));
        String md5Hex = DigestUtil.md5Hex(file);
        FileTransferUploadRequestPacket requestPacket = FileTransferUploadRequestPacket.builder()
                .toUserId(toUserId)
                .clientFileDir(fileDir)
                .fileName(fileName)
                .fileSize(file.length())
                .md5Hex(md5Hex).build();
        channel.writeAndFlush(requestPacket);
    }

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_UPLOAD_REQUEST;
    }
}
