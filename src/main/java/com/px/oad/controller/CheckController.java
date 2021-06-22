package com.px.oad.controller;


import com.px.oad.vo.JsonBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.qq.weixin.mp.aes.SHA1;

import javax.servlet.http.HttpServletRequest;




@RestController()
@RequestMapping("/valid")
@Slf4j
public class CheckController {


    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = "/checkSignature", method = RequestMethod.GET)
    public String getBlogContent(/*@RequestParam(name = "signature") String  signature,
                                   @RequestParam(name = "timestamp") String  timestamp,
                                   @RequestParam(name = "timestamp") String  nonce,
                                 @RequestParam(name = "echostr") String  echostr*/) {
        String token="pxisgod";
        String AESKey="MBLt6RA1tLtzvhNJwqkqwJKwMS7tq9Q4MCYqNbvP5GQ";
        JsonBean jsonBean = new JsonBean();
        try {
            /*String result=SHA1.getSHA1(token,timestamp,nonce,AESKey);
            if(result.equals(signature)){

            }else{

            }*/
        } catch (Exception e) {
            log.error("获取博客内容失败", e);
            jsonBean.fail("获取博客内容失败");
        }
        String echostr=request.getParameter("echostr");
        return echostr;
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public JsonBean demo() {

        JsonBean jsonBean = new JsonBean();
        try {
            jsonBean.setData("测试成功");
        } catch (Exception e) {
            log.error("获取博客内容失败", e);
            jsonBean.fail("获取博客内容失败");
        }
        return jsonBean;
    }


}
