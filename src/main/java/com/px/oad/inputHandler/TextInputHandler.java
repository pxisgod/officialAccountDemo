package com.px.oad.inputHandler;

import com.px.oad.configuration.ClientZkConfig;
import com.px.oad.outputHandler.OutXmlProcess;
import com.px.oad.vo.ReceiveXmlEntity;
import com.px.oad.vo.VpnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 文本输入处理类
 * @author ldk
 *
 */
@Component
public class TextInputHandler  {
	@Autowired
	private OutXmlProcess outXmlProcess;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static String register = "Hi，欢迎关注";

	/**
	 * 根据接收到的实体对象，确定回复Xml文本消息内容。
	 * @param receiveXmlEntity 接收到的实体对象（ReceiveXmlEntity类型）
	 * @return 回复Xml消息内容（String类型）
	 */
	public String handleWithReceive(ReceiveXmlEntity receiveXmlEntity) {
		String receiveContent = receiveXmlEntity.getContent();
		StringBuffer content = new StringBuffer();

		if(receiveContent != null && receiveContent.equals("pxisgod")) {
			String openId = receiveXmlEntity.getFromUserName();
			logger.info("----------------收到用户：openId=" + openId + " 的文本消息,文本内容为：" + receiveContent + "----------------");
			VpnInfo vpnInfo=ClientZkConfig.getVpnInfo();
			if(vpnInfo!=null) {
				content.append("IP:").append(vpnInfo.getIp()).append("\n");
				content.append("PORT:").append(vpnInfo.getPort()).append("\n");
				content.append("PASSWORD:").append(vpnInfo.getPassword());
				return outXmlProcess.getTextResult(receiveXmlEntity, content.toString());
			}
		}

		return outXmlProcess.getTextResult(receiveXmlEntity, content.toString());
	}
}
