package cn.e3mall.service.activemq;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestActiveMQ {

	@Test
	public void testActiveMQProducer(){
		//初始化一个容器
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//获取jmstemplate对象
		JmsTemplate template = context.getBean(JmsTemplate.class);
		//获取destination
		Destination destination = (Destination) context.getBean("queueDestination");
		//使用jmstemplate对象发送消息
		template.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("hello spring activemq jmstemplate");
				return message;
			}
		});
	}
}
