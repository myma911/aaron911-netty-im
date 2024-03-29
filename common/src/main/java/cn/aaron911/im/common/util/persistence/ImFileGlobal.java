package cn.aaron911.im.common.util.persistence;

import lombok.Data;

@Data
public class ImFileGlobal {

    private Long fileSize;

    private String md5Hex;

    /**
     * 服务端文件地址
     */
    private String serverFileUrl;
}