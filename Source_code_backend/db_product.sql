-- Active: 1747214105640@@127.0.0.1@3306@db_product

/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 127.0.0.1:3306
 Source Schema         : db_product141

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 21/04/2025 06:29:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `managerid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The primary key is automatically numbered',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'username',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'password',
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'realname',
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'contact',
  PRIMARY KEY (`managerid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'manager table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES (1, 'SuperAdmin', '96e79218965eb72c92a549dd5a330112', 'SuperAdmin', '13313559595');
INSERT INTO `manager` VALUES (2, 'Admin1', '96e79218965eb72c92a549dd5a330112', 'Admin1', '18921200308');

-- ----------------------------
-- Table structure for members
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members`  (
  `membersid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The primary key is automatically numbered',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'username',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'password',
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'realname',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sex',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'birthday',
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'contact',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'image',
  `regdate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'regdate',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'college',
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'major',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'address',
  `iscooled` int(2) NULL DEFAULT 0 COMMENT '0normal 1freezed',
  `qq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `idcard` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'identity card',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `pwdback_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'The verification code used when retrieving the password',
  `grades1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Standby field',
  `grades2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Standby field',
  `grades3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Standby field',
  `grades4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Standby field',
  `grades5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Standby field',
  `grades6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Standby field',
  PRIMARY KEY (`membersid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of members
-- ----------------------------
INSERT INTO `members` VALUES (4, 'User1', '96e79218965eb72c92a549dd5a330112', 'xjtluer', 'female', '2025-04-17', '19962173796', 'upload_files/25.jpg', '2025-04-17', 'xjtlu', '', 'XX', 0, '2306821402', '2252461', 'thzx469@163.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `members` VALUES (5, 'User2', '96e79218965eb72c92a549dd5a330112', 'Yize Liu', 'male', '2025-04-17', '15035596763', 'upload_files/26.jpg', '2025-04-17', 'xjtlu', 'ICSmajor', 'XX', 0, '2445354941', '2254472', '2445354941@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for projectinfo
-- ----------------------------
DROP TABLE IF EXISTS `projectinfo`;
CREATE TABLE `projectinfo`  (
  `projectinfoid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The primary key is automatically numbered',
  `projectinfo_typeid` int(11) NULL DEFAULT 0 COMMENT 'Foreign key: projectinfo_typeid',
  `projectinfoname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'projectinfoname',
  `status` int(11) NULL DEFAULT 0 COMMENT '0normal   1offline',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'image',
  `remark` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'remark',
  `createdate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'createdate',
  `iscooled` int(11) NULL DEFAULT 0 COMMENT '0normal   1unreviewed  2not passed',
  `projectinfo_files` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'projectinfo_files',
  `membersid` int(11) NULL DEFAULT 0 COMMENT 'Foreign key: membersid',
  `keyword_table` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'keyword',
  `projectinfo_0` int(11) NULL DEFAULT 0 COMMENT 'Standby field',
  `projectinfo_istop` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'not' COMMENT 'top',
  `projectinfo_isflv` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'not' COMMENT 'carousel',
  `projectinfo_hits` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 'hits',
  `projectinfo_artist` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'artist',
  `projectinfo_album` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'album',
  `projectinfo_3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Standby field',
  `projectinfo_4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Standby field',
  `projectinfo_5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Standby field',
  `projectinfo_6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Standby field',
  PRIMARY KEY (`projectinfoid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 192 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Music table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of projectinfo
-- ----------------------------
INSERT INTO `projectinfo` VALUES (1, 5, 'Runaway', 0, 'upload_files/1001.jpg', '<p>Kanye said, "Who designed the sidewalk, who designed the fountain, who designed the stop sign, who designed the traffic light? These things are so ubiquitous that the people who designed them are forgotten." He said what he wants is to be "forgotten.""</p>', '2025-04-17 20:00:00', 0, 'upload_files/1.mp3', 4, NULL, 1, 'Yes', 'Yes', '0', 'Ye', '《My Beautiful Dark Twisted Fantasy》', '', '', '', '');
INSERT INTO `projectinfo` VALUES (2, 4, 'Welcome To New York', 0, 'upload_files/1002.jpg', '<p>The New York subway is dirty and smelly, yet stepping outside brings you the neon-lit spectacle of Times Square. Gazing at the Manhattan skyline with its thousands of lights, you still worry endlessly about where you will live next year. You have walked through narrow streets splashed with filthy water, yet you have also witnessed Central Park\'s stunning beauty through the changing seasons. Taylor lives here, and so do I. It is a kind of luck—to wake up in this city, anxious yet striving to live. Welcome to New York.</p>', '2025-04-17 20:00:00', 0, 'upload_files/2.mp3', 4, NULL, 51, 'Yes', 'Yes', '55', 'Taylor Swift', '1989', '', '', '', '');
INSERT INTO `projectinfo` VALUES (3, 7, 'No Talking', 0, 'upload_files/1003.jpg', '<p>In the past, when shopping for clothes or ordering food at restaurants, I could always tell exactly what she wanted at a glance. Yet each time, I brushed it off as mere coincidence. A long while after we broke up, we met again at a class reunion. When buying shaved ice, she was browsing the samples, and just as she was about to order, I again chose exactly what she wanted. She kept pressing me for an explanation; I just smiled. Later, amidst the noisy KTV, she sat across from me and texted again to ask. "I just recognize that look in your eyes when you see something you like—because I lived in them for years."</p>', '2025-04-17 20:00:00', 0, 'upload_files/3.mp3', 4, '不要说话', 20, 'Yes', 'Yes', '0', 'Eason Chan', 'The theme song of the film', '', '', '', '');
INSERT INTO `projectinfo` VALUES (4, 6, 'Greatest Works of Art', 0, 'upload_files/1004.jpg', '<p>Madness of the age, king of music——all creation bows before my symphony.</p>', '2025-04-17 20:00:00', 0, 'upload_files/4.mp3', 4, NULL, 1, 'Yes', 'Yes', '0', 'Jay Chou', '《Greatest Works of Art》', '', '', '', '');
-- ----------------------------
-- Table structure for projectinfo_type
-- ----------------------------
DROP TABLE IF EXISTS `projectinfo_type`;
CREATE TABLE `projectinfo_type`  (
  `projectinfo_typeid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The primary key is automatically numbered',
  `projectinfo_typename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'typename',
  PRIMARY KEY (`projectinfo_typeid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Music Classification Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of projectinfo_type
-- ----------------------------
INSERT INTO `projectinfo_type` VALUES (3, 'J-Pop/K-Pop');
INSERT INTO `projectinfo_type` VALUES (4, 'English');
INSERT INTO `projectinfo_type` VALUES (5, 'Electronic');
INSERT INTO `projectinfo_type` VALUES (6, 'Classical');
INSERT INTO `projectinfo_type` VALUES (7, 'Chinese');

SET FOREIGN_KEY_CHECKS = 1;