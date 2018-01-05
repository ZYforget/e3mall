package cn.e3mall.test.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {

	@Test
	public void testJedis(){
		Jedis jedis=new Jedis("192.168.25.130", 6379);
		jedis.set("r1", "你好");
		String string = jedis.get("r1");
		System.out.println(string);
		jedis.close();
	}
	@Test
	public void testJedisPool(){
		//创建一个jedis连接池
		JedisPool pool=new JedisPool("192.168.25.130", 6379);
		//获取jedis
		Jedis jedis = pool.getResource();
		jedis.set("p1", "连接池");
		String string = jedis.get("p1");
		System.out.println(string);
		//关闭连接
		jedis.close();
		pool.close();
	}
}
