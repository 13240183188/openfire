package com.dbn.openfire.plugin.utils;

import java.io.StringReader;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.io.SAXReader;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Presence;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dbn.openfire.plugin.constant.Constants;
import com.dbn.openfire.plugin.redis.DbnRedisServer;

public class DBNReceiptRedisThreadUtil extends Thread {
	private static final Logger Log = LoggerFactory
			.getLogger(DBNReceiptRedisThreadUtil.class);
	private boolean runFlag = true;

	private PacketDeliverer deliverer = XMPPServer.getInstance()
			.getPacketDeliverer();
	private PresenceManager presenceManager = XMPPServer.getInstance()
			.getPresenceManager();
	public DBNReceiptRedisThreadUtil(){
		this.setName("DBNReceiptRedisThreadUtil");
	}
	public void run() {
		while (runFlag) {
			processMessage();
		}
		
	}

	private void processMessage() {
		try {
			processDBNMessageMode();
			this.sleep(20 * 1000l);
		} catch (Exception e) {
			Log.error("DBNReceiptRedisThreadUtil错误：" + e);
		}

	}

	private void processDBNMessageMode() {
		SAXReader xmlReader = new SAXReader();
		JedisPool pool = DbnRedisServer.getJedis();
		Jedis jedis = pool.getResource();
		try {
			Set<String> messageMap = jedis.zrange(Constants.OPENFIRE + Constants.NXIN_OFFLINE_MAP, 0, -1);
			Log.info("DBNReceiptRedisThreadUtil:所有等待回执消息:" + messageMap.size() + "------messageMap:" + messageMap);
			if (null != messageMap && messageMap.size() > 0) {
				for (String msgId : messageMap) {
					String message = jedis.get(Constants.OPENFIRE + msgId);
					Log.info("回执机智处理消息" + message);
					if (null != message) {
						Message msg = new Message(xmlReader.read(new StringReader(message)).getRootElement());
						String dateTem = jedis.get(Constants.OPENFIRE + Constants.REDIS_TIME + msg.getID());
						if (!StringUtils.isEmpty(dateTem)) {
							Collection<Presence> presences = presenceManager.getPresences(msg.getTo().toString().split("@")[0]);
							if (presences.size() == 0) {
								jedis.zrem(Constants.OPENFIRE + Constants.NXIN_OFFLINE_MAP, msg.getID());
								jedis.del(Constants.OPENFIRE + Constants.REDIS_TIME + msg.getID());
								jedis.del(Constants.OPENFIRE + msg.getID());
								if (!msg.getBody().equals("0") && !msg.getBody().equals("4")) {
									Log.error("DBNReceiptRedisThreadUtil  save   message  by   DbnSaveGroupThread ：" + msg);
									jedis.zadd(Constants.OPENFIRE+Constants.OFFLINE_GROUP_MAP, System.currentTimeMillis(), msgId);
									jedis.set(Constants.OPENFIRE+Constants.OFFLINE_GROUP+msgId, message.toString());
								}
								
							} else {
								Long oldDate = Long.parseLong(dateTem);
								Long nowDate = System.currentTimeMillis();
								if (nowDate.longValue() - oldDate.longValue() > 30 * 1000
										&& nowDate.longValue() - oldDate.longValue() < 60 * 1000) {
									deliverer.deliver(msg);
								} else if (nowDate.longValue() - oldDate.longValue() > 60 * 1000) {
									jedis.zrem(Constants.OPENFIRE + Constants.NXIN_OFFLINE_MAP, msg.getID());
									jedis.del(Constants.OPENFIRE + Constants.REDIS_TIME + msg.getID());
									jedis.del(Constants.OPENFIRE + msg.getID());
									if (!msg.getBody().equals("0") && !msg.getBody().equals("4")) {
										Log.error("DBNReceiptRedisThreadUtil  save   message  by   DbnSaveGroupThread ：" + msg);
										jedis.zadd(Constants.OPENFIRE+Constants.OFFLINE_GROUP_MAP, System.currentTimeMillis(), msgId);
										jedis.set(Constants.OPENFIRE+Constants.OFFLINE_GROUP+msgId, message.toString());
									}
								}
							}
						} else {
							jedis.zrem(Constants.OPENFIRE + Constants.NXIN_OFFLINE_MAP, msg.getID());
							jedis.del(Constants.OPENFIRE + msg.getID());
							jedis.del(Constants.OPENFIRE + Constants.REDIS_TIME + msg.getID());
							Log.error("删除时间为空消息：" + msg);
						}
					} else {
						jedis.zrem(Constants.OPENFIRE + Constants.NXIN_OFFLINE_MAP, msgId);
						jedis.del(Constants.OPENFIRE + msgId);
						jedis.del(Constants.OPENFIRE + Constants.REDIS_TIME + msgId);
						Log.error("删除消息id为空消息：" + msgId);
					}
				}
			}
		} catch (Exception e) {
			Log.error("DBNReceiptRedisThreadUtil send message is error :" + e);
		} finally {
			pool.returnResourceObject(jedis);
		}
	}

	public void shutdown() {
		runFlag = false;
		Log.info("[DBNReceiptRedisThreadUtil]已停止......");
	}
}
