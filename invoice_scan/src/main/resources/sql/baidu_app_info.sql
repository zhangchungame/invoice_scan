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

 Date: 02/01/2020 00:02:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for baidu_app_info
-- ----------------------------
DROP TABLE IF EXISTS `baidu_app_info`;
CREATE TABLE `baidu_app_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `invoice_used_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of baidu_app_info
-- ----------------------------
INSERT INTO `baidu_app_info` VALUES (1, '17864821', 'PmQ2gx4KovbDekph57GbscFd', 'GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6', 1);
INSERT INTO `baidu_app_info` VALUES (2, '18153664', 'r3Xb9mFIBzfbIX2GhTjKZYvu', 'XIiUGSf643FcwlYbVaYYdIqHUSsWDEwq', 1);

SET FOREIGN_KEY_CHECKS = 1;
