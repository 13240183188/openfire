package com.dbn.openfire.plugin.constant;

/**
 * Ӧ�÷�����ؾ�̬���û�����
 * 
 * @author lyf
 * 
 */
public class ServerConstants {
	// MC���Ե�ַ
	// public static String MEMCACHE_ADDRESS = "10.200.1.104:11211";
	// MC��ʽ��ַ
	// public static String MEMCACHE_ADDRESS = "10.200.3.20:11211";
	// //MC��ׯ��ʽ��������ַ
	// public static String MEMCACHE_ADDRESS = "10.201.20.19:11211";
	// MCȨ�� ����
	public static int[] MEMCACHE_ADDRESS_WEIGHTS = new int[] { 1, 1 };
	// MCȨ�� ����
	// public static int [] MEMCACHE_ADDRESS_WEIGHTS= new int[]{1};
	// MC���ӳش�С
	public static int MEMCACHE_CONNECTIONPOOLSIZE = 15;
	// MC��ʱʱ��
	public static long MEMCACHED_TIMEOUT = 5000l;
	// ��ִ�汾��
	public static String MEMCACHE_RECEIVED_LESS_VERSION = "1.1.1";
	// �����˺ŷ�����Ϣ�汾�ж�
	public static String MEMCACHE_PUBLIC_VERSION = "2.2.0";
	// ��ҳ�˷����Լ�
	public static String MEMCACHE_TO_ONESELF_BY_WEB = "1.3.2";
	// MC����jid��ȡ�汾��ǰ׺
	public static String MEMCACHE_USERJID_VERSION_KEY = "userjid_version_";
}
