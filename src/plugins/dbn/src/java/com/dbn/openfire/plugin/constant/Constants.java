package com.dbn.openfire.plugin.constant;

/**
 * 常量类
 * 
 * @author linyu
 * 
 */

public class Constants {

	/**
	 * DBN节点名
	 */
	public static final String DBN_ELEMENT_NAME = "nxin";
	/**
	 * DBN命名空间
	 */
	public static final String DBN_NAMESPACE = "www.nxin.com";
	/**
	 * DBN消息节点名
	 */
	public static final String DBN_MESSAGE_NAME = "m";
	/**
	 * web消息来源
	 */
	public static final String SOURCE_BY_WEB = "web";
	/**
	 * web消息类型
	 */
	public static final String WEB_TOPIC = "openfire.topic";
	/**
	 * token 缓存Key
	 */
	public static final String NXIN_TOKEN = "nxin_iostoken_jid_";
	/**
	 * 用户昵称缓存标识
	 */
	public static final String OA_WEB_CACHE = "oatongWeb";
	/**
	 * redis 分类前缀
	 */
	public final static String OPENFIRE = "openfire:";
	/**
	 * 离线消息暂存redis map前缀 用于离线消息 存 插件回执map
	 */
	public final static String NXIN_OFFLINE_MAP = "nxin_offline_map";
	/**
	 * 群聊离线消息异步批量保存
	 */
	public final static String OFFLINE_GROUP_MAP = "offline_group_map";
	/**
	 * 群聊离线消息KEY 前缀
	 */
	public final static String OFFLINE_GROUP = "offline_group:";
	/**
	 * 等待回执消息保存时间key前缀
	 */
	public final static String REDIS_TIME = "redis_time_";
	/**
	 * 群房间详情缓存前缀
	 */
	public final static String GROUP_ROOM_INFO = "group_room_info_";

	/**
	 * 公共账号后台数据会被interceptor方法拦截到 添加公共账号缓存用于区分后台公共账号 interceptor公共账号消息
	 */
	public final static String PUBLIC_SERVER = "public_server_";
	/**
	 * 公共账号根据公共账号jid从缓存中获取公共账号名称
	 */
	public final static String PUBLIC_JID_NICKNAME = "oatongWebcacheInfoByJId_";
	/**
	 * 根据jid获取用户昵称    从缓存中获取前缀
	 */
	public final static String USER_JID_NICKNAME = "oatongWebcacheUserNickNameByJId_";
	/**
	 * Web服务域名
	 */

	public static final String QUEUE_NAME_DBNJT = "nxin_public_dabeinongjituan";
	public static final String QUEUE_NAME_DBNGEN = "nxin_public_general";
	public static final String QUEUE_NAME_INS = "nxin_public_instant";
	public static final String QUEUE_NAME_LAR = "nxin_public_large";
	public static final String QUEUE_NAME_NFB = "nxin_public_nongfubao";
	public static final String QUEUE_NAME_OA = "nxin_public_oa";
	public static final String QUEUE_NAME_Q = "nxin_public_q";
	public static final String QUEUE_NAME_YZGJ = "nxin_public_yangzhugongju";
	public static final String QUEUE_NAME_YZNC = "nxin_public_yangzhuneican";
	public static final String QUEUE_NAME_ZLW = "nxin_public_zhulianwang";
	public static final String QUEUE_NAME_ZXH = "nxin_public_zhuxiaohui";
	public static final String QUEUE_NAME_JTNH = "nxin_public_jituannianhui";
	public static final String QUEUE_WEB_SERVER = "nxin_webserver_q";
	/**
	 * 公共账号离线消息缓存异步保存map
	 */
	
	public final static String OFFLINE_PUBLIC_MAP = "offline_public_map";
	/**
	 * 群聊离线消息KEY 前缀
	 */
	public final static String OFFLINE_PUBLIC = "offline_public:";
	/**
	 * 默认群名称
	 */
	public final static String GROUP_TOPIC = "未命名群组";
	public static final String GROUP_MEMBER_LIST = "group_member_list_";
	public static final String GROUP_MEMBER_INFO_LIST = "group_member_info_list_";
}
