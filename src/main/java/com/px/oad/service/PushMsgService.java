package com.px.oad.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 消息推送url
 */
@Service
public class PushMsgService {


    //推送消息的url
    @Value("${pushMsgUrl}")
    private String accessTokenUrl;

    /**
     * 推送消息到微信公众号
     * @param xml
     */
    public void pushMsg(String xml){

    }
}
