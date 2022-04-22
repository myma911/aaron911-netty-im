package cn.aaron911.im.common.util.persistence;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileMode;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ImFileUtil {

    public static int readFile(String filePath, int beginPos, byte[] bytes) throws IOException {
        File file = FileUtil.file(filePath);
        return readFile(file, beginPos, bytes);
    }

    public static int readFile(File file, int beginPos, byte[] bytes) throws IOException {
        check(file);
        try (RandomAccessFile randomAccessFile = FileUtil.createRandomAccessFile(file, FileMode.r)) {
            randomAccessFile.seek(beginPos);
            return randomAccessFile.read(bytes);
        }
    }

    private static void check(File file) {
        if (null == file) {
            throw new RuntimeException("file is null");
        }
        if (file.isDirectory()) {
            throw new RuntimeException("not a file");
        }
    }


    public static void writeFile(String filePath, int beginPos, byte[] bytes) throws IOException {
        File file = new File(filePath);
        writeFile(file, beginPos, bytes);
    }

    public static void writeFile(File file, int beginPos, byte[] bytes) throws IOException {
        check(file);
        try (RandomAccessFile randomAccessFile = FileUtil.createRandomAccessFile(file, FileMode.rw)) {
            randomAccessFile.seek(beginPos);
            randomAccessFile.write(bytes);
        }
    }

}
