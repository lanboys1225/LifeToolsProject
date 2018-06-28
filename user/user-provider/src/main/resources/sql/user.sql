/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : life-tools

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-28 18:50:45
*/

SET FOREIGN_KEY_CHECKS=0;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '13556000000', '123456', 'lanboys', 'lanboys', 'N', '2018-06-27 16:39:15', '2018-06-27 16:41:53');
