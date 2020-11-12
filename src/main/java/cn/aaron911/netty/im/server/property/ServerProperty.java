package cn.aaron911.netty.im.server.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author:
 * @time: 2020/11/10 14:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aaron911.netty.im.server")
public class ServerProperty {

    private Integer bind_port = 8000;

    private Integer boss_group_thread_count = 1;

    private Integer worker_group_thread_count = 2;

    private String leak_detector_level = "DISABLED";

    private Integer max_payload_size = 65536;
}
