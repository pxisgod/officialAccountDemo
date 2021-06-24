package com.px.oad.controller;


import com.px.oad.service.ProcessRequest;
import com.px.oad.service.PushMsgService;
import com.px.oad.util.SignUtil;
import com.px.oad.vo.JsonBean;
import com.px.oad.vo.VpnInfo;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;


@RestController()
@RequestMapping("/official-account")
@Slf4j
public class OfficialAccountController {


    @Autowired
    private HttpServletRequest request;

    //推送消息的服务
    @Autowired
    private PushMsgService pushMsgService;

    @Autowired
    private ProcessRequest processRequest;

    //token值
    @Value("${token}")
    private String token;


    /**
     * 微信公众号的消息处理接口
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping( method = RequestMethod.GET)
    public String signup(){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce,token)) {
            log.info("微信服务端接入成功！");
            return echostr;
        }
        return null;
    }

    /**
     * 微信各种消息的接收接口。
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String handleMsgs(HttpServletRequest request, HttpServletResponse response) {
        // 调用核心业务类接收消息、处理消息
        String respMessage = "";
        try {
            respMessage = processRequest.process(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }


    /**
     * 主动推送VPN信息消息到微信公众号
     * @param vpnInfo
     * @return
     */
    @RequestMapping(value = "/pushVpnInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonBean pushVpnInfo(@RequestBody VpnInfo vpnInfo) {

        JsonBean jsonBean = new JsonBean();
        try {
            jsonBean.setData("测试成功");
        } catch (Exception e) {
            log.error("获取博客内容失败", e);
            jsonBean.fail("获取博客内容失败");
        }
        return jsonBean;
    }



    /**
     * 主动推送VPN信息消息到微信公众号
     * @param vpnInfo
     * @return
     */
    @RequestMapping(value = "/getAccessToken", method = RequestMethod.POST)
    @ResponseBody
    public JsonBean getAccessToken(@RequestBody VpnInfo vpnInfo) {

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
