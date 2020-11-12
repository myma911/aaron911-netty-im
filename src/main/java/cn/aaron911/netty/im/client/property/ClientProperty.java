package cn.aaron911.netty.im.client.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author:
 * @time: 2020/11/10 16:43
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aaron911.netty.im.client")
public class ClientProperty {

    private int max_retry = 5;

    private String host = "127.0.0.1";

    private int port = 8000;

}
