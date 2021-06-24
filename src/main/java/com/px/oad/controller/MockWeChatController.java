package com.px.oad.controller;

import com.px.oad.vo.JsonBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 模仿wechat服务器
 */
@RestController()
@RequestMapping("/mockWeChat")
@Slf4j
public class MockWeChatController {


    @RequestMapping(value = "/getAccessToken", method = {RequestMethod.GET,RequestMethod.POST})
    public JsonBean getAccessToken()  {
        return null;
    }

    @RequestMapping(value = "/pushMsg", method = {RequestMethod.GET,RequestMethod.POST})
    public JsonBean pushMsg()  {
        return null;
    }



}
