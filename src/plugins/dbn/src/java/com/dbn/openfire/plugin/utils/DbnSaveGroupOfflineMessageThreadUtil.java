package com.dbn.openfire.plugin.utils;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;

import org.xmpp.packet.Message;

import com.dbn.openfire.plugin.constant.Constants;
import com.dbn.openfire.plugin.redis.DbnRedisServer;

import org.dom4j.io.SAXReader;
import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DbnSaveGroupOfflineMessageThreadUtil extends Thread {
	private static final Logger Log = LoggerFactory.getLogger(DbnSaveGroupOfflineMessageThreadUtil.class);
	private boolean runFlag = true;
	private static final String INSERT_OFFLINE = "INSERT INTO ofOffline (username, messageID, creationDate, messageSize, stanza) VALUES (?, ?, ?, ?, ?)";
	Set<String> msgIdMap = null;
	public DbnSaveGroupOfflineMessageThreadUtil(){
		this.setName("DbnSaveGroupOfflineMessageThreadUtil");
	}
	public void run() {
		
			while (runFlag) {
				saveOfflineMessage();
			}
	}
	
	/**
	 * ����������Ϣ
	 */
	private void saveOfflineMessage() {
		JedisPool pool = DbnRedisServer.getJedis();
		Jedis jedis = pool.getResource();
		
		SAXReader xmlReader = new SAXReader();
		try {
			msgIdMap = jedis.zrange(Constants.OPENFIRE+ Constants.OFFLINE_GROUP_MAP, 0, 999);
		} catch (Exception e) {
			Log.error("DbnSaveGroupOfflineMessageThreadUtil is error :" + e);
		} finally {
			pool.returnResourceObject(jedis);
		}
		if (null != msgIdMap && msgIdMap.size() > 0) {
			String username = null;
			String messageID;
			String msgXML = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			Jedis jedis1 = pool.getResource();
			try {
				con = DbConnectionManager.getConnection();
				pstmt = con.prepareStatement(INSERT_OFFLINE);
				for (String msgId : msgIdMap) {
					String msgTem = jedis1.get(Constants.OPENFIRE+ Constants.OFFLINE_GROUP + msgId);
					if (null != msgTem) {
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
						jedis1.zrem(Constants.OPENFIRE + Constants.OFFLINE_GROUP_MAP,msgId);
						jedis1.del(Constants.OPENFIRE + Constants.OFFLINE_GROUP+ msgId);
					}
				}
				pstmt.executeBatch();
			} catch (Exception e) {
				Log.error("�첽����Ⱥ��������Ϣʧ�ܣ�" + e);
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
	
	
	/*private void processOfflineMessage(){
		ConcurrentLinkedQueue<Message> map = ConcurrentGroupQueueUtil.getIntance().getMap();
		// ��������������
		Connection con = null;
		PreparedStatement pstmt = null;
		String username = null;
		String messageID;
		String msgXML = null;
		int count = 0;
		Message msg = null;
		try{
			while(map.size()>0){
				msg = map.poll();
				if(null!=msg){
					String toJid = msg.getTo().toString().split("@")[0];
					Collection<Presence> presences = presenceManager.getPresences(toJid);
					if (presences.size() >0) {
						JedisPool pool = DbnRedisServer.getJedis();
						Jedis jedis = pool.getResource();
						Long time = System.currentTimeMillis();
						try {
							deliverer.deliver(msg);
							jedis.zadd(Constants.OPENFIRE+ Constants.NXIN_OFFLINE_MAP, time, msg.getID());
							jedis.set(Constants.OPENFIRE + msg.getID(),msg.toString());
							jedis.set(Constants.OPENFIRE + Constants.REDIS_TIME+ msg.getID(), time + "");
							Log.info("DbnSavePublicOfflineMessageThreadUtil ����������Ϣ:"+ msg);
						} catch (PacketException e) {
							Log.error("DbnSaveOfflineMessageThreadUtil deliverer msg is error:"+e);
						} catch (UnauthorizedException e) {
							Log.error("DbnSaveOfflineMessageThreadUtil UnauthorizedException is error:"+e);
						} finally {
							pool.returnResourceObject(jedis);
						}
					} else if(presences.size() == 0){
						// ��֯�������
						count++;
						if (con == null) {
							con = DbConnectionManager.getConnection();
							pstmt = con.prepareStatement(INSERT_OFFLINE);
						}
						username = msg.getTo().toString().split("@")[0];
						messageID = msg.getID();
						msgXML = msg.getElement().asXML();
						pstmt.setString(1, username);
						pstmt.setString(2, messageID);
						pstmt.setLong(3, new java.util.Date().getTime());
						pstmt.setInt(4, msgXML.length());
						pstmt.setString(5, msgXML);
						pstmt.addBatch();
						if (count >= 1000) {
							break;
						}
					}
				}
			}
			if(count > 0){
				pstmt.executeBatch();
			}
		}catch(Exception e){
			Log.error("DbnSaveGroupOfflineMessageThreadUtil  is error:"+e);
		}finally {
			DbConnectionManager.closeConnection(pstmt, con);
		}
	}*/
	

	/**
	 * �߳̽���
	 */
	public void shutdown() {
		runFlag = false;
	}
}
