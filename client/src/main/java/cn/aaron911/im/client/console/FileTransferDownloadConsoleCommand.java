package cn.aaron911.im.client.console;

import cn.aaron911.im.common.protocol.request.FileTransferDownloadRequestPacket;
import cn.aaron911.im.common.util.persistence.ImFileSession;
import cn.aaron911.im.common.util.session.Session;
import cn.aaron911.im.common.util.session.SessionUtil;
import cn.hutool.core.io.FileUtil;
import io.netty.channel.Channel;

import java.io.File;
import java.util.Scanner;

import static cn.aaron911.im.common.protocol.command.Command.FILE_TRANSFER_DOWNLOAD_REQUEST;

public class FileTransferDownloadConsoleCommand implements ConsoleCommand {
    
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("下载文件，请输入文件md5：");
        String md5Hex = scanner.next();
        Session session = SessionUtil.getSession(channel);
        ImFileSession imFileSession = session.getFileMap().get(md5Hex);
        if (null == imFileSession){
            System.out.println("文件md5输入错误，结束");
            return;
        }
        System.out.print("下载文件，请输入要保存文件的目录：");
        String dir = scanner.next();
        boolean exist = FileUtil.exist(dir);
        if (!exist){
            FileUtil.mkdir(dir);
            System.out.println("目录不存在，创建目录["+ dir +"]");
        }
        if (!FileUtil.isDirectory(dir)){
            System.out.println("不是目录，结束");
            return;
        }
        try {
            // 在本地创建文件
            File file = new File(dir + imFileSession.getFileName());
            file.createNewFile();
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        imFileSession.setClientFileDir(dir);
        FileTransferDownloadRequestPacket requestPacket = new FileTransferDownloadRequestPacket();
        requestPacket.setMd5Hex(md5Hex);
        requestPacket.setClientFileUrl(dir);
        requestPacket.setStatus(imFileSession.getStatus());
        requestPacket.setReadPosition(imFileSession.getReadPosition());

        channel.writeAndFlush(requestPacket);
    }

    @Override
    public Byte getCommand() {
        return FILE_TRANSFER_DOWNLOAD_REQUEST;
    }
}
