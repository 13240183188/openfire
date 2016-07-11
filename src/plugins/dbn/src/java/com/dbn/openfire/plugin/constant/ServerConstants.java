package com.dbn.openfire.plugin.constant;

/**
 * 应用服务相关静态配置汇总类
 * 
 * @author lyf
 * 
 */
public class ServerConstants {
	// MC测试地址
	// public static String MEMCACHE_ADDRESS = "10.200.1.104:11211";
	// MC正式地址
	// public static String MEMCACHE_ADDRESS = "10.200.3.20:11211";
	// //MC亦庄正式服务器地址
	// public static String MEMCACHE_ADDRESS = "10.201.20.19:11211";
	// MC权重 测试
	public static int[] MEMCACHE_ADDRESS_WEIGHTS = new int[] { 1, 1 };
	// MC权重 正试
	// public static int [] MEMCACHE_ADDRESS_WEIGHTS= new int[]{1};
	// MC连接池大小
	public static int MEMCACHE_CONNECTIONPOOLSIZE = 15;
	// MC超时时间
	public static long MEMCACHED_TIMEOUT = 5000l;
	// 回执版本号
	public static String MEMCACHE_RECEIVED_LESS_VERSION = "1.1.1";
	// 公共账号发送消息版本判断
	public static String MEMCACHE_PUBLIC_VERSION = "2.2.0";
	// 网页端发给自己
	public static String MEMCACHE_TO_ONESELF_BY_WEB = "1.3.2";
	// MC根据jid获取版本号前缀
	public static String MEMCACHE_USERJID_VERSION_KEY = "userjid_version_";
}
