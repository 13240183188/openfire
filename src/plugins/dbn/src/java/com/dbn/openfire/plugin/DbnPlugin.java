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
 * DBN���������
 * 
 * @author linyu
 * 
 */
public class DbnPlugin implements Plugin, Component {

	/**
	 * ��־
	 */
	private static final Logger Log = LoggerFactory.getLogger(DbnPlugin.class);

	/**
	 * ����������
	 */
	private ComponentManager componentManager;

	/**
	 * ���������
	 */
	private PluginManager pluginManager;

	/**
	 * ������
	 */
	public static String SERVICE_NAME = "nxingroup";
	/**
	 * �첽����Ⱥ��������Ϣ
	 */
	private DbnSaveGroupOfflineMessageThreadUtil dbnSaveGroupOfflineMessageThreadUtil = new DbnSaveGroupOfflineMessageThreadUtil();
	/**
	 * �첽���湫���˺�������Ϣ
	 */
	private DbnSavePublicOfflineMessageThreadUtil publicOfflineMessageThreadUtil = new DbnSavePublicOfflineMessageThreadUtil();
	/**
	 * redis ��ִ������Ϣ����
	 */
	private DBNReceiptRedisThreadUtil dBNReceiptRedisThreadUtil = new DBNReceiptRedisThreadUtil();
	/**
	 * ������
	 */
	private Interceptor interceptor = new Interceptor();
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		
		InterceptorManager.getInstance().addInterceptor(interceptor);// ���������
		Log.info("Startting DbnPlugin");
		//��ʼ��redis���ӳ�
		DbnRedisServer.initJedisPool();
		// ע�ᵽ�������
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
		InterceptorManager.getInstance().removeInterceptor(interceptor);// �Ƴ�������
		// �Ƴ��������
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
		//����redis����
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
