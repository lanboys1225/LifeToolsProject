/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : life-tools

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-03 17:29:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(30) NOT NULL COMMENT '群名称',
  `group_number` varchar(80) NOT NULL COMMENT '群对外 ID',
  `group_owner_id` bigint(20) NOT NULL COMMENT '群主 user_id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `group_owner_id` (`group_owner_id`),
  CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`group_owner_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for group_approval
-- ----------------------------
DROP TABLE IF EXISTS `group_approval`;
CREATE TABLE `group_approval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL COMMENT '群 id',
  `group_join_user_id` bigint(20) NOT NULL COMMENT '申请入群者 id',
  `approval_user_id` bigint(20) NOT NULL COMMENT '审核者 id',
  `approval_time` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `approval_status` varchar(20) DEFAULT 'approvaling' COMMENT '审核状态  approvaling success fail',
  `approval_fail_cause` varchar(255) DEFAULT NULL COMMENT '审核失败原因',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uindex` (`group_id`,`group_join_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL COMMENT '群 id',
  `user_id` bigint(20) NOT NULL COMMENT '用户 id',
  `group_role` varchar(20) DEFAULT 'member' COMMENT '角色 creator',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uindex` (`group_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `group_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `group_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `phone` varchar(15) NOT NULL COMMENT '用户注册手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `is_delete` varchar(20) NOT NULL DEFAULT 'N' COMMENT 'Y 已删除 N 未删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uindex` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `log_type` varchar(30) DEFAULT NULL COMMENT '日志类型 register login resetPassword',
  `user_id` bigint(30) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `login_status` varchar(20) DEFAULT NULL COMMENT '0 登录成功 1 退出登录 2 用户不存在 3 用户被删除 4 未设置登录密码 5 登录密码错误 6 密码错误次数超限5次',
  `token` varchar(255) DEFAULT NULL,
  `token_expire_time` datetime DEFAULT NULL COMMENT 'token过期时间',
  `ip` varchar(30) DEFAULT NULL COMMENT 'ip地址',
  `version` varchar(30) DEFAULT NULL COMMENT 'app 版本',
  `device_id` varchar(255) DEFAULT NULL COMMENT '手机 设备id',
  `platform` varchar(30) DEFAULT NULL COMMENT '平台 ios android pc',
  `channel` varchar(30) DEFAULT NULL COMMENT 'app 渠道id 如 yingyongbao',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
