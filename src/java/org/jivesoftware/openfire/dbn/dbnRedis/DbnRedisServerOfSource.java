package org.jivesoftware.openfire.dbn.dbnRedis;


import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;




public class DbnRedisServerOfSource {
	private static JedisPool pool = null;
	private static final Logger Log = LoggerFactory.getLogger(DbnRedisServerOfSource.class);
	private static String redis_address = JiveGlobals.getProperty("host_redis_address");
	/**
	 * Get connected to the client
	 * 
	 * @return
	 */
	public static JedisPool getJedis() {
		if(null==pool){
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(100);
			config.setMaxIdle(25);
			config.setMaxWaitMillis(10 * 1000l);
			config.setTestOnBorrow(true);
            pool = new JedisPool(config, redis_address, 6379, 0, null, 10);
		}
		return pool;
	}
	/**
	 * Destruction of connection to the client
	 */
	public static void destroyRedisClient(JedisPool pool,Jedis jedis) {
		if (pool != null) {
			pool.returnResourceObject(jedis);
		}
	}
}
