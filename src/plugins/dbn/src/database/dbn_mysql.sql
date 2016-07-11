# $Revision$
# $Date$

INSERT INTO ofVersion (name, version) VALUES ('dbn', 1);

CREATE TABLE dbn_group_room(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	roomId varchar(100) NOT NULL COMMENT '房间id',
	topic varchar(100) NOT NULL COMMENT '房间主题',
	hostJID varchar(100) NOT NULL COMMENT '群主jid',
	createTime datetime NOT NULL COMMENT '创建时间',
	isDismiss int(1) DEFAULT 0 COMMENT '是否解散:1 解散;0 正常',
	PRIMARY KEY (id,roomId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='群聊-房间表';

CREATE TABLE dbn_group_member(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	roomId varchar(100) NOT NULL COMMENT '房间id',
	username varchar(100) NOT NULL COMMENT '成员id',
	memberJID varchar(100) NOT NULL COMMENT '成员jid',
	createTime datetime NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='群聊-成员表';

CREATE TABLE dbn_group_msg(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	roomId varchar(100) NOT NULL COMMENT '房间id',
	fromJID varchar(100) NOT NULL COMMENT '发送者jid',
	msgType varchar(30) NOT NULL COMMENT '消息类型',
	content varchar(1000) DEFAULT '' COMMENT '消息内容',
	url varchar(200) DEFAULT '' COMMENT '多媒体地址',
	property varchar(500) DEFAULT '' COMMENT '消息附加属性',
	createTime datetime NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='群聊-消息表';


CREATE TABLE dbn_msg(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	fromJID varchar(100) NOT NULL COMMENT '发送者jid',
	toJID varchar(100) NOT NULL COMMENT '接收者jid',
	msgType varchar(30) NOT NULL COMMENT '消息类型',
	content varchar(1000) DEFAULT '' COMMENT '消息内容',
	url varchar(200) DEFAULT '' COMMENT '多媒体地址',
	property varchar(500) DEFAULT '' COMMENT '消息附加属性',
	createTime datetime NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='单聊-消息表';

CREATE TABLE dbn_token(
	Id bigint(19) NOT NULL AUTO_INCREMENT,
	JID varchar(100) NOT NULL COMMENT '用户jid',
	token varchar(100) NOT NULL COMMENT '设备token',
	createTime datetime NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='单聊-苹果设备token表';


