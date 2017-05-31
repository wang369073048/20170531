# Host: localhost  (Version: 5.6.23)
# Date: 2015-03-18 09:36:51
# Generator: MySQL-Front 5.3  (Build 4.13)

/*!40101 SET NAMES utf8 */;

#
# Source for table "lrm_groupinfo"
#

DROP TABLE IF EXISTS `lrm_groupinfo`;
CREATE TABLE `lrm_groupinfo` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` bigint(11) NOT NULL,
  `GROUP_NAME` varchar(20) NOT NULL,
  `SORT_NUMBER` int(5) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `CREATOR` bigint(11) NOT NULL,
  `UPDATE_USER` bigint(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETE_MARK` int(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "lrm_groupinfo"
#

INSERT INTO `lrm_groupinfo` VALUES (1,0,'组织机构',1,'2012-12-06 13:05:17',1,NULL,NULL,0),(2,1,'测试部门123',13,'2015-03-09 14:38:32',1,1,'2015-03-09 14:38:38',1);

#
# Source for table "lrm_log"
#

DROP TABLE IF EXISTS `lrm_log`;
CREATE TABLE `lrm_log` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(20) NOT NULL,
  `IP` varchar(20) NOT NULL,
  `OPERATE_TIME` datetime NOT NULL,
  `OPERATE_MODULE` varchar(20) NOT NULL,
  `OPERATE_ACTION` varchar(20) NOT NULL,
  `OPERATE_OBJECT` varchar(300) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Data for table "lrm_log"
#

INSERT INTO `lrm_log` VALUES (1,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 11:27:21','系统菜单管理','删除图片','系统菜单管理'),(3,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 12:21:35','系统菜单管理','新增','dsa'),(4,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 12:21:42','系统菜单管理','修改','dsa'),(5,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 12:21:46','系统菜单管理','删除','dsa'),(6,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:38:32','部门信息管理','新增','测试部门'),(7,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:38:38','部门信息管理','修改','测试部门123'),(8,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:38:48','部门信息管理','删除','测试部门123'),(9,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:42:02','人员信息管理','新增','dsadsa'),(10,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:42:13','人员信息管理','修改','dsadsa321'),(11,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:42:20','人员信息管理','修改','dsadsa321'),(12,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:42:23','人员信息管理','删除','dsadsa321'),(13,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:53:28','角色信息管理','新增','dsadsadsa'),(14,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:53:34','角色信息管理','修改','aaa'),(15,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:53:38','角色信息管理','删除','aaa'),(16,'超级管理员','0:0:0:0:0:0:0:1','2015-03-09 14:54:21','系统菜单管理','删除','数据字典管理');

#
# Source for table "lrm_menuinfo"
#

DROP TABLE IF EXISTS `lrm_menuinfo`;
CREATE TABLE `lrm_menuinfo` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` bigint(11) NOT NULL,
  `MENU_NAME` varchar(20) NOT NULL,
  `MENU_ENNAME` varchar(20) DEFAULT NULL,
  `MENU_URL` varchar(200) DEFAULT NULL,
  `SORT_NUMBER` int(5) NOT NULL,
  `MENU_IMG` varchar(200) DEFAULT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `CREATOR` bigint(11) NOT NULL,
  `UPDATE_USER` bigint(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETE_MARK` int(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "lrm_menuinfo"
#

INSERT INTO `lrm_menuinfo` VALUES (1,0,'系统管理','System','1',1,NULL,'2012-11-30 11:01:04',1,1,NULL,0),(2,1,'系统菜单管理','System Menu','modules/sysMenu/sysMenuTree.jsp',1,NULL,'2013-02-19 16:59:40',1,1,'2013-10-22 17:20:29',0),(3,1,'部门信息管理','Group Info','modules/org/groupTree.jsp',2,'upload/sysMenuImg/2013/10/18/4efd4f9e-a07b-4198-a486-9c624b77dd3b.png','2013-02-19 17:00:01',1,1,'2013-10-18 13:42:41',0),(4,1,'角色授权管理','Role EmPower','modules/role/roleList.jsp',4,'upload/sysMenuImg/2013/10/18/058d21ad-0c6e-4008-aa8f-b3abcb8ae70d.png','2013-02-19 17:01:34',1,1,'2013-10-18 13:44:05',0),(5,1,'人员信息管理','User Info','modules/org/groupUserTree.jsp',3,'upload/sysMenuImg/2013/10/18/012bbaa9-fdb8-4c8c-92be-e300daf6be1a.png','2013-02-19 17:01:13',1,1,'2013-10-18 13:43:57',0),(6,1,'系统日志管理','System Log','modules/log/logList.jsp',5,'upload/sysMenuImg/2013/10/18/4dfb8a4b-be51-462e-937f-9fdc5d8b4891.png','2013-10-18 13:34:44',1,1,'2013-10-18 13:37:37',0);

#
# Source for table "lrm_menurole"
#

DROP TABLE IF EXISTS `lrm_menurole`;
CREATE TABLE `lrm_menurole` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `MENU_ID` bigint(11) NOT NULL,
  `ROLE_ID` bigint(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Data for table "lrm_menurole"
#

INSERT INTO `lrm_menurole` VALUES (8,1,1),(9,2,1),(10,3,1),(11,5,1),(12,4,1),(13,6,1);

#
# Source for table "lrm_roleinfo"
#

DROP TABLE IF EXISTS `lrm_roleinfo`;
CREATE TABLE `lrm_roleinfo` (
  `ID` bigint(11) NOT NULL,
  `ROLE_NAME` varchar(20) NOT NULL,
  `ROLE_CODE` varchar(20) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `CREATOR` bigint(11) NOT NULL,
  `UPDATE_USER` bigint(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETE_MARK` int(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "lrm_roleinfo"
#

INSERT INTO `lrm_roleinfo` VALUES (1,'超级管理员','sysAdmin','2012-11-28 12:41:47',0,NULL,NULL,0);

#
# Source for table "lrm_userinfo"
#

DROP TABLE IF EXISTS `lrm_userinfo`;
CREATE TABLE `lrm_userinfo` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` varchar(20) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `USER_NAME` varchar(20) NOT NULL,
  `ENABLED` int(1) NOT NULL,
  `USER_TYPE` int(1) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `CREATOR` bigint(11) NOT NULL,
  `UPDATE_USER` bigint(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETE_MARK` int(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "lrm_userinfo"
#

INSERT INTO `lrm_userinfo` VALUES (1,'admin','1','超级管理员',0,1,'2012-11-28 10:39:34',0,1,'2013-10-18 16:31:21',0),(2,'dsadsa321','dddd','dsadsa321',0,1,'2015-03-09 14:42:02',1,1,'2015-03-09 14:42:20',1);

#
# Source for table "lrm_usergroup"
#

DROP TABLE IF EXISTS `lrm_usergroup`;
CREATE TABLE `lrm_usergroup` (
  `GROUP_ID` bigint(11) NOT NULL,
  `USER_ID` bigint(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`GROUP_ID`),
  KEY `FK667D0EA2AFEBA0EC` (`USER_ID`),
  KEY `FK667D0EA217E50AE` (`GROUP_ID`),
  CONSTRAINT `FK667D0EA217E50AE` FOREIGN KEY (`GROUP_ID`) REFERENCES `lrm_groupinfo` (`ID`),
  CONSTRAINT `FK667D0EA2AFEBA0EC` FOREIGN KEY (`USER_ID`) REFERENCES `lrm_userinfo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "lrm_usergroup"
#

INSERT INTO `lrm_usergroup` VALUES (1,1),(1,2);

#
# Source for table "lrm_userrole"
#

DROP TABLE IF EXISTS `lrm_userrole`;
CREATE TABLE `lrm_userrole` (
  `USER_ID` bigint(11) NOT NULL,
  `ROLE_ID` bigint(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `FK877457F344B70CC` (`ROLE_ID`),
  KEY `FK877457F3AFEBA0EC` (`USER_ID`),
  CONSTRAINT `FK877457F344B70CC` FOREIGN KEY (`ROLE_ID`) REFERENCES `lrm_roleinfo` (`ID`),
  CONSTRAINT `FK877457F3AFEBA0EC` FOREIGN KEY (`USER_ID`) REFERENCES `lrm_userinfo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "lrm_userrole"
#

INSERT INTO `lrm_userrole` VALUES (1,1);

#
# Source for table "zyk_city"
#

DROP TABLE IF EXISTS `zyk_city`;
CREATE TABLE `zyk_city` (
  `cityId` int(11) NOT NULL AUTO_INCREMENT COMMENT '省市Id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型',
  `parentId` varchar(100) DEFAULT '' COMMENT '父级Id',
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

#
# Data for table "zyk_city"
#

INSERT INTO `zyk_city` VALUES (1,'北京',1,'0'),(2,'昌平',2,'1'),(3,'通州',2,'1'),(4,'北京1',1,'0'),(5,'北京1',2,'4'),(6,'北京2',1,'0'),(7,'北京2',2,'6'),(8,'北京3',1,'0'),(9,'北京3',2,'8'),(10,'北京4',1,'0'),(11,'北京4',2,'10'),(12,'北京5',1,'0'),(13,'北京5',2,'12'),(14,'北京6',1,'0'),(15,'北京6',2,'14'),(16,'北京7',1,'0'),(17,'北京7',2,'16'),(18,'北京8',1,'0'),(19,'北京8',2,'18'),(20,'北京9',1,'0'),(21,'北京9',2,'20'),(22,'北京10',1,'0'),(23,'北京10',2,'22'),(24,'鍖椾含',1,'0'),(25,'鍖椾含',2,'24');

#
# Source for table "zyk_course"
#

DROP TABLE IF EXISTS `zyk_course`;
CREATE TABLE `zyk_course` (
  `courseId` varchar(100) NOT NULL DEFAULT '' COMMENT '课程ID',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '所属资源库id',
  `Fullname` varchar(100) NOT NULL DEFAULT '' COMMENT '课程名称',
  `Specialty` varchar(100) NOT NULL DEFAULT '' COMMENT '所属专业',
  `Author` varchar(100) DEFAULT NULL COMMENT '授课老师',
  `courseType` varchar(100) DEFAULT NULL COMMENT '课程类型',
  `courseLevel` varchar(100) DEFAULT NULL COMMENT '课程级别',
  `Description` varchar(1000) DEFAULT NULL COMMENT '课程简介',
  `modifiedTime` datetime DEFAULT NULL COMMENT '更新时间（含创建时间）',
  PRIMARY KEY (`courseId`,`zykId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_course"
#


#
# Source for table "zyk_course_resource_relation"
#

DROP TABLE IF EXISTS `zyk_course_resource_relation`;
CREATE TABLE `zyk_course_resource_relation` (
  `relationId` varchar(100) NOT NULL DEFAULT '' COMMENT '关系id',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '资源库id',
  `Courseid` varchar(100) NOT NULL DEFAULT '' COMMENT '课程id',
  `CourseModuleId` varchar(100) DEFAULT NULL COMMENT '课程模块id',
  `courseModule` varchar(100) NOT NULL DEFAULT '' COMMENT '课程模块名称',
  `Resourceid` varchar(100) NOT NULL DEFAULT '' COMMENT '资源id',
  PRIMARY KEY (`relationId`,`zykId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_course_resource_relation"
#


#
# Source for table "zyk_log"
#

DROP TABLE IF EXISTS `zyk_log`;
CREATE TABLE `zyk_log` (
  `logId` varchar(100) NOT NULL DEFAULT '' COMMENT '日志Id',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '资源库Id',
  `Time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `Userid` varchar(100) NOT NULL DEFAULT '' COMMENT '用户ID',
  `Ip` varchar(15) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `Equipment` varchar(255) DEFAULT NULL COMMENT '访问设备',
  `Courseid` varchar(100) NOT NULL DEFAULT '' COMMENT '课程ID',
  `ObjectType` varchar(100) NOT NULL DEFAULT '' COMMENT '操作对象所属模块',
  `ObjectId` varchar(100) NOT NULL DEFAULT '' COMMENT '操作对象路径',
  `Action` varchar(255) NOT NULL DEFAULT '' COMMENT '动作',
  PRIMARY KEY (`logId`,`zykId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_log"
#


#
# Source for table "zyk_questionbank"
#

DROP TABLE IF EXISTS `zyk_questionbank`;
CREATE TABLE `zyk_questionbank` (
  `qbId` varchar(100) NOT NULL DEFAULT '' COMMENT '专业ID题库标识',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '所属资源库id',
  `Title` varchar(100) DEFAULT '' COMMENT '专业名称+xxx题库',
  `QuestionNum` int(11) DEFAULT NULL COMMENT '题库题目数量',
  `ObjQuesNum` int(11) DEFAULT NULL COMMENT '客观题数量',
  `SubQuesNum` int(11) DEFAULT NULL COMMENT '主观题数量',
  `CitedQuesNum` int(11) DEFAULT NULL COMMENT '被测试、练习引用题目数量',
  `QuesUsingNum` int(11) DEFAULT NULL COMMENT '题目使用总次数',
  PRIMARY KEY (`qbId`,`zykId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_questionbank"
#


#
# Source for table "zyk_resource"
#

DROP TABLE IF EXISTS `zyk_resource`;
CREATE TABLE `zyk_resource` (
  `resourceId` varchar(100) NOT NULL DEFAULT '' COMMENT '资源素材id',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '所属资源库id',
  `Title` varchar(100) NOT NULL DEFAULT '' COMMENT '素材名称',
  `Source` varchar(100) NOT NULL DEFAULT '' COMMENT '素材来源',
  `Specialty` varchar(100) NOT NULL DEFAULT '' COMMENT '所属专业',
  `Course` varchar(100) NOT NULL DEFAULT '' COMMENT '所属课程',
  `Knowledge` varchar(100) NOT NULL DEFAULT '' COMMENT '对应知识点/技能点',
  `Keywords` varchar(100) NOT NULL DEFAULT '' COMMENT '关键词',
  `Subject` varchar(100) NOT NULL DEFAULT '' COMMENT '适用对象',
  `Instruction` varchar(100) NOT NULL DEFAULT '' COMMENT '应用类型',
  `mediaType` varchar(100) NOT NULL DEFAULT '' COMMENT '媒体类型',
  `language` varchar(100) NOT NULL DEFAULT '' COMMENT '资源语言',
  `fileFormat` varchar(100) NOT NULL DEFAULT '' COMMENT '文件格式',
  `Filesize` int(11) NOT NULL DEFAULT '0' COMMENT '文件大小，单位字节(byte)',
  `modifiedTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间（含创建时间）',
  PRIMARY KEY (`resourceId`,`zykId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_resource"
#


#
# Source for table "zyk_user"
#

DROP TABLE IF EXISTS `zyk_user`;
CREATE TABLE `zyk_user` (
  `userId` varchar(100) NOT NULL DEFAULT '' COMMENT '用户Id',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '所属资源库id',
  `Username` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `Role` varchar(100) NOT NULL DEFAULT '' COMMENT '角色 （教师或学生、社会学习者、企业用户、其他）',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `telephone` varchar(100) DEFAULT NULL COMMENT '电话',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `institute` varchar(100) DEFAULT NULL COMMENT '所在单位',
  `province` varchar(100) DEFAULT NULL COMMENT '所在省份',
  `City` varchar(100) DEFAULT NULL COMMENT '所在地市',
  `Specialty` varchar(100) DEFAULT NULL COMMENT '所属专业',
  `createdTime` datetime DEFAULT NULL COMMENT '注册时间或创建时间',
  PRIMARY KEY (`userId`,`zykId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_user"
#


#
# Source for table "zyk_zyk"
#

DROP TABLE IF EXISTS `zyk_zyk`;
CREATE TABLE `zyk_zyk` (
  `id` varchar(100) NOT NULL DEFAULT '' COMMENT '主键ID',
  `zykId` varchar(100) NOT NULL DEFAULT '' COMMENT '资源库Id',
  `cityId` varchar(100) NOT NULL DEFAULT '' COMMENT '省市Id',
  `Fullname` varchar(100) DEFAULT NULL COMMENT '专业名称',
  `SpecialtyCategory` varchar(100) DEFAULT NULL COMMENT '所属专业大类名称',
  `SpecialtyName` varchar(100) DEFAULT NULL COMMENT '所属专业名称',
  `MoreSpecialty` varchar(100) DEFAULT NULL COMMENT '适用的专业目录名称',
  `website` varchar(100) DEFAULT NULL COMMENT '资源库访问地址',
  `instituteInCharge` varchar(100) DEFAULT NULL COMMENT '项目主持单位',
  `personInCharge` varchar(100) DEFAULT NULL COMMENT '项目主持人',
  `cooperationInstitute` varchar(100) DEFAULT NULL COMMENT '联合建设单位',
  `modifiedDate` date DEFAULT NULL COMMENT '申请日期',
  `declarationDate` date DEFAULT NULL COMMENT '申报日期',
  `projectDate` date DEFAULT NULL COMMENT '立项时间',
  `accepDate` date DEFAULT NULL COMMENT '验收时间',
  `Status` varchar(100) NOT NULL DEFAULT '' COMMENT '资源库状态（1：申报中、2：待验收、3：已验收）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "zyk_zyk"
#

INSERT INTO `zyk_zyk` VALUES ('2c99ce9b4c219d9f014c21a396b80002','bde08e93db7b4646ad9ed65994d23061','1','数控技术','制造大类','机械设计制造类',NULL,NULL,'同方',NULL,NULL,NULL,NULL,'2015-03-16',NULL,'2'),('2c99ce9b4c21aa17014c21d6937f0001','bde08e93db7b4646ad9ed65994d23061','1','数控技术','制造大类','机械设计制造类',NULL,NULL,'同方2',NULL,NULL,NULL,NULL,'2015-03-16',NULL,'2');
