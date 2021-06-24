package com.px.oad.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * accessToken定时刷新任务
 */
@Configuration
public class AccessTokenRefreshConfig {

    @Value("${refreshPeriod}")
    private Long refreshPeriod;

    @Value("${validTime}")
    private Long validTime;


}
