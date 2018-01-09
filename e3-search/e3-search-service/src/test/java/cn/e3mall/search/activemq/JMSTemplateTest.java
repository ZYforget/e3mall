package cn.e3mall.search.activemq;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMSTemplateTest {

	@Test
	public void consumerJMSTemplate(){
		//初始化一个容器
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		System.out.println("消费者服务开启");
		try {
			System.in.read();
			System.out.println("消费者服务关闭");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
