package cn.itcast.ms.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import cn.itcast.ms.utils.SmsUtils;
@Component("msConsumer")
public class MsConsumer  implements MessageListener{
	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
	    try {
			String telephone = mapMessage.getString("telephone");
			String checkcode = mapMessage.getString("checkcode");
			SmsUtils.sendSmsCheckCode(checkcode, telephone);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	    
	}

}
