package cn.aaron911.netty.im.util.persistence;

import cn.aaron911.netty.im.protocol.request.FileTransferUploadDataRequestPacket;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileMode;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ImFileUtil {

    public static void writeFile(String serverFileUrl, FileTransferUploadDataRequestPacket fileTransferUploadDataRequestPacket) throws IOException {
        File serverFile = new File(serverFileUrl);
        RandomAccessFile randomAccessFile = FileUtil.createRandomAccessFile(serverFile, FileMode.rw);
        randomAccessFile.seek(fileTransferUploadDataRequestPacket.getBeginPos());
        randomAccessFile.write(fileTransferUploadDataRequestPacket.getBytes());
        randomAccessFile.close();
    }


}
