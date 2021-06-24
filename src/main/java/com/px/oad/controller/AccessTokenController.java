package com.px.oad.controller;


import com.px.oad.vo.AccessTokenInfo;
import com.px.oad.vo.JsonBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/accessToken")
@Slf4j
public class AccessTokenController {


    @Value(value = "${grantType}")
    private String grantType;

    @Value("${appId}")
    private String appId;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${accessTokenUrl}")
    private String accessTokenUrl;


    @RequestMapping(value = "/getAccessToken", method = {RequestMethod.GET,RequestMethod.POST})
    public JsonBean getAccessToken()  {
        return null;
    }

    @RequestMapping(value = "/updateAccessToken", method = {RequestMethod.GET,RequestMethod.POST})
    public JsonBean updateAccessToken(@RequestBody AccessTokenInfo accessTokenInfo)  {
        return null;
    }


}
