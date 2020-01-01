/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : invoice

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 02/01/2020 00:02:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for baidu_app_process_times
-- ----------------------------
DROP TABLE IF EXISTS `baidu_app_process_times`;
CREATE TABLE `baidu_app_process_times`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scan_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `process_times` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of baidu_app_process_times
-- ----------------------------
INSERT INTO `baidu_app_process_times` VALUES (1, 'invoice', 2);

SET FOREIGN_KEY_CHECKS = 1;
