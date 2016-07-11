# $Revision$
# $Date$

INSERT INTO ofVersion (name, version) VALUES ('dbn', 1);

CREATE TABLE dbn_group_room(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	roomId varchar(100) NOT NULL COMMENT '����id',
	topic varchar(100) NOT NULL COMMENT '��������',
	hostJID varchar(100) NOT NULL COMMENT 'Ⱥ��jid',
	createTime datetime NOT NULL COMMENT '����ʱ��',
	isDismiss int(1) DEFAULT 0 COMMENT '�Ƿ��ɢ:1 ��ɢ;0 ����',
	PRIMARY KEY (id,roomId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Ⱥ��-�����';

CREATE TABLE dbn_group_member(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	roomId varchar(100) NOT NULL COMMENT '����id',
	username varchar(100) NOT NULL COMMENT '��Աid',
	memberJID varchar(100) NOT NULL COMMENT '��Աjid',
	createTime datetime NOT NULL COMMENT '����ʱ��',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Ⱥ��-��Ա��';

CREATE TABLE dbn_group_msg(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	roomId varchar(100) NOT NULL COMMENT '����id',
	fromJID varchar(100) NOT NULL COMMENT '������jid',
	msgType varchar(30) NOT NULL COMMENT '��Ϣ����',
	content varchar(1000) DEFAULT '' COMMENT '��Ϣ����',
	url varchar(200) DEFAULT '' COMMENT '��ý���ַ',
	property varchar(500) DEFAULT '' COMMENT '��Ϣ��������',
	createTime datetime NOT NULL COMMENT '����ʱ��',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Ⱥ��-��Ϣ��';


CREATE TABLE dbn_msg(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	fromJID varchar(100) NOT NULL COMMENT '������jid',
	toJID varchar(100) NOT NULL COMMENT '������jid',
	msgType varchar(30) NOT NULL COMMENT '��Ϣ����',
	content varchar(1000) DEFAULT '' COMMENT '��Ϣ����',
	url varchar(200) DEFAULT '' COMMENT '��ý���ַ',
	property varchar(500) DEFAULT '' COMMENT '��Ϣ��������',
	createTime datetime NOT NULL COMMENT '����ʱ��',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='����-��Ϣ��';

CREATE TABLE dbn_token(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	JID varchar(100) NOT NULL COMMENT '�û�jid',
	token varchar(100) NOT NULL COMMENT '�豸token',
	createTime datetime NOT NULL COMMENT '����ʱ��',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='����-ƻ���豸token��';


