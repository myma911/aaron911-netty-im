package cn.aaron911.im.common.attribute;

import cn.aaron911.im.common.util.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
