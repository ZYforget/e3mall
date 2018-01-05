package cn.e3mall.test.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisClusterTest {

	@Test
	public void testRedisCluster(){
		Set<HostAndPort> set=new HashSet<>();
		set.add(new HostAndPort("192.168.25.130", 7001));
		set.add(new HostAndPort("192.168.25.130", 7002));
		set.add(new HostAndPort("192.168.25.130", 7003));
		set.add(new HostAndPort("192.168.25.130", 7004));
		set.add(new HostAndPort("192.168.25.130", 7005));
		set.add(new HostAndPort("192.168.25.130", 7006));
		JedisCluster cluster=new JedisCluster(set);
		cluster.set("c1", "紫阳");
		String string = cluster.get("c1");
		System.out.println(string);
		cluster.close();
	}
}
