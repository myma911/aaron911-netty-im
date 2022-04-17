package cn.aaron911.netty.im.util.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局文件存储
 */
public class ImFileCacheUtil {

    /**
     * key 文件md5
     * value ImFileGlobal
     */
    private static Map<String, ImFileGlobal> concurrentHashMap = new ConcurrentHashMap<>();

    public static ImFileGlobal get(String md5){
        return concurrentHashMap.get(md5);
    }

    public static void set(String md5Hex, ImFileGlobal imFileGlobal){
        concurrentHashMap.put(md5Hex, imFileGlobal);
    }
}
