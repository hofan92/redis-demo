/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.235-Master
Source Server Version : 50717
Source Host           : 192.168.1.235:3306
Source Database       : redis_demo_db

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-19 11:25:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for security_user
-- ----------------------------
DROP TABLE IF EXISTS `security_user`;
CREATE TABLE `security_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `real_name` varchar(5) NOT NULL COMMENT '用户真实姓名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `user_name` varchar(10) NOT NULL COMMENT '用户名，不是真实姓名',
  `user_address` varchar(100) NOT NULL COMMENT '用户地址',
  `occupation_level` int(4) unsigned NOT NULL COMMENT '职位级别',
  `unit_id` varchar(32) NOT NULL COMMENT '所属单位ID',
  `unit_name` varchar(20) NOT NULL COMMENT '公司名称',
  `unit_type` varchar(4) NOT NULL COMMENT '单位类型：电力公司、施工公司、设计公司',
  `department_id` varchar(32) NOT NULL COMMENT '所属部门ID',
  `department_name` varchar(10) NOT NULL COMMENT '部门名称',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `role_name` varchar(10) NOT NULL COMMENT '角色名称',
  `telephone` bigint(11) unsigned NOT NULL COMMENT '用户手机号码',
  `valid` tinyint(1) unsigned NOT NULL COMMENT '是否有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_UNITTYPE_USERNAME` (`user_name`,`unit_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户表';

