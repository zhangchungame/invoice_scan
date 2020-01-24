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

 Date: 02/01/2020 00:03:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for scan_image_by_day_detail
-- ----------------------------
DROP TABLE IF EXISTS `scan_image_by_day_detail`;
CREATE TABLE `scan_image_by_day_detail`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `deal_date` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `upload_num` int(11) DEFAULT NULL,
  `scan_num` int(11) DEFAULT NULL,
  `insert_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `deal_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
