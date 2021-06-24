package com.px.oad.outputHandler;

import java.util.List;

import com.px.oad.vo.ReceiveXmlEntity;
import com.px.oad.vo.SendXmlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 用于创建输出xml文本的处理器。
 * @author ldk
 *
 */
@Component
public class OutXmlProcess {
	@Autowired
	private TextOutputHandler textOutputHandler;

	/**
     * 通过接收XML实体类对象和设定好的发送文本，确定发送Text类型的XML文本。
     * @param receiveXmlEntity 接受XML实体对象。
     * @param respContent 设定好的发送文本。
     * @return 要发送的Xml文本
     */
    public String getTextResult(ReceiveXmlEntity receiveXmlEntity, String respContent) {
    	//对发送Xml实体对象进行确定
    	SendXmlEntity sendXmlEntity = getSendXmlEntity(receiveXmlEntity);
    	sendXmlEntity.setMsgType("text");
    	sendXmlEntity.setContent(respContent);

    	return textOutputHandler.getXmlResult(sendXmlEntity);
    }


    /**
	 * 根据输入xml对象，创建输出xml对象，并填充基础属性。
	 * @param receiveXmlEntity
	 * @return
	 */
	private SendXmlEntity getSendXmlEntity(ReceiveXmlEntity receiveXmlEntity) {
		SendXmlEntity sendXmlEntity = new SendXmlEntity();
    	sendXmlEntity.setFromUserName(receiveXmlEntity.getToUserName());
    	sendXmlEntity.setToUserName(receiveXmlEntity.getFromUserName());
    	sendXmlEntity.setCreateTime(receiveXmlEntity.getCreateTime());
    	return sendXmlEntity;
	}
}
