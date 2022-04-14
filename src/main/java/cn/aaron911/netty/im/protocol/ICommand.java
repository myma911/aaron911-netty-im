package cn.aaron911.netty.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;

public interface ICommand {

    @JSONField(serialize = false)
    Byte getCommand();
}
