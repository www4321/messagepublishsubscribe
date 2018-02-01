/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50505
Source Host           : 10.108.171.217:3306
Source Database       : DataPlatform

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-1-30 10:32:33
*/

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
CREATE TABLE If Not Exists `users` (
  `userName` varchar(32) DEFAULT NULL COMMENT '用户名',
  `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

