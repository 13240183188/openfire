package org.jivesoftware.openfire.dbn.dbnUtils;
/**
 * Constants
 * 
 * @author songguopei
 * 
 */
public class DbnUtils {
	/**
	 * DBN_Node_Name
	 */
	public static final String DBN_ELEMENT_NAME = "nxin";
	/**
	 * DBN命名空间
	 */
	public static final String DBN_NAMESPACE="www.nxin.com";
	/**
	 * DBN消息节点名
	 */
	public static final String DBN_MESSAGE_NAME = "m";
	
	
	/**
	 * redis 分类前缀
	 */
	public final static String OPENFIRE = "openfire:";
	/**
	 * 离线消息暂存redis map前缀  用于离线消息  存   插件回执map
	 */
	public final static String NXIN_OFFLINE_MAP = "nxin_offline_map";
	/**
	 * 等待回执消息保存时间key前缀
	 */
	public final static String REDIS_TIME="redis_time_";
	/**
	 * 数据库缓存密码
	 */
	public final static String REDIS_PASSWORLD="redis_passworld:";
	
}
