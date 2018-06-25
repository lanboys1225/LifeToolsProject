/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : life-tools

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-26 11:37:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(30) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL COMMENT '0 登录成功 1 退出登录 2 用户不存在 3 用户被删除 4 未设置登录密码 5 登录密码错误 6 密码错误次数超限5次',
  `token` varchar(255) DEFAULT NULL,
  `token_expire_time` datetime DEFAULT NULL COMMENT 'token过期时间',
  `date` datetime NOT NULL COMMENT '调用时间',
  `ip` varchar(30) DEFAULT NULL COMMENT 'ip地址',
  `version` varchar(30) DEFAULT NULL COMMENT 'app 版本',
  `device_id` varchar(255) DEFAULT NULL COMMENT '手机 设备id',
  `platform` varchar(30) DEFAULT NULL COMMENT '平台 ios android pc',
  `channel` varchar(30) DEFAULT NULL COMMENT 'app 渠道id 如 yingyongbao',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
