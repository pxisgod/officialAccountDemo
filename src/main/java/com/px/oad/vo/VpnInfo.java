package com.px.oad.vo;

import lombok.Data;

/**
 * Vpn消息
 */
@Data
public class VpnInfo {
    private String serverIp;
    private Short serverPort;
    private String password;
    private String method;
    private String protocol;
    private String obfs;
}
