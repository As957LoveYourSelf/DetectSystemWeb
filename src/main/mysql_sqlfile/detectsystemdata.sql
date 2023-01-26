/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : detectsystemdata

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 26/01/2023 09:56:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building`  (
  `bname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `floors` int(0) NULL DEFAULT NULL,
  `clsNo` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `isUsing` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`clsNo`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of building
-- ----------------------------
INSERT INTO `building` VALUES ('教学楼一', 6, '1-101', NULL);
INSERT INTO `building` VALUES ('教学楼二', 7, '2-101', NULL);
INSERT INTO `building` VALUES ('教学楼三', 7, '3-101', NULL);
INSERT INTO `building` VALUES ('教学楼四', 5, '4-101', NULL);
INSERT INTO `building` VALUES ('教学楼五', 8, '5-101', NULL);
INSERT INTO `building` VALUES ('综合楼A', 11, 'A-101', NULL);
INSERT INTO `building` VALUES ('综合楼B', 11, 'B-101', NULL);
INSERT INTO `building` VALUES ('综合楼C', 13, 'C-101', NULL);

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `className` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `collage` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade` int(0) NOT NULL,
  `major` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `studentCount` int(0) NULL DEFAULT NULL,
  `teacherCount` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`className`) USING BTREE,
  INDEX `collage`(`collage`) USING BTREE,
  INDEX `className`(`className`) USING BTREE,
  INDEX `grade`(`grade`) USING BTREE,
  CONSTRAINT `classes_ibfk_3` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `classes_ibfk_4` FOREIGN KEY (`grade`) REFERENCES `grade` (`grade`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------

-- ----------------------------
-- Table structure for collage
-- ----------------------------
DROP TABLE IF EXISTS `collage`;
CREATE TABLE `collage`  (
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collage
-- ----------------------------
INSERT INTO `collage` VALUES ('人工智能学院');
INSERT INTO `collage` VALUES ('体育科学学院');
INSERT INTO `collage` VALUES ('化学化工学院');
INSERT INTO `collage` VALUES ('教育科学学院');
INSERT INTO `collage` VALUES ('生物与纳米科学学院');
INSERT INTO `collage` VALUES ('电子电气工程学院');
INSERT INTO `collage` VALUES ('美术与设计学院');
INSERT INTO `collage` VALUES ('航空航天工程学院');
INSERT INTO `collage` VALUES ('计算机科学与技术学院');
INSERT INTO `collage` VALUES ('音乐与舞蹈学院');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `cname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `collage` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  INDEX `teacher`(`teacher`) USING BTREE,
  INDEX `major`(`major`) USING BTREE,
  INDEX `collage`(`collage`) USING BTREE,
  INDEX `cname`(`cname`) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`teacher`) REFERENCES `teachers` (`tname`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`major`) REFERENCES `majors` (`majorName`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_ibfk_3` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for faces
-- ----------------------------
DROP TABLE IF EXISTS `faces`;
CREATE TABLE `faces`  (
  `fid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `picturePath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`fid`) USING BTREE,
  INDEX `user name`(`uname`) USING BTREE,
  INDEX `user id`(`uid`) USING BTREE,
  CONSTRAINT `user id` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user name` FOREIGN KEY (`uname`) REFERENCES `users` (`uname`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of faces
-- ----------------------------

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `grade` int(0) NOT NULL,
  PRIMARY KEY (`grade`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (2019);
INSERT INTO `grade` VALUES (2020);
INSERT INTO `grade` VALUES (2021);
INSERT INTO `grade` VALUES (2022);
INSERT INTO `grade` VALUES (2023);

-- ----------------------------
-- Table structure for majors
-- ----------------------------
DROP TABLE IF EXISTS `majors`;
CREATE TABLE `majors`  (
  `majorName` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `collage` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`majorName`) USING BTREE,
  INDEX `collage`(`collage`) USING BTREE,
  CONSTRAINT `majors_ibfk_1` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of majors
-- ----------------------------

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `sno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `college` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cls` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `grade` int(0) NULL DEFAULT NULL,
  `major` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sno`) USING BTREE,
  INDEX `college`(`college`) USING BTREE,
  INDEX `class`(`cls`) USING BTREE,
  INDEX `grade`(`grade`) USING BTREE,
  INDEX `sname`(`sname`) USING BTREE,
  CONSTRAINT `students_ibfk_3` FOREIGN KEY (`college`) REFERENCES `collage` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `students_ibfk_4` FOREIGN KEY (`cls`) REFERENCES `classes` (`className`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `students_ibfk_5` FOREIGN KEY (`grade`) REFERENCES `grade` (`grade`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `students_ibfk_6` FOREIGN KEY (`sno`) REFERENCES `users` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `students_ibfk_7` FOREIGN KEY (`sname`) REFERENCES `users` (`uname`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers`  (
  `tno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `leadclass` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无',
  `teachCourse` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teachClass` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  INDEX `lead_class`(`leadclass`) USING BTREE,
  INDEX `tno`(`tno`) USING BTREE,
  INDEX `tname`(`tname`) USING BTREE,
  INDEX `teachCourse`(`teachCourse`) USING BTREE,
  INDEX `teacheClass`(`teachClass`) USING BTREE,
  CONSTRAINT `teachers_ibfk_3` FOREIGN KEY (`leadclass`) REFERENCES `classes` (`className`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teachers_ibfk_4` FOREIGN KEY (`tno`) REFERENCES `users` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teachers_ibfk_5` FOREIGN KEY (`tname`) REFERENCES `users` (`uname`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teachers_ibfk_6` FOREIGN KEY (`teachCourse`) REFERENCES `course` (`cname`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teachers_ibfk_7` FOREIGN KEY (`teachClass`) REFERENCES `classes` (`className`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachers
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `uname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uage` int(0) NULL DEFAULT NULL,
  `uphone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `registerTime` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` int(0) NULL DEFAULT NULL,
  `rcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `online` int(0) NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `fid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `utype` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `uname`(`uname`) USING BTREE,
  INDEX `face id`(`fid`) USING BTREE,
  CONSTRAINT `face id` FOREIGN KEY (`fid`) REFERENCES `faces` (`fid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('admin', '1', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'admin');

SET FOREIGN_KEY_CHECKS = 1;
