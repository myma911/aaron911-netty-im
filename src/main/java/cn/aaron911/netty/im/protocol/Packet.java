package cn.aaron911.netty.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;


@Data
public abstract class Packet implements ICommand {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

}
