package cn.aaron911.im.common.protocol;

import com.alibaba.fastjson.annotation.JSONField;

public interface ICommand {

    @JSONField(serialize = false)
    Byte getCommand();
}
