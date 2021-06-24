package com.px.oad.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    //accessToken服务器地址
    @Value("${accessTokenServerUrl}")
    private String accessTokenServerUrl;

}
