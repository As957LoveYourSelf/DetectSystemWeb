/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80030
Source Host           : localhost:3306
Source Database       : detectsystemdata

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2023-02-09 21:11:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `bname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `floors` int DEFAULT NULL,
  `clsNo` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `isUsing` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`clsNo`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `className` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `collage` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade` int NOT NULL,
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `studentCount` int DEFAULT '0',
  `teacherCount` int DEFAULT '0',
  `headmaster` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`className`),
  KEY `className` (`className`),
  KEY `collage` (`collage`),
  KEY `grade` (`grade`),
  KEY `major` (`major`),
  KEY `headmaster` (`headmaster`),
  CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `classes_ibfk_2` FOREIGN KEY (`grade`) REFERENCES `grade` (`grade`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `classes_ibfk_3` FOREIGN KEY (`major`) REFERENCES `majors` (`majorName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `classes_ibfk_4` FOREIGN KEY (`headmaster`) REFERENCES `teachers` (`tname`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for collage
-- ----------------------------
DROP TABLE IF EXISTS `collage`;
CREATE TABLE `collage` (
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cno` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(15) DEFAULT NULL,
  `major` varchar(15) DEFAULT NULL,
  `collage` varchar(15) DEFAULT NULL,
  `studygrade` int DEFAULT NULL,
  `teach_count` int DEFAULT '0',
  PRIMARY KEY (`cno`),
  KEY `cname` (`cname`),
  KEY `collage` (`collage`),
  KEY `studygrade` (`studygrade`),
  KEY `major` (`major`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`studygrade`) REFERENCES `grade` (`grade`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_3` FOREIGN KEY (`major`) REFERENCES `majors` (`majorName`) ON DELETE CASCADE ON UPDATE CASCADE
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
  KEY `address` (`address`),
  CONSTRAINT `course_address_person_ibfk_1` FOREIGN KEY (`classname`) REFERENCES `classes` (`className`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_address_person_ibfk_2` FOREIGN KEY (`personCourseNo`) REFERENCES `course` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_address_person_ibfk_3` FOREIGN KEY (`address`) REFERENCES `building` (`clsNo`) ON DELETE SET NULL ON UPDATE CASCADE
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
  KEY `address` (`address`),
  CONSTRAINT `course_address_public_ibfk_1` FOREIGN KEY (`classname`) REFERENCES `classes` (`className`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_address_public_ibfk_2` FOREIGN KEY (`publicCourseNo`) REFERENCES `course_public` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_address_public_ibfk_3` FOREIGN KEY (`address`) REFERENCES `building` (`clsNo`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for course_public
-- ----------------------------
DROP TABLE IF EXISTS `course_public`;
CREATE TABLE `course_public` (
  `cno` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(15) DEFAULT NULL,
  `collage` varchar(15) DEFAULT NULL,
  `studygrade` int DEFAULT '2022',
  `teach_count` int DEFAULT '0',
  PRIMARY KEY (`cno`),
  KEY `collage` (`collage`),
  KEY `cname` (`cname`),
  KEY `studygrade` (`studygrade`),
  CONSTRAINT `course_public_ibfk_1` FOREIGN KEY (`studygrade`) REFERENCES `grade` (`grade`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_public_ibfk_2` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for faces
-- ----------------------------
DROP TABLE IF EXISTS `faces`;
CREATE TABLE `faces` (
  `fid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `picturePath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uid` varchar(50) NOT NULL,
  PRIMARY KEY (`fid`) USING BTREE,
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
  `collage` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`majorName`),
  KEY `collage` (`collage`),
  CONSTRAINT `majors_ibfk_1` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `sno` varchar(50) NOT NULL,
  `sname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `collage` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cls` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade` int NOT NULL,
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`sno`),
  KEY `sname` (`sname`),
  KEY `collage` (`collage`) USING BTREE,
  KEY `grade` (`grade`),
  KEY `major` (`major`),
  KEY `cls` (`cls`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `users` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `students_ibfk_2` FOREIGN KEY (`grade`) REFERENCES `grade` (`grade`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `students_ibfk_3` FOREIGN KEY (`major`) REFERENCES `majors` (`majorName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `students_ibfk_4` FOREIGN KEY (`cls`) REFERENCES `classes` (`className`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `students_ibfk_5` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for studycourse
-- ----------------------------
DROP TABLE IF EXISTS `studycourse`;
CREATE TABLE `studycourse` (
  `sno` varchar(50) DEFAULT NULL,
  `cno` int DEFAULT NULL,
  KEY `sno` (`sno`),
  KEY `cno` (`cno`),
  CONSTRAINT `studycourse_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `students` (`sno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `studycourse_ibfk_2` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for study_public_course
-- ----------------------------
DROP TABLE IF EXISTS `study_public_course`;
CREATE TABLE `study_public_course` (
  `sno` varchar(50) DEFAULT NULL,
  `cpno` int DEFAULT NULL,
  KEY `sno` (`sno`),
  KEY `cpno` (`cpno`),
  CONSTRAINT `study_public_course_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `students` (`sno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `study_public_course_ibfk_2` FOREIGN KEY (`cpno`) REFERENCES `course_public` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teachcls
-- ----------------------------
DROP TABLE IF EXISTS `teachcls`;
CREATE TABLE `teachcls` (
  `tno` varchar(50) DEFAULT NULL,
  `classname` varchar(30) DEFAULT NULL,
  KEY `tno` (`tno`),
  KEY `classname` (`classname`),
  CONSTRAINT `teachcls_ibfk_1` FOREIGN KEY (`tno`) REFERENCES `teachers` (`tno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teachcls_ibfk_2` FOREIGN KEY (`classname`) REFERENCES `classes` (`className`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teachcourse
-- ----------------------------
DROP TABLE IF EXISTS `teachcourse`;
CREATE TABLE `teachcourse` (
  `tno` varchar(50) NOT NULL,
  `cno` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers` (
  `tno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `collage` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `leadclass` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`tno`),
  KEY `tname` (`tname`),
  KEY `collage` (`collage`),
  KEY `leadclass` (`leadclass`),
  CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`tno`) REFERENCES `users` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teachers_ibfk_2` FOREIGN KEY (`collage`) REFERENCES `collage` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teachers_ibfk_3` FOREIGN KEY (`leadclass`) REFERENCES `classes` (`className`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for teach_public_course
-- ----------------------------
DROP TABLE IF EXISTS `teach_public_course`;
CREATE TABLE `teach_public_course` (
  `tno` varchar(50) DEFAULT NULL,
  `cpno` int DEFAULT NULL,
  KEY `tno` (`tno`),
  KEY `cpno` (`cpno`),
  CONSTRAINT `teach_public_course_ibfk_1` FOREIGN KEY (`tno`) REFERENCES `teachers` (`tno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teach_public_course_ibfk_2` FOREIGN KEY (`cpno`) REFERENCES `course_public` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `registerTime` varchar(20) DEFAULT NULL,
  `state` bit(1) DEFAULT b'1',
  `rcode` varchar(255) DEFAULT NULL,
  `online` bit(1) DEFAULT b'0',
  `birthday` datetime DEFAULT NULL,
  `fid` varchar(50) DEFAULT NULL,
  `utype` varchar(10) NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `uname` (`uname`),
  KEY `face id` (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP TRIGGER IF EXISTS `addstudent`;
DELIMITER ;;
CREATE TRIGGER `addstudent` AFTER INSERT ON `students` FOR EACH ROW update classes set studentCount=studentCount+1 where className=NEW.cls
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert2user_student`;
DELIMITER ;;
CREATE TRIGGER `insert2user_student` AFTER INSERT ON `students` FOR EACH ROW REPLACE into users(uid, uname, utype, sex) values (NEW.sno, NEW.sname, 'student', NEW.sex)
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteuser_student`;
DELIMITER ;;
CREATE TRIGGER `deleteuser_student` AFTER DELETE ON `students` FOR EACH ROW delete from users where uid=OLD.sno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deletestudent`;
DELIMITER ;;
CREATE TRIGGER `deletestudent` AFTER DELETE ON `students` FOR EACH ROW update classes set studentCount=studentCount-1 where className=OLD.cls
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `addteacher`;
DELIMITER ;;
CREATE TRIGGER `addteacher` AFTER INSERT ON `teachcls` FOR EACH ROW update classes set teacherCount=teacherCount+1 where className=NEW.classname
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteteacher`;
DELIMITER ;;
CREATE TRIGGER `deleteteacher` AFTER DELETE ON `teachcls` FOR EACH ROW update classes set teacherCount=teacherCount-1 where className=OLD.classname
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `headmaster_add`;
DELIMITER ;;
CREATE TRIGGER `headmaster_add` AFTER INSERT ON `teachers` FOR EACH ROW update classes set headmaster=NEW.tname where className=NEW.leadclass
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert2user_teacher`;
DELIMITER ;;
CREATE TRIGGER `insert2user_teacher` AFTER INSERT ON `teachers` FOR EACH ROW replace into users(uid, uname, utype, sex) values (NEW.tno, NEW.tname, 'teacher', NEW.sex)
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_teacher`;
DELIMITER ;;
CREATE TRIGGER `delete_teacher` AFTER DELETE ON `teachers` FOR EACH ROW delete from users where uid=OLD.tno
;;
DELIMITER ;
