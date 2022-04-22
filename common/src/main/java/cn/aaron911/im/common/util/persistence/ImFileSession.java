package cn.aaron911.im.common.util.persistence;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImFileSession {

    /**
     * 客户端文件地址
     */
    private String clientFileDir;

    /**
     * 服务端文件地址
     */
    private String serverFileUrl;

    private String fileName;

    private Long fileSize;

    private String md5Hex;

    /**
     * 0开始、1中间、2结尾、3完成
     */
    private ImFileState status;

    /**
     * 读取位置
     */
    private Integer readPosition;

    /**
     * 要发送的用户id
     */
    private String toUserId;
}