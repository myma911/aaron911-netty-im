package cn.aaron911.im.common.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public abstract class ImPacket implements ICommand {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

}
