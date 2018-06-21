package cn.e3mall.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.e3mall.common.jedis.JedisClient;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class JedisTest {
	@Autowired
	private JedisClient jedisClient;
	@Test
	public void test(){
		jedisClient.set("hello","tomcat");
		String get = jedisClient.get("hello");
		System.out.println(get);
	}
}
