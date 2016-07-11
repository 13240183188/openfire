package com.dbn.openfire.plugin.utils;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.xmpp.packet.Message;
import org.xmpp.packet.Presence;

import com.dbn.openfire.plugin.constant.Constants;
import com.dbn.openfire.plugin.redis.DbnRedisServer;

import org.dom4j.io.SAXReader;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.PacketException;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DbnSavePublicOfflineMessageThreadUtil extends Thread {
	private static final Logger Log = LoggerFactory.getLogger(DbnSavePublicOfflineMessageThreadUtil.class);
	private boolean runFlag = true;
	private static final String INSERT_OFFLINE = "INSERT INTO ofOffline (username, messageID, creationDate, messageSize, stanza) VALUES (?, ?, ?, ?, ?)";
	/**
	 * 出席管理器
	 */
	private PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();
	/**
	 * send message
	 */
	private static PacketDeliverer deliverer = XMPPServer.getInstance().getPacketDeliverer();
	public DbnSavePublicOfflineMessageThreadUtil() {
		this.setName("DbnSavePublicOfflineMessageThreadUtil");
	}

	public void run() {

		while (runFlag) {
			saveOfflineMessage();
		}
	}
	/**
	 * 保存离线消息
	 */
	private void saveOfflineMessage() {
		JedisPool pool = DbnRedisServer.getJedis();
		Jedis jedis = pool.getResource();
		SAXReader xmlReader = new SAXReader();
		Set<String> msgIdMap = null;
		try{
			msgIdMap =  jedis.zrange(Constants.OPENFIRE+Constants.OFFLINE_PUBLIC_MAP, 0, 999);
		} catch (Exception e) {
			Log.error("DbnSavePublicOfflineMessageThreadUtil is error :" + e);
		} finally {
			pool.returnResourceObject(jedis);
		}
//			Log.error("save public offline message size():"+msgIdMap.size());
			if(null!=msgIdMap && msgIdMap.size()>0){
				Connection con = null;
				PreparedStatement pstmt = null;
				String username = null;
				String messageID;
				String msgXML = null;
				Jedis jedis1 = pool.getResource();
				try {
				con = DbConnectionManager.getConnection();
				pstmt = con.prepareStatement(INSERT_OFFLINE);
				for(String msgId:msgIdMap){
					String msgTem = jedis1.get(Constants.OPENFIRE+Constants.OFFLINE_PUBLIC+msgId);
					if(null!=msgTem){
						Message msg = new Message(xmlReader.read(new StringReader(msgTem)).getRootElement());
						username = msg.getTo().toString().split("@")[0];
						messageID = msg.getID();
						msgXML = msg.getElement().asXML();
						pstmt.setString(1, username);
						pstmt.setString(2, messageID);
						pstmt.setLong(3, new java.util.Date().getTime());
						pstmt.setInt(4, msgXML.length());
						pstmt.setString(5, msgXML);
						pstmt.addBatch();
						jedis1.zrem(Constants.OPENFIRE+Constants.OFFLINE_PUBLIC_MAP, msgId);
						jedis1.del(Constants.OPENFIRE+Constants.OFFLINE_PUBLIC+msgId);
					}
				}
				pstmt.executeBatch();
			}catch (Exception e) {
				Log.error("异步保存公共帐号离线消息失败：" + e);
			} finally {
				DbConnectionManager.closeConnection(pstmt, con);
				pool.returnResourceObject(jedis1);
			}
		}else{
			try {
				sleep(2*1000l);
			} catch (InterruptedException e) {
				Log.error(e.getMessage());
			}
		}
	}


	/**
	 * 线程结束
	 */
	public void shutdown() {
		runFlag = false;
	}
}
