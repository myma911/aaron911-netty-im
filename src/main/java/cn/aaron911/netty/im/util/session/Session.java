package cn.aaron911.netty.im.util.session;

import cn.aaron911.netty.im.util.persistence.ImFileSession;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private String userId;
    private String userName;

    private Map<String, ImFileSession> fileMap = new HashMap<>();

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Map<String, ImFileSession> getFileMap() {
        return fileMap;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return StrUtil.format("userId[{}],userName[{}]", userId, userName);
    }



}
