package com.dbn.openfire.plugin.test;

import java.util.Collection;

import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.packet.Presence;

public class PresenceTest {
	
	public static void main(String[] args) {
		PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();
		Collection<Presence> presences = presenceManager.getPresences("xiaosong");
		for(Presence pre:presences){
		System.out.println(pre);	
		}
	}
}
