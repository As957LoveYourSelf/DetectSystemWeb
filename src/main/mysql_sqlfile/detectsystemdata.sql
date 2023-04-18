/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80027
Source Host           : localhost:3306
Source Database       : detectsystemdata

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2023-04-18 18:10:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `bname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `floors` int NOT NULL,
  `clsNo` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `isOrder` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`clsNo`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `className` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade` int NOT NULL,
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `studentCount` int DEFAULT '0',
  `teacherCount` int DEFAULT '0',
  `headmaster` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`className`),
  KEY `className` (`className`),
  KEY `collage` (`college`),
  KEY `grade` (`grade`),
  KEY `major` (`major`),
  KEY `headmaster` (`headmaster`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for course_address_person
-- ----------------------------
DROP TABLE IF EXISTS `course_address_person`;
CREATE TABLE `course_address_person` (
  `classname` varchar(30) DEFAULT NULL,
  `personCourseNo` int DEFAULT NULL,
  `address` varchar(10) DEFAULT NULL,
  `time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  KEY `classname` (`classname`),
  KEY `personCourseNo` (`personCourseNo`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for course_address_public
-- ----------------------------
DROP TABLE IF EXISTS `course_address_public`;
CREATE TABLE `course_address_public` (
  `classname` varchar(30) DEFAULT NULL,
  `publicCourseNo` int DEFAULT NULL,
  `address` varchar(10) DEFAULT NULL,
  `time` varchar(15) DEFAULT NULL,
  KEY `classname` (`classname`),
  KEY `publicCourseNo` (`publicCourseNo`),
  KEY `address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for course_major
-- ----------------------------
DROP TABLE IF EXISTS `course_major`;
CREATE TABLE `course_major` (
  `cno` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(15) DEFAULT NULL,
  `major` varchar(15) DEFAULT NULL,
  `college` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `grade` int DEFAULT NULL,
  PRIMARY KEY (`cno`),
  KEY `cname` (`cname`),
  KEY `collage` (`college`),
  KEY `studygrade` (`grade`),
  KEY `major` (`major`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for course_public
-- ----------------------------
DROP TABLE IF EXISTS `course_public`;
CREATE TABLE `course_public` (
  `cno` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(15) DEFAULT NULL,
  `college` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `grade` int DEFAULT '2022',
  PRIMARY KEY (`cno`),
  KEY `collage` (`college`),
  KEY `cname` (`cname`),
  KEY `studygrade` (`grade`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for faces
-- ----------------------------
DROP TABLE IF EXISTS `faces`;
CREATE TABLE `faces` (
  `faceImg` mediumblob NOT NULL,
  `uid` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `faces_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `grade` int NOT NULL,
  PRIMARY KEY (`grade`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for majors
-- ----------------------------
DROP TABLE IF EXISTS `majors`;
CREATE TABLE `majors` (
  `majorName` varchar(15) NOT NULL,
  `college` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`majorName`),
  KEY `collage` (`college`),
  CONSTRAINT `majors_ibfk_1` FOREIGN KEY (`college`) REFERENCES `college` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for order_room
-- ----------------------------
DROP TABLE IF EXISTS `order_room`;
CREATE TABLE `order_room` (
  `clsNo` varchar(10) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `uno` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sign_detail
-- ----------------------------
DROP TABLE IF EXISTS `sign_detail`;
CREATE TABLE `sign_detail` (
  `course` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `classname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `sno` varchar(50) NOT NULL,
  `sname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cls` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade` int NOT NULL,
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`sno`),
  KEY `sname` (`sname`),
  KEY `collage` (`college`) USING BTREE,
  KEY `grade` (`grade`),
  KEY `major` (`major`),
  KEY `cls` (`cls`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for study_major_course
-- ----------------------------
DROP TABLE IF EXISTS `study_major_course`;
CREATE TABLE `study_major_course` (
  `sno` varchar(50) DEFAULT NULL,
  `cno` int DEFAULT NULL,
  KEY `sno` (`sno`),
  KEY `cno` (`cno`),
  CONSTRAINT `study_major_course_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `students` (`sno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `study_major_course_ibfk_2` FOREIGN KEY (`cno`) REFERENCES `course_major` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for study_public_course
-- ----------------------------
DROP TABLE IF EXISTS `study_public_course`;
CREATE TABLE `study_public_course` (
  `sno` varchar(50) DEFAULT NULL,
  `cpno` int DEFAULT NULL,
  KEY `sno` (`sno`),
  KEY `cpno` (`cpno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teachcls
-- ----------------------------
DROP TABLE IF EXISTS `teachcls`;
CREATE TABLE `teachcls` (
  `tno` varchar(50) DEFAULT NULL,
  `classname` varchar(30) DEFAULT NULL,
  `major` varchar(15) DEFAULT NULL,
  `grade` int DEFAULT NULL,
  KEY `tno` (`tno`),
  KEY `classname` (`classname`),
  CONSTRAINT `teachcls_ibfk_1` FOREIGN KEY (`tno`) REFERENCES `teachers` (`tno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teachcls_ibfk_2` FOREIGN KEY (`classname`) REFERENCES `classes` (`className`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers` (
  `tno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `leadclass` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`tno`),
  KEY `tname` (`tname`),
  KEY `collage` (`college`),
  KEY `leadclass` (`leadclass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teach_major_course
-- ----------------------------
DROP TABLE IF EXISTS `teach_major_course`;
CREATE TABLE `teach_major_course` (
  `tno` varchar(50) NOT NULL,
  `cno` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teach_public_course
-- ----------------------------
DROP TABLE IF EXISTS `teach_public_course`;
CREATE TABLE `teach_public_course` (
  `tno` varchar(50) DEFAULT NULL,
  `cpno` int DEFAULT NULL,
  KEY `tno` (`tno`),
  KEY `cpno` (`cpno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for todo
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo` (
  `title` varchar(30) DEFAULT NULL,
  `context` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `uname` varchar(50) NOT NULL,
  `uid` varchar(50) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e',
  `uage` int DEFAULT NULL,
  `uphone` varchar(20) DEFAULT NULL,
  `online` bit(1) DEFAULT b'0',
  `birthday` datetime DEFAULT NULL,
  `utype` varchar(10) NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `introduce` varchar(255) DEFAULT 'Hello World',
  `sign` bit(1) NOT NULL DEFAULT b'0',
  `addfacecount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `uname` (`uname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP TRIGGER IF EXISTS `deletecs`;
DELIMITER ;;
CREATE TRIGGER `deletecs` AFTER DELETE ON `course_major` FOR EACH ROW delete from study_major_course where cno=OLD.cno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deletetcs`;
DELIMITER ;;
CREATE TRIGGER `deletetcs` AFTER DELETE ON `course_major` FOR EACH ROW delete from teach_major_course where cno=OLD.cno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deletepcs`;
DELIMITER ;;
CREATE TRIGGER `deletepcs` AFTER DELETE ON `course_public` FOR EACH ROW delete from study_public_course where cpno=OLD.cno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deletetpcs`;
DELIMITER ;;
CREATE TRIGGER `deletetpcs` AFTER DELETE ON `course_public` FOR EACH ROW delete from teach_public_course where cpno=OLD.cno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `addstudent`;
DELIMITER ;;
CREATE TRIGGER `addstudent` AFTER INSERT ON `students` FOR EACH ROW update classes set studentCount=studentCount+1 where className=NEW.cls
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert2user_student`;
DELIMITER ;;
CREATE TRIGGER `insert2user_student` AFTER INSERT ON `students` FOR EACH ROW REPLACE into users(uid, uname, utype, sex) values (NEW.sno, NEW.sname, 'student', NEW.sex)
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteuser_student`;
DELIMITER ;;
CREATE TRIGGER `deleteuser_student` AFTER DELETE ON `students` FOR EACH ROW delete from users where uid=OLD.sno
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deletestudent`;
DELIMITER ;;
CREATE TRIGGER `deletestudent` AFTER DELETE ON `students` FOR EACH ROW update classes set studentCount=studentCount-1 where className=OLD.cls
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `addteacher`;
DELIMITER ;;
CREATE TRIGGER `addteacher` AFTER INSERT ON `teachcls` FOR EACH ROW update classes set teacherCount=teacherCount+1 where className=NEW.classname
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteteacher`;
DELIMITER ;;
CREATE TRIGGER `deleteteacher` AFTER DELETE ON `teachcls` FOR EACH ROW update classes set teacherCount=teacherCount-1 where className=OLD.classname
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `headmaster_add`;
DELIMITER ;;
CREATE TRIGGER `headmaster_add` AFTER INSERT ON `teachers` FOR EACH ROW update classes set headmaster=NEW.tname where className=NEW.leadclass
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert2user_teacher`;
DELIMITER ;;
CREATE TRIGGER `insert2user_teacher` AFTER INSERT ON `teachers` FOR EACH ROW replace into users(uid, uname, utype, sex) values (NEW.tno, NEW.tname, 'teacher', NEW.sex)
;
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_teacher`;
DELIMITER ;;
CREATE TRIGGER `delete_teacher` AFTER DELETE ON `teachers` FOR EACH ROW delete from users where uid=OLD.tno
;;
DELIMITER ;
