-- 时间银行 SQL

DROP DATABASE epm;
CREATE DATABASE epm;
USE epm;

--用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `avatar` varchar(200) NOT NULL COMMENT '头像',
  `openid` varchar(50) NOT NULL,
  `info` varchar(1000) NOT NULL,
  `creat_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 评论信息表
DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论或回复的id，回复有专门的reply_id值',
  `reply_id` int(11) NOT NULL DEFAULT 0 COMMENT '回复评论的id，若为评论则取0，若是回复则取对应的comment_id',
  `comment_content` text DEFAULT NULL,
  `city_id` int(11) NOT NULL DEFAULT 0 COMMENT '评论地区',
  `user_id` int(11) NOT NULL COMMENT '用于join user表，获取头像，姓名',
  `comment_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_time` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `ix_reply` (`reply_id`),
  KEY `ix_region` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 省份信息表
DROP TABLE IF EXISTS `province_detail`;
CREATE TABLE `province_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '市区id',
  `province_id` int(11) NOT NULL COMMENT '省id',
  `province_name` varchar(50) NOT NULL DEFAULT 'XX省' COMMENT '评论省份',
  `city_name` varchar(50) NOT NULL DEFAULT 'YY市' COMMENT '评论市区',
  PRIMARY KEY (`id`),
  KEY `ix_province` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
