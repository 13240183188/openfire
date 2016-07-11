package com.dbn.openfire.plugin.interceptor;

import java.util.Collection;

import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

public class Interceptor implements PacketInterceptor{
	private PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();
	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incoming, boolean processed) throws PacketRejectedException {
		if(incoming && processed && (packet instanceof Message)){
			Message msg = (Message)packet;
			System.out.println("msg:"+msg);
			System.out.println("------------------------------");
			Collection<Presence> presences = presenceManager.getPresences(msg.getFrom().toBareJID().split("@")[0]);
			for(Presence pre: presences){
				System.out.println("pre:"+pre);
			}
		}
		
	}
	
}
