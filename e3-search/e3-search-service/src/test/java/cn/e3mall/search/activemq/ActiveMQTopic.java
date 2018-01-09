package cn.e3mall.search.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQTopic {

	@Test
	public void testTopicProducer()throws Exception{
		// 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
		ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 第二步：使用ConnectionFactory对象创建一个Connection对象。
		Connection connection = factory.createConnection();
		// 第三步：开启连接，调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Topic对象。
		Topic topic=session.createTopic("active_topic");
		// 第六步：使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(topic);
		// 第七步：创建一个Message对象，创建一个TextMessage对象。
		TextMessage message = session.createTextMessage("hello activemq topic");
		// 第八步：使用Producer对象发送消息。
		producer.send(message);;
		// 第九步：关闭资源。
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicConsumer()throws Exception{
		// 消费者：接收消息。
		// 第一步：创建一个ConnectionFactory对象。
		ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 第二步：从ConnectionFactory对象中获得一个Connection对象。
		Connection connection = factory.createConnection();
		// 第三步：开启连接。调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象。和发送端保持一致topic，并且话题的名称一致。
		Topic topic = session.createTopic("active_topic");
		// 第六步：使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(topic);
		// 第七步：接收消息。
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage) message;
				try {
					String text = textMessage.getText();
					// 第八步：打印消息。
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//等待键盘输入
		System.in.read();
		// 第九步：关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
