package com.dbn.openfire.plugin;

import java.io.File;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.component.Component;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.component.ComponentManagerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

import com.dbn.openfire.plugin.interceptor.Interceptor;
import com.dbn.openfire.plugin.redis.DbnRedisServer;
import com.dbn.openfire.plugin.utils.DBNReceiptRedisThreadUtil;
import com.dbn.openfire.plugin.utils.DbnSaveGroupOfflineMessageThreadUtil;
import com.dbn.openfire.plugin.utils.DbnSavePublicOfflineMessageThreadUtil;

/**
 * DBN插件启动类
 * 
 * @author linyu
 * 
 */
public class DbnPlugin implements Plugin, Component {

	/**
	 * 日志
	 */
	private static final Logger Log = LoggerFactory.getLogger(DbnPlugin.class);

	/**
	 * 容器管理器
	 */
	private ComponentManager componentManager;

	/**
	 * 插件管理器
	 */
	private PluginManager pluginManager;

	/**
	 * 服务名
	 */
	public static String SERVICE_NAME = "nxingroup";
	/**
	 * 异步保存群聊离线消息
	 */
	private DbnSaveGroupOfflineMessageThreadUtil dbnSaveGroupOfflineMessageThreadUtil = new DbnSaveGroupOfflineMessageThreadUtil();
	/**
	 * 异步保存公共账号离线消息
	 */
	private DbnSavePublicOfflineMessageThreadUtil publicOfflineMessageThreadUtil = new DbnSavePublicOfflineMessageThreadUtil();
	/**
	 * redis 回执发送消息机制
	 */
	private DBNReceiptRedisThreadUtil dBNReceiptRedisThreadUtil = new DBNReceiptRedisThreadUtil();
	/**
	 * 拦截器
	 */
	private Interceptor interceptor = new Interceptor();
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		
		InterceptorManager.getInstance().addInterceptor(interceptor);// 添加拦截器
		Log.info("Startting DbnPlugin");
		//初始化redis连接池
		DbnRedisServer.initJedisPool();
		// 注册到组件服务
		pluginManager = manager;
		componentManager = ComponentManagerFactory.getComponentManager();
		try {
			componentManager.addComponent(SERVICE_NAME, this);
		} catch (ComponentException e) {
			Log.error(e.getMessage(), e);
		}
		
		dbnSaveGroupOfflineMessageThreadUtil.start();
		publicOfflineMessageThreadUtil.start();
		dBNReceiptRedisThreadUtil.start();
		
	}

	@Override
	public void destroyPlugin() {
		Log.info("Stopping DbnPlugin");
		InterceptorManager.getInstance().removeInterceptor(interceptor);// 移除拦截器
		// 移除组件服务
		pluginManager = null;
		try {
			componentManager.removeComponent(SERVICE_NAME);
			componentManager = null;
		} catch (Exception e) {
			if (componentManager != null) {
				Log.error(e.getMessage(), e);
			}
		}
		dbnSaveGroupOfflineMessageThreadUtil.shutdown();
		publicOfflineMessageThreadUtil.shutdown();
		dBNReceiptRedisThreadUtil.shutdown();
		//销毁redis链接
		DbnRedisServer.destroyRedisClient();
		
	}

	
	@Override
	public String getDescription() {
		return pluginManager.getDescription(this);
	}

	@Override
	public String getName() {
		return pluginManager.getName(this);
	}

	@Override
	public void initialize(JID arg0, ComponentManager arg1)
			throws ComponentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void processPacket(Packet packet) {
	}

}
