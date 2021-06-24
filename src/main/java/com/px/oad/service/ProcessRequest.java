package com.px.oad.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.px.oad.inputHandler.TextInputHandler;
import com.px.oad.outputHandler.OutXmlProcess;
import com.px.oad.util.Map2EntityProcess;
import com.px.oad.util.XMLUtils;
import com.px.oad.vo.ReceiveXmlEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 对各类请求进行处理的类。
 * @author ldk
 *
 */
@Component
public class ProcessRequest {

	@Autowired
	private TextInputHandler textInputHandler;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据request请求进行具体的处理
	 * @param request Http请求
	 * @param response Http回应
	 * @return
	 * @throws Exception
	 */
    public String process(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //解析请求Xml文档为map类型容器
        Map<String, String> map = XMLUtils.parseXml(request);
        //将map转化为接收Xml实体对象
        ReceiveXmlEntity receiveXmlEntity = new Map2EntityProcess().getMsgEntity(map);
        //最终返回的xml文本
        String result = "";

        String msgType = receiveXmlEntity.getMsgType();
        logger.info("收到消息，消息类型为：" + msgType + "!");
        //文本消息
        if (msgType.equals("text")) {
        	result = textInputHandler.handleWithReceive(receiveXmlEntity);
        }

        return result;
    }


}
