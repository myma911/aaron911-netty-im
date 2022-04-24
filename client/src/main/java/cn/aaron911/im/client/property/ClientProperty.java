package cn.aaron911.im.client.property;

import lombok.Data;

@Data
public class ClientProperty {

    private int max_retry = 5;

    private String host = "127.0.0.1";

    private int port = 8000;

}
