package com.px.oad.controller;


import com.px.oad.vo.JsonBean;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.qq.weixin.mp.aes.SHA1;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;


@RestController()
@RequestMapping("/valid")
@Slf4j
public class CheckController {


    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = "/checkSignature", method = {RequestMethod.GET,RequestMethod.POST})
    public String checkSignature(/*@RequestParam(name = "signature") String  signature,
                                   @RequestParam(name = "timestamp") String  timestamp,
                                   @RequestParam(name = "timestamp") String  nonce,
                                 @RequestParam(name = "echostr") String  echostr*/
    @RequestBody(required = false)String xml) throws DocumentException, UnsupportedEncodingException {
        String token="pxisgod";
        String AESKey="MBLt6RA1tLtzvhNJwqkqwJKwMS7tq9Q4MCYqNbvP5GQ";
        String appId="wx2eaf3e410a27d289";
        String appSecret="0e8a78d13ff130d9716732bfbcbaface";
        String access_token="46__DASFh2duMrdJ0nTUAxg-h2Xfpn-3a6IyD8byq1iPGx-LOskRRH6VWPVg2krPr3KUxsjWpBqNsmvh5UfGOjIRcPwurJtLHHqC6gtteGyq-ffHcm1kP6hRfu7A7ESPqvb2C2yfCSGqLvobJWsRVSaACAVZW";
        JsonBean jsonBean = new JsonBean();
        if(request.getParameterMap().isEmpty()){
            SAXReader reader = new SAXReader();
            Document document = null;
            try {
                //result是需要解析的字符串
                //解析字符串需要转换成流的形式，可以指定转换字符编码
                document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
                Element root=document.getRootElement();
                String msgType=root.elementText("MsgType");
                if(msgType.equals("text")){
                    String content=root.elementText("Content");
                    String ToUserName=root.elementText("FromUserName");
                    String FromUserName=root.elementText("ToUserName");
                    String CreateTime=root.elementText("CreateTime");
                    if(content.equals("pxisgod")){
                        return "<xml>\n" +
                                " <ToUserName><![CDATA["+ToUserName+"]]></ToUserName>\n" +
                                " <FromUserName><![CDATA["+FromUserName+"]]></FromUserName>\n" +
                                " <CreateTime>"+CreateTime+"</CreateTime>\n" +
                                " <MsgType><![CDATA[text]]></MsgType>\n" +
                                " <Content><![CDATA[godispx]]></Content>\n" +
                                "</xml>";
                    }else{
                        return "success";
                    }
                }else{
                    return "success";
                }
            } catch (DocumentException e) {
                throw  e;
            } catch (UnsupportedEncodingException e) {
               throw e;
            }
        }else {
            String echostr = request.getParameter("echostr");
            return echostr;
        }
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


    public static void main(String[] args) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            //result是需要解析的字符串
            //解析字符串需要转换成流的形式，可以指定转换字符编码
            String xml="<xml>\n" +
                    " <ToUserName><![CDATA[粉丝号]]></ToUserName>\n" +
                    " <FromUserName><![CDATA[公众号]]></FromUserName>\n" +
                    " <CreateTime>1460541339</CreateTime>\n" +
                    " <MsgType><![CDATA[text]]></MsgType>\n" +
                    " <Content><![CDATA[test]]></Content>\n" +
                    "</xml>";
            document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            Element root=document.getRootElement();
            String msgType=root.elementText("MsgType");
            System.out.println(msgType);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
