package com.px.oad.vo;

import lombok.Data;

@Data
public class AccessTokenInfo {
    private String token;
    private String version;
    private Long refreshTime;
}
