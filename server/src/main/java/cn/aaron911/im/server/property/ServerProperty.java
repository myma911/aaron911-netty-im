package cn.aaron911.im.server.property;

import lombok.Data;

@Data
public class ServerProperty {

    private Integer bind_port = 8000;

    private Integer boss_group_thread_count = 1;

    private Integer worker_group_thread_count = 2;

    private String leak_detector_level = "DISABLED";

    private Integer max_payload_size = 65536;
}
