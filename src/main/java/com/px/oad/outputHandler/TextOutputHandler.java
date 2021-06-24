package com.px.oad.outputHandler;

import com.px.oad.vo.SendXmlEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 封装有根据接收到的实体对象ReceiveXmlEntity，负责对Text类型请求进行关键字处理，并返回对应的回复XML文本内容的一系列操作。
 * @author ldk
 *
 */
@Component
public class TextOutputHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据发送Xml实体对象构建发送xml文本。
	 * @param sendXmlEntity 发送Xml实体对象（SendXmlEntity类型）
	 * @return 要发送的xml文本(String类型)
	 */
	public String getXmlResult(SendXmlEntity sendXmlEntity) {
		StringBuffer result = new StringBuffer();
		result.append("<xml><ToUserName><![CDATA[");
		result.append(sendXmlEntity.getToUserName());
		result.append("]]></ToUserName><FromUserName><![CDATA[");
		result.append(sendXmlEntity.getFromUserName());
		result.append("]]></FromUserName><CreateTime>");
		result.append(sendXmlEntity.getCreateTime());
		result.append("</CreateTime><MsgType><![CDATA[");
		result.append(sendXmlEntity.getMsgType());
		result.append("]]></MsgType><Content><![CDATA[");
		result.append(sendXmlEntity.getContent());
		result.append("]]></Content></xml>");
		return result.toString();
	}

}
