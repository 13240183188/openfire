package org.jivesoftware.openfire.dbn.dbnUtils;

import java.util.UUID;

public class GenerateIdUtils {
	public static String generateUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}
}
