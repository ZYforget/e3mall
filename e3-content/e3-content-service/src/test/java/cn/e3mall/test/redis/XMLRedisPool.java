package cn.e3mall.test.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

public class XMLRedisPool {

	@Test
	public void testRedis(){
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient client = context.getBean(JedisClient.class);
		client.set("x1","forget");
		String string = client.get("x1");
		System.out.println(string);
	}
}
