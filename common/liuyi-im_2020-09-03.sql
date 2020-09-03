# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.21)
# Database: liuyi-im
# Generation Time: 2020-09-03 07:48:30 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `app_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `app_key` varchar(32) DEFAULT NULL,
  `app_name` varchar(16) NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:有效,0:无效',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group`;

CREATE TABLE `group` (
  `group_id` bigint(20) NOT NULL COMMENT '会话 ID',
  `group_name` varchar(32) DEFAULT NULL COMMENT '会话 name',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table group_member
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_member`;

CREATE TABLE `group_member` (
  `group_id` bigint(20) NOT NULL COMMENT '关联会话ID',
  `user_id` bigint(20) NOT NULL COMMENT '会话持有人 ID',
  `member_name` varchar(24) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `receiver_id` bigint(20) unsigned NOT NULL,
  `message_id` bigint(20) unsigned NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `message_type` tinyint(1) DEFAULT NULL,
  `message_body` varchar(1024) DEFAULT '' COMMENT '1:有效,0:无效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `send_time` datetime DEFAULT NULL,
  `sender_id` bigint(20) NOT NULL,
  PRIMARY KEY (`receiver_id`,`message_id`),
  KEY `receiver_id` (`receiver_id`,`message_id`,`message_type`,`sender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table message_id_section
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message_id_section`;

CREATE TABLE `message_id_section` (
  `section_id` bigint(20) NOT NULL,
  `current_id` bigint(20) DEFAULT NULL,
  `max_id` bigint(20) DEFAULT NULL,
  `step` bigint(20) DEFAULT '10000',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table message_id_section_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message_id_section_user`;

CREATE TABLE `message_id_section_user` (
  `user_id` bigint(20) NOT NULL,
  `section_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `section_id` (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `portrait` varchar(256) NOT NULL DEFAULT '' COMMENT '头像',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:有效,0:无效',
  `app_key` varchar(32) DEFAULT NULL,
  `outer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user_device
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_device`;

CREATE TABLE `user_device` (
  `user_id` bigint(20) NOT NULL COMMENT 'UserID',
  `sdk_name` varchar(32) DEFAULT NULL COMMENT 'SDK名称',
  `sdk_version` varchar(8) DEFAULT NULL COMMENT 'SDK版本',
  `device_label` varchar(128) DEFAULT NULL COMMENT '设备名',
  `device_id` varchar(64) DEFAULT NULL COMMENT '设备ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_connect_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后连接时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
