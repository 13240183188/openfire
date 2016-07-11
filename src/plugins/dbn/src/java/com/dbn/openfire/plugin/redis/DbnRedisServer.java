package com.dbn.openfire.plugin.redis;

import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class DbnRedisServer {
	private static JedisPool pool = null;
	private static final Logger Log = LoggerFactory.getLogger(DbnRedisServer.class);
	private static String redis_address = JiveGlobals.getProperty("host_redis_address");
	
	public static void initJedisPool() {
		if(null == pool){
			Log.info("initJedisPool start jedisPool !");
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(100);
			config.setMaxIdle(25);
			config.setMaxWaitMillis(10 * 1000l);
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, redis_address, 6379, 0, null, 10);
		}
	}

	/**
	 * 创建redis连接池连接
	 * 
	 * @return
	 */
	public static JedisPool getJedis() {
		return pool;
	}

	/**
	 * 销毁redis连接
	 */
	public static void destroyRedisClient() {
		if(null!=pool){
			pool.destroy();
			pool=null;
		}
		Log.info("initJedisPool is destroyRedisClient!");
	}
	
}
