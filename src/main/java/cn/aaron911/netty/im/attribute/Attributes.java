package cn.aaron911.netty.im.attribute;

import cn.aaron911.netty.im.session.Session;
import io.netty.util.AttributeKey;


public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
