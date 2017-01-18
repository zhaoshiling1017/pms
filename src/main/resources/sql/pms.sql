/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.21-70.1-log : Database - pms
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `pms`;

/*Table structure for table `assignee` */

DROP TABLE IF EXISTS `assignee`;

CREATE TABLE `assignee` (
  `assignee_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `task_id` int(11) DEFAULT NULL COMMENT '工单ID',
  `user_id` int(11) DEFAULT NULL COMMENT '受理人ID',
  `result` varchar(50) DEFAULT NULL COMMENT '受理结果',
  `comment` text COMMENT '备注',
  `suggest_time` datetime DEFAULT NULL COMMENT '建议时间',
  `suggest_id` varchar(50) DEFAULT NULL COMMENT '建议人',
  PRIMARY KEY (`assignee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `assignee` */

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(30) DEFAULT NULL,
  `department_code` varchar(50) DEFAULT NULL,
  `parent_department_id` int(11) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `principal_person_id` int(11) DEFAULT NULL,
  `create_by_id` int(11) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `is_deleted` varchar(10) DEFAULT '0',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

/*Data for the table `department` */

insert  into `department`(`department_id`,`department_name`,`department_code`,`parent_department_id`,`level`,`type`,`principal_person_id`,`create_by_id`,`create_at`,`is_deleted`) 
values 
(1,'人力资源部','01',1,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(2,'生产调度室','02',2,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(3,'物资部','03',3,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(4,'安质部','04',4,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(5,'财务部','05',5,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(6,'新线办','06',6,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(7,'改造办','07',7,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(8,'企发、法规','08',8,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(9,'项目部','09',9,'1','部室',NULL,NULL,'2015-10-12 14:20:22','0'),
(10,'中心项目部','W0',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(11,'维修一项目部','W1',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(12,'维修二项目部','W2',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(13,'维修三项目部','W3',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(14,'维修四项目部','W4',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(15,'维修五项目部','W5',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(16,'维修六项目部','W6',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(17,'维修七项目部','W7',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0'),
(18,'维修八项目部','W8',9,'2','项目部',NULL,NULL,'2015-10-12 14:20:22','0');


/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '集团消息ID',
  `msg_title` varchar(255) DEFAULT NULL COMMENT '消息标题',
  `content` text COMMENT '消息内容',
  `publisher_id` int(11) DEFAULT NULL COMMENT '发布人ID',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `is_deleted` varchar(20) DEFAULT '0' COMMENT '删除标记',
  `status` varchar(20) DEFAULT NULL COMMENT '发送状态 ',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

/*Table structure for table `msg_file` */

DROP TABLE IF EXISTS `msg_file`;

CREATE TABLE `msg_file` (
  `msg_file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `msg_id` int(11) DEFAULT NULL COMMENT '集团消息ID',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `path` varchar(255) DEFAULT NULL COMMENT '附件地址',
  `create_by_id` int(11) DEFAULT NULL COMMENT '创建人',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除',
  `file_name` varchar(255) DEFAULT NULL COMMENT '附件名称',
  PRIMARY KEY (`msg_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `msg_file` */

/*Table structure for table `msg_role` */

DROP TABLE IF EXISTS `msg_role`;

CREATE TABLE `msg_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `msg_id` int(11) DEFAULT NULL COMMENT '集团消息ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `user_id` int(11) DEFAULT NULL COMMENT '人员ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '人员姓名',
  `status` varchar(20) DEFAULT '0' COMMENT '已读未读状态',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `is_deleted` varchar(20) DEFAULT '0' COMMENT '删除状态',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `msg_role` */

/*Table structure for table `node_role` */

DROP TABLE IF EXISTS `node_role`;

CREATE TABLE `node_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `node_id` int(11) DEFAULT NULL COMMENT '节点ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `procdef_id` int(11) DEFAULT NULL COMMENT '流程定义',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `node_role` */

INSERT INTO `node_role` VALUES ('1', '1', '15', '1');
INSERT INTO `node_role` VALUES ('2', '2', '6', '1');
INSERT INTO `node_role` VALUES ('3', '2', '7', '1');
INSERT INTO `node_role` VALUES ('4', '2', '8', '1');
INSERT INTO `node_role` VALUES ('5', '2', '9', '1');
INSERT INTO `node_role` VALUES ('6', '2', '10', '1');
INSERT INTO `node_role` VALUES ('7', '2', '11', '1');
INSERT INTO `node_role` VALUES ('8', '2', '12', '1');
INSERT INTO `node_role` VALUES ('9', '2', '13', '1');
INSERT INTO `node_role` VALUES ('10', '2', '14', '1');
INSERT INTO `node_role` VALUES ('11', '3', '3', '1');
INSERT INTO `node_role` VALUES ('12', '4', '15', '1');
INSERT INTO `node_role` VALUES ('13', '5', '16', '1');
INSERT INTO `node_role` VALUES ('14', '6', '15', '1');
INSERT INTO `node_role` VALUES ('15', '7', '3', '1');
INSERT INTO `node_role` VALUES ('16', '8', '6', '1');
INSERT INTO `node_role` VALUES ('17', '8', '7', '1');
INSERT INTO `node_role` VALUES ('18', '8', '8', '1');
INSERT INTO `node_role` VALUES ('19', '8', '9', '1');
INSERT INTO `node_role` VALUES ('20', '8', '10', '1');
INSERT INTO `node_role` VALUES ('21', '8', '11', '1');
INSERT INTO `node_role` VALUES ('22', '8', '12', '1');
INSERT INTO `node_role` VALUES ('23', '8', '13', '1');
INSERT INTO `node_role` VALUES ('24', '8', '14', '1');
INSERT INTO `node_role` VALUES ('25', '9', '17', '2');
INSERT INTO `node_role` VALUES ('26', '10', '6', '2');
INSERT INTO `node_role` VALUES ('27', '10', '7', '2');
INSERT INTO `node_role` VALUES ('28', '10', '8', '2');
INSERT INTO `node_role` VALUES ('29', '10', '9', '2');
INSERT INTO `node_role` VALUES ('30', '10', '10', '2');
INSERT INTO `node_role` VALUES ('31', '10', '11', '2');
INSERT INTO `node_role` VALUES ('32', '10', '12', '2');
INSERT INTO `node_role` VALUES ('33', '10', '13', '2');
INSERT INTO `node_role` VALUES ('34', '10', '14', '2');
INSERT INTO `node_role` VALUES ('35', '11', '18', '2');
INSERT INTO `node_role` VALUES ('36', '12', '3', '2');
INSERT INTO `node_role` VALUES ('37', '13', '17', '2');

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `procdef_id` int(11) DEFAULT NULL COMMENT '流程定义ID',
  `procinst_id` int(11) DEFAULT NULL COMMENT '流程实例ID',
  `notice_type` varchar(20) DEFAULT NULL COMMENT '通知类型',
  `remind_time` datetime DEFAULT NULL COMMENT '定时日期',
  `remind_type` varchar(20) DEFAULT NULL COMMENT '定时类型',
  `comment` text COMMENT '备注',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `created_by_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` varchar(20) DEFAULT '0' COMMENT '是否删除',
  `task_id` varchar(20) COMMENT '工单ID',
  `role_id` varchar(20) COMMENT '角色ID',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `notice` */

/*Table structure for table `notice_role` */

DROP TABLE IF EXISTS `notice_role`;

CREATE TABLE `notice_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `notice_Id` int(11) DEFAULT NULL COMMENT '系统消息ID',
  `user_id` int(11) DEFAULT NULL COMMENT '人员ID',
  `status` varchar(20) DEFAULT '0' COMMENT '已读未读状态',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收到时间',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `is_deleted` varchar(10) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `notice_role` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单、连接、按钮权限',
  `permission_str` varchar(50) DEFAULT NULL COMMENT '菜单权限辨识字符串',
  `parent_permission_id` int(11) DEFAULT NULL COMMENT '父级菜单ID',
  `parent_permission_name` varchar(50) DEFAULT NULL COMMENT '父级菜单名字',
  `comment` text COMMENT '备注,说明',
  `create_by_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` varchar(10) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '权限基础字段', '权限基础字段', '1', null, null, '1', '2016-10-31 10:37:47', '0');
INSERT INTO `permission` VALUES ('2', '系统管理', '系统管理', '1', null, null, '1', '2016-10-31 10:39:45', '0');
INSERT INTO `permission` VALUES ('3', '人员管理', '系统管理-人员管理', '2', '系统管理', null, '1', '2016-10-31 10:40:30', '0');
INSERT INTO `permission` VALUES ('4', '角色管理', '系统管理-角色管理', '2', '系统管理', null, '1', '2016-10-31 10:40:59', '0');
INSERT INTO `permission` VALUES ('5', '发起普通流程', '发起普通流程', '1', null, null, '1', '2016-10-31 10:49:08', '0');
INSERT INTO `permission` VALUES ('6', '发布集团消息', '发布集团消息', '1', null, null, '1', '2016-10-31 10:49:42', '0');
INSERT INTO `permission` VALUES ('7', '发起临时流程', '发起临时流程', '1', null, null, '1', '2016-10-31 10:50:08', '0');
INSERT INTO `permission` VALUES ('8', '进度查询', '进度查询', '1', null, null, '1', '2016-10-31 11:21:48', '0');
INSERT INTO `permission` VALUES ('9', '流程详情', '进度查询-流程详情', '8', '进度查询', null, '1', '2016-10-31 11:22:20', '0');
INSERT INTO `permission` VALUES ('10', '通知', '通知', '1', null, null, '1', '2016-10-31 11:22:52', '0');
INSERT INTO `permission` VALUES ('11', '集团消息', '通知-集团消息', '10', '通知', null, '1', '2016-10-31 11:23:45', '0');
INSERT INTO `permission` VALUES ('12', '发布消息', '集团消息-发布消息', '11', '集团消息', null, '1', '2016-10-31 11:26:12', '0');
INSERT INTO `permission` VALUES ('13', '删除', '集团消息-删除', '11', '集团消息', null, '1', '2016-10-31 11:26:15', '0');
INSERT INTO `permission` VALUES ('14', '消息通知', '通知-消息通知', '10', '通知', null, '1', '2016-10-31 11:28:20', '0');
INSERT INTO `permission` VALUES ('15', '删除', '消息通知-删除', '14', '消息通知', null, '1', '2016-10-31 11:30:16', '0');
INSERT INTO `permission` VALUES ('16', '标为已读', '消息通知-标为已读', '14', '消息通知', null, '1', '2016-10-31 11:30:18', '0');
INSERT INTO `permission` VALUES ('17', '系统通知', '通知-系统通知', '10', '通知', null, '1', '2016-10-31 11:30:55', '0');
INSERT INTO `permission` VALUES ('18', '删除', '系统通知-删除', '17', '系统通知', null, '1', '2016-10-31 11:31:28', '0');
INSERT INTO `permission` VALUES ('19', '标为已读', '系统通知-标为已读', '17', '系统通知', null, '1', '2016-10-31 11:31:52', '0');
INSERT INTO `permission` VALUES ('20', '任务管理', '任务管理', '1', null, null, '1', '2016-10-31 11:33:44', '0');
INSERT INTO `permission` VALUES ('21', '流程详情', '任务管理-流程详情', '20', '任务管理', null, '1', '2016-10-31 11:34:46', '0');
INSERT INTO `permission` VALUES ('22', '处理', '任务管理-处理', '20', '任务管理', null, '1', '2016-10-31 11:35:26', '0');
INSERT INTO `permission` VALUES ('23', '与我有关', '与我有关', '1', null, null, '1', '2016-10-31 11:36:09', '0');
INSERT INTO `permission` VALUES ('24', '流程详情', '与我有关-流程详情', '23', '与我有关', null, '1', '2016-10-31 11:36:41', '0');
INSERT INTO `permission` VALUES ('25', '统计报表', '统计报表', '1', null, null, '1', '2016-10-31 11:37:38', '0');
INSERT INTO `permission` VALUES ('26', '流程启动统计', '统计报表-流程启动统计', '25', '统计报表', null, '1', '2016-10-31 11:38:18', '0');
INSERT INTO `permission` VALUES ('27', '流程详情', '流程启动统计-流程详情', '26', '流程启动统计', null, '1', '2016-10-31 11:39:01', '0');
INSERT INTO `permission` VALUES ('28', '部门执行统计', '统计报表-部门执行统计', '25', '统计报表', null, '1', '2016-10-31 11:41:29', '0');
INSERT INTO `permission` VALUES ('29', '流程详情', '部门执行统计-流程详情', '28', '部门执行统计', null, '1', '2016-10-31 11:43:26', '0');
INSERT INTO `permission` VALUES ('30', '新增', '人员管理-新增', '3', '人员管理', null, '1', '2016-10-31 11:47:35', '0');
INSERT INTO `permission` VALUES ('31', '编辑', '人员管理-编辑', '3', '人员管理', null, '1', '2016-10-31 11:47:37', '0');
INSERT INTO `permission` VALUES ('32', '停用', '人员管理-停用', '3', '人员管理', null, '1', '2016-10-31 11:48:36', '0');
INSERT INTO `permission` VALUES ('33', '新增', '角色管理-新增', '4', '角色管理', null, '1', '2016-10-31 11:49:40', '0');
INSERT INTO `permission` VALUES ('34', '人员', '角色管理-人员', '4', '角色管理', null, '1', '2016-10-31 11:50:19', '0');
INSERT INTO `permission` VALUES ('35', '权限配置', '角色管理-权限配置', '4', '角色管理', null, '1', '2016-10-31 11:50:49', '0');
INSERT INTO `permission` VALUES ('36', '停用', '角色管理-停用', '4', '角色管理', null, '1', '2016-10-31 11:51:45', '0');


/*Table structure for table `proc_node` */

DROP TABLE IF EXISTS `proc_node`;

CREATE TABLE `proc_node` (
  `node_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程节点ID',
  `node_name` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `node_code` varchar(255) DEFAULT NULL COMMENT '节点编号',
  `node_type` varchar(20) DEFAULT NULL COMMENT '节点类型',
  `up_node_id` varchar(255) DEFAULT NULL COMMENT '上一节点ID',
  `next_node_id` varchar(255) DEFAULT NULL COMMENT '下一节点ID',
  `procdef_id` int(11) DEFAULT NULL COMMENT '流程定义ID',
  `created_by_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` varchar(20) DEFAULT '0' COMMENT '删除标记',
  `is_time` varchar(20) DEFAULT NULL COMMENT '是否限时',
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `proc_node` */

INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('1', '财务部下发', '', '0', '0', '2', '1', '1', '2016-10-14 17:29:11', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('2', '项目部提报', '', '0', '1', '3', '1', '1', '2016-10-14 17:30:13', '0', '0');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('3', '安质部审批', '', '1', '2', '4', '1', '1', '2016-10-14 17:31:23', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('4', '财务部提议', '', '0', '3', '5', '1', '1', '2016-10-14 17:32:50', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('5', '物资部采购', '', '0', '4', '6,7,8', '1', '1', '2016-10-14 17:33:25', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('6', '财务部备案', NULL, '0', '5', '', '1', '1', '2016-10-14 09:47:51', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('7', '安质部备案', NULL, '0', '5', NULL, '1', '1', '2016-10-14 11:27:15', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('8', '项目部接收', NULL, '0', '5', '-1', '1', '1', '2016-10-14 11:27:17', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('9', '培训部下发', NULL, '0', '0', '10', '2', '1', '2016-10-24 15:27:35', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('10', '项目部上报', NULL, '0', '9', '11', '2', '1', '2016-10-24 15:29:33', '0', '0');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('11', '开发岗初审', NULL, '1', '10', '12', '2', '1', '2016-10-24 15:32:03', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('12', '安质部接收', NULL, '0', '11', '13', '2', '1', '2016-10-24 15:33:57', '0', '1');
INSERT INTO `pms`.`proc_node` (`node_id`, `node_name`, `node_code`, `node_type`, `up_node_id`, `next_node_id`, `procdef_id`, `created_by_id`, `created_at`, `is_deleted`, `is_time`) VALUES ('13', '培训部接收', NULL, '0', '12', '-1', '2', '1', '2016-10-24 15:35:31', '0', '1');

/*Table structure for table `procdef` */

DROP TABLE IF EXISTS `procdef`;

CREATE TABLE `procdef` (
  `procdef_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程定义ID',
  `proc_name` varchar(255) DEFAULT NULL COMMENT '流程名称',
  `proc_code` varchar(255) DEFAULT NULL COMMENT '流程编号',
  `proc_type` varchar(20) DEFAULT NULL COMMENT '流程类型',
  `created_by_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` varchar(20) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`procdef_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `procdef` */

INSERT INTO `procdef` VALUES ('1', '项目部修理费、生产消耗费用、低值易耗费用专项管理工作流程', 'SGJH001', '0', '1', '2016-09-14 09:36:35', '0');
INSERT INTO `procdef` VALUES ('2', '操作类员工技能等级鉴定工作流程', 'SGJH002', '0', '1', '2016-10-24 15:27:26', '0');

/*Table structure for table `procdef_role` */

DROP TABLE IF EXISTS `procdef_role`;

CREATE TABLE `procdef_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `procdef_id` int(11) DEFAULT NULL COMMENT '流程定义ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `create_by_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` varchar(20) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `procdef_role` */

INSERT INTO `procdef_role` VALUES ('1', '1', '15', '1', '2016-09-07 10:06:49', '0');
INSERT INTO `procdef_role` VALUES ('2', '2', '17', '1', '2016-10-24 16:58:36', '0');
INSERT INTO `procdef_role` VALUES ('3', '1', '17', '1', '2016-10-24 17:03:10', '0');
INSERT INTO `procdef_role` VALUES ('4', '1', '1', '1', '2016-10-26 17:13:26', '0');
INSERT INTO `procdef_role` VALUES ('5', '2', '1', '1', '2016-11-02 10:10:25', '0');

/*Table structure for table `procinst` */

DROP TABLE IF EXISTS `procinst`;

CREATE TABLE `procinst` (
  `procinst_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程实例ID',
  `procdef_id` int(11) DEFAULT NULL COMMENT '流程定义ID',
  `proc_type` varchar(255) DEFAULT NULL COMMENT '流程类型	',
  `proc_name` varchar(255) DEFAULT NULL COMMENT '流程名称	',
  `procinst_code` varchar(255) DEFAULT NULL COMMENT '实例编号	',
  `status` varchar(20) DEFAULT NULL COMMENT '运行状态	',
  `start_time` datetime DEFAULT NULL COMMENT '流程启动时间',
  `end_time` datetime DEFAULT NULL COMMENT '流程结束时间',
  `comment` text COMMENT '备注',
  `start_person_id` int(11) DEFAULT NULL COMMENT '发起人ID	',
  `start_person_role_id`      int(11) COMMENT '发起人角色ID	',
  PRIMARY KEY (`procinst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

/*Data for the table `procinst` */

/*Table structure for table `procinst_file` */

DROP TABLE IF EXISTS `procinst_file`;

CREATE TABLE `procinst_file` (
  `procinst_file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程附件ID',
  `procinst_id` int(11) DEFAULT NULL COMMENT '流程实例ID',
  `task_id` int(11) DEFAULT NULL COMMENT '工单ID',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `path` varchar(255) DEFAULT NULL COMMENT '附件地址',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `proc_type` int(11) DEFAULT NULL COMMENT '类型',
  `create_by_id` int(11) DEFAULT NULL COMMENT '上传人',
  `node_id` int(11) DEFAULT NULL COMMENT '节点ID',
  `is_back` varchar(50) DEFAULT '0' COMMENT '退回标记',
  `upload_task_id` varchar(50) DEFAULT '0' COMMENT '上传附件的工单ID',
  `upload_node_id` varchar(50) DEFAULT '0' COMMENT '上传附件的节点ID',
  `upload_role_id` varchar(50) DEFAULT '0' COMMENT '上传附件角色ID',
  PRIMARY KEY (`procinst_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `procinst_file` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `create_by_id` int(11) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `is_deleted` varchar(10) DEFAULT '0',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('1', 'admin', '最高权限', '1', '2016-09-07 10:09:07', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('2', '安质室领导', NULL, '1', '2016-09-07 10:09:05', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('3', '安质部领导', NULL, '1', '2016-09-07 10:09:17', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('4', '生产调度室小调', NULL, '1', '2016-09-07 10:10:52', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('5', '维修调度室小调', NULL, '1', '2016-09-07 10:12:31', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('6', '项目部1部长', NULL, '1', '2016-11-03 16:37:31', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('7', '项目部2部长', NULL, '1', '2016-11-03 16:37:34', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('8', '项目部3部长', NULL, '1', '2016-11-03 16:37:36', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('9', '项目部4部长', NULL, '1', '2016-11-03 16:37:39', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('10', '项目部5部长', NULL, '1', '2016-11-03 16:37:41', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('11', '项目部6部长', NULL, '1', '2016-11-03 16:37:43', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('12', '项目部7部长', NULL, '1', '2016-11-03 16:37:47', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('13', '项目部8部长', NULL, '1', '2016-11-03 16:37:49', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('14', '项目部9部长', NULL, '1', '2016-11-03 16:37:51', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('15', '财务部领导', NULL, '1', '2016-11-03 16:37:53', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('16', '物资部部长', NULL, '1', '2016-11-03 16:37:55', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('17', '培训部主任', NULL, '1', '2016-10-24 16:25:44', '0');
INSERT INTO `pms`.`role` (`role_id`, `role_name`, `comment`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('18', '培训部开发岗', NULL, '1', '2016-10-24 16:26:15', '0');

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_permission` */

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('3', '2');
INSERT INTO `role_permission` VALUES ('4', '2');
INSERT INTO `role_permission` VALUES ('5', '2');
INSERT INTO `role_permission` VALUES ('6', '2');
INSERT INTO `role_permission` VALUES ('7', '2');
INSERT INTO `role_permission` VALUES ('8', '2');
INSERT INTO `role_permission` VALUES ('9', '2');
INSERT INTO `role_permission` VALUES ('10', '2');
INSERT INTO `role_permission` VALUES ('11', '2');
INSERT INTO `role_permission` VALUES ('12', '2');
INSERT INTO `role_permission` VALUES ('13', '2');
INSERT INTO `role_permission` VALUES ('14', '2');
INSERT INTO `role_permission` VALUES ('15', '2');
INSERT INTO `role_permission` VALUES ('16', '2');
INSERT INTO `role_permission` VALUES ('17', '2');
INSERT INTO `role_permission` VALUES ('18', '2');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('2', '3');
INSERT INTO `role_permission` VALUES ('3', '3');
INSERT INTO `role_permission` VALUES ('4', '3');
INSERT INTO `role_permission` VALUES ('5', '3');
INSERT INTO `role_permission` VALUES ('6', '3');
INSERT INTO `role_permission` VALUES ('7', '3');
INSERT INTO `role_permission` VALUES ('8', '3');
INSERT INTO `role_permission` VALUES ('9', '3');
INSERT INTO `role_permission` VALUES ('10', '3');
INSERT INTO `role_permission` VALUES ('11', '3');
INSERT INTO `role_permission` VALUES ('12', '3');
INSERT INTO `role_permission` VALUES ('13', '3');
INSERT INTO `role_permission` VALUES ('14', '3');
INSERT INTO `role_permission` VALUES ('15', '3');
INSERT INTO `role_permission` VALUES ('16', '3');
INSERT INTO `role_permission` VALUES ('17', '3');
INSERT INTO `role_permission` VALUES ('18', '3');
INSERT INTO `role_permission` VALUES ('1', '4');
INSERT INTO `role_permission` VALUES ('2', '4');
INSERT INTO `role_permission` VALUES ('3', '4');
INSERT INTO `role_permission` VALUES ('4', '4');
INSERT INTO `role_permission` VALUES ('5', '4');
INSERT INTO `role_permission` VALUES ('6', '4');
INSERT INTO `role_permission` VALUES ('7', '4');
INSERT INTO `role_permission` VALUES ('8', '4');
INSERT INTO `role_permission` VALUES ('9', '4');
INSERT INTO `role_permission` VALUES ('10', '4');
INSERT INTO `role_permission` VALUES ('11', '4');
INSERT INTO `role_permission` VALUES ('12', '4');
INSERT INTO `role_permission` VALUES ('13', '4');
INSERT INTO `role_permission` VALUES ('14', '4');
INSERT INTO `role_permission` VALUES ('15', '4');
INSERT INTO `role_permission` VALUES ('16', '4');
INSERT INTO `role_permission` VALUES ('17', '4');
INSERT INTO `role_permission` VALUES ('18', '4');
INSERT INTO `role_permission` VALUES ('1', '5');
INSERT INTO `role_permission` VALUES ('2', '5');
INSERT INTO `role_permission` VALUES ('3', '5');
INSERT INTO `role_permission` VALUES ('4', '5');
INSERT INTO `role_permission` VALUES ('5', '5');
INSERT INTO `role_permission` VALUES ('6', '5');
INSERT INTO `role_permission` VALUES ('7', '5');
INSERT INTO `role_permission` VALUES ('8', '5');
INSERT INTO `role_permission` VALUES ('9', '5');
INSERT INTO `role_permission` VALUES ('10', '5');
INSERT INTO `role_permission` VALUES ('11', '5');
INSERT INTO `role_permission` VALUES ('12', '5');
INSERT INTO `role_permission` VALUES ('13', '5');
INSERT INTO `role_permission` VALUES ('14', '5');
INSERT INTO `role_permission` VALUES ('15', '5');
INSERT INTO `role_permission` VALUES ('16', '5');
INSERT INTO `role_permission` VALUES ('17', '5');
INSERT INTO `role_permission` VALUES ('18', '5');
INSERT INTO `role_permission` VALUES ('1', '6');
INSERT INTO `role_permission` VALUES ('2', '6');
INSERT INTO `role_permission` VALUES ('3', '6');
INSERT INTO `role_permission` VALUES ('4', '6');
INSERT INTO `role_permission` VALUES ('5', '6');
INSERT INTO `role_permission` VALUES ('6', '6');
INSERT INTO `role_permission` VALUES ('7', '6');
INSERT INTO `role_permission` VALUES ('8', '6');
INSERT INTO `role_permission` VALUES ('9', '6');
INSERT INTO `role_permission` VALUES ('10', '6');
INSERT INTO `role_permission` VALUES ('11', '6');
INSERT INTO `role_permission` VALUES ('12', '6');
INSERT INTO `role_permission` VALUES ('13', '6');
INSERT INTO `role_permission` VALUES ('14', '6');
INSERT INTO `role_permission` VALUES ('15', '6');
INSERT INTO `role_permission` VALUES ('16', '6');
INSERT INTO `role_permission` VALUES ('17', '6');
INSERT INTO `role_permission` VALUES ('18', '6');
INSERT INTO `role_permission` VALUES ('1', '7');
INSERT INTO `role_permission` VALUES ('2', '7');
INSERT INTO `role_permission` VALUES ('3', '7');
INSERT INTO `role_permission` VALUES ('4', '7');
INSERT INTO `role_permission` VALUES ('5', '7');
INSERT INTO `role_permission` VALUES ('6', '7');
INSERT INTO `role_permission` VALUES ('7', '7');
INSERT INTO `role_permission` VALUES ('8', '7');
INSERT INTO `role_permission` VALUES ('9', '7');
INSERT INTO `role_permission` VALUES ('10', '7');
INSERT INTO `role_permission` VALUES ('11', '7');
INSERT INTO `role_permission` VALUES ('12', '7');
INSERT INTO `role_permission` VALUES ('13', '7');
INSERT INTO `role_permission` VALUES ('14', '7');
INSERT INTO `role_permission` VALUES ('15', '7');
INSERT INTO `role_permission` VALUES ('16', '7');
INSERT INTO `role_permission` VALUES ('17', '7');
INSERT INTO `role_permission` VALUES ('18', '7');
INSERT INTO `role_permission` VALUES ('1', '8');
INSERT INTO `role_permission` VALUES ('2', '8');
INSERT INTO `role_permission` VALUES ('3', '8');
INSERT INTO `role_permission` VALUES ('4', '8');
INSERT INTO `role_permission` VALUES ('5', '8');
INSERT INTO `role_permission` VALUES ('6', '8');
INSERT INTO `role_permission` VALUES ('7', '8');
INSERT INTO `role_permission` VALUES ('8', '8');
INSERT INTO `role_permission` VALUES ('9', '8');
INSERT INTO `role_permission` VALUES ('10', '8');
INSERT INTO `role_permission` VALUES ('11', '8');
INSERT INTO `role_permission` VALUES ('12', '8');
INSERT INTO `role_permission` VALUES ('13', '8');
INSERT INTO `role_permission` VALUES ('14', '8');
INSERT INTO `role_permission` VALUES ('15', '8');
INSERT INTO `role_permission` VALUES ('16', '8');
INSERT INTO `role_permission` VALUES ('17', '8');
INSERT INTO `role_permission` VALUES ('18', '8');
INSERT INTO `role_permission` VALUES ('1', '9');
INSERT INTO `role_permission` VALUES ('2', '9');
INSERT INTO `role_permission` VALUES ('3', '9');
INSERT INTO `role_permission` VALUES ('4', '9');
INSERT INTO `role_permission` VALUES ('5', '9');
INSERT INTO `role_permission` VALUES ('6', '9');
INSERT INTO `role_permission` VALUES ('7', '9');
INSERT INTO `role_permission` VALUES ('8', '9');
INSERT INTO `role_permission` VALUES ('9', '9');
INSERT INTO `role_permission` VALUES ('10', '9');
INSERT INTO `role_permission` VALUES ('11', '9');
INSERT INTO `role_permission` VALUES ('12', '9');
INSERT INTO `role_permission` VALUES ('13', '9');
INSERT INTO `role_permission` VALUES ('14', '9');
INSERT INTO `role_permission` VALUES ('15', '9');
INSERT INTO `role_permission` VALUES ('16', '9');
INSERT INTO `role_permission` VALUES ('17', '9');
INSERT INTO `role_permission` VALUES ('18', '9');
INSERT INTO `role_permission` VALUES ('1', '10');
INSERT INTO `role_permission` VALUES ('2', '10');
INSERT INTO `role_permission` VALUES ('3', '10');
INSERT INTO `role_permission` VALUES ('4', '10');
INSERT INTO `role_permission` VALUES ('5', '10');
INSERT INTO `role_permission` VALUES ('6', '10');
INSERT INTO `role_permission` VALUES ('7', '10');
INSERT INTO `role_permission` VALUES ('8', '10');
INSERT INTO `role_permission` VALUES ('9', '10');
INSERT INTO `role_permission` VALUES ('10', '10');
INSERT INTO `role_permission` VALUES ('11', '10');
INSERT INTO `role_permission` VALUES ('12', '10');
INSERT INTO `role_permission` VALUES ('13', '10');
INSERT INTO `role_permission` VALUES ('14', '10');
INSERT INTO `role_permission` VALUES ('15', '10');
INSERT INTO `role_permission` VALUES ('16', '10');
INSERT INTO `role_permission` VALUES ('17', '10');
INSERT INTO `role_permission` VALUES ('18', '10');
INSERT INTO `role_permission` VALUES ('1', '11');
INSERT INTO `role_permission` VALUES ('2', '11');
INSERT INTO `role_permission` VALUES ('3', '11');
INSERT INTO `role_permission` VALUES ('4', '11');
INSERT INTO `role_permission` VALUES ('5', '11');
INSERT INTO `role_permission` VALUES ('6', '11');
INSERT INTO `role_permission` VALUES ('7', '11');
INSERT INTO `role_permission` VALUES ('8', '11');
INSERT INTO `role_permission` VALUES ('9', '11');
INSERT INTO `role_permission` VALUES ('10', '11');
INSERT INTO `role_permission` VALUES ('11', '11');
INSERT INTO `role_permission` VALUES ('12', '11');
INSERT INTO `role_permission` VALUES ('13', '11');
INSERT INTO `role_permission` VALUES ('14', '11');
INSERT INTO `role_permission` VALUES ('15', '11');
INSERT INTO `role_permission` VALUES ('16', '11');
INSERT INTO `role_permission` VALUES ('17', '11');
INSERT INTO `role_permission` VALUES ('18', '11');
INSERT INTO `role_permission` VALUES ('1', '12');
INSERT INTO `role_permission` VALUES ('2', '12');
INSERT INTO `role_permission` VALUES ('3', '12');
INSERT INTO `role_permission` VALUES ('4', '12');
INSERT INTO `role_permission` VALUES ('5', '12');
INSERT INTO `role_permission` VALUES ('6', '12');
INSERT INTO `role_permission` VALUES ('7', '12');
INSERT INTO `role_permission` VALUES ('8', '12');
INSERT INTO `role_permission` VALUES ('9', '12');
INSERT INTO `role_permission` VALUES ('10', '12');
INSERT INTO `role_permission` VALUES ('11', '12');
INSERT INTO `role_permission` VALUES ('12', '12');
INSERT INTO `role_permission` VALUES ('13', '12');
INSERT INTO `role_permission` VALUES ('14', '12');
INSERT INTO `role_permission` VALUES ('15', '12');
INSERT INTO `role_permission` VALUES ('16', '12');
INSERT INTO `role_permission` VALUES ('17', '12');
INSERT INTO `role_permission` VALUES ('18', '12');
INSERT INTO `role_permission` VALUES ('1', '13');
INSERT INTO `role_permission` VALUES ('2', '13');
INSERT INTO `role_permission` VALUES ('3', '13');
INSERT INTO `role_permission` VALUES ('4', '13');
INSERT INTO `role_permission` VALUES ('5', '13');
INSERT INTO `role_permission` VALUES ('6', '13');
INSERT INTO `role_permission` VALUES ('7', '13');
INSERT INTO `role_permission` VALUES ('8', '13');
INSERT INTO `role_permission` VALUES ('9', '13');
INSERT INTO `role_permission` VALUES ('10', '13');
INSERT INTO `role_permission` VALUES ('11', '13');
INSERT INTO `role_permission` VALUES ('12', '13');
INSERT INTO `role_permission` VALUES ('13', '13');
INSERT INTO `role_permission` VALUES ('14', '13');
INSERT INTO `role_permission` VALUES ('15', '13');
INSERT INTO `role_permission` VALUES ('16', '13');
INSERT INTO `role_permission` VALUES ('17', '13');
INSERT INTO `role_permission` VALUES ('18', '13');
INSERT INTO `role_permission` VALUES ('1', '14');
INSERT INTO `role_permission` VALUES ('2', '14');
INSERT INTO `role_permission` VALUES ('3', '14');
INSERT INTO `role_permission` VALUES ('4', '14');
INSERT INTO `role_permission` VALUES ('5', '14');
INSERT INTO `role_permission` VALUES ('6', '14');
INSERT INTO `role_permission` VALUES ('7', '14');
INSERT INTO `role_permission` VALUES ('8', '14');
INSERT INTO `role_permission` VALUES ('9', '14');
INSERT INTO `role_permission` VALUES ('10', '14');
INSERT INTO `role_permission` VALUES ('11', '14');
INSERT INTO `role_permission` VALUES ('12', '14');
INSERT INTO `role_permission` VALUES ('13', '14');
INSERT INTO `role_permission` VALUES ('14', '14');
INSERT INTO `role_permission` VALUES ('15', '14');
INSERT INTO `role_permission` VALUES ('16', '14');
INSERT INTO `role_permission` VALUES ('17', '14');
INSERT INTO `role_permission` VALUES ('18', '14');
INSERT INTO `role_permission` VALUES ('1', '15');
INSERT INTO `role_permission` VALUES ('2', '15');
INSERT INTO `role_permission` VALUES ('3', '15');
INSERT INTO `role_permission` VALUES ('4', '15');
INSERT INTO `role_permission` VALUES ('5', '15');
INSERT INTO `role_permission` VALUES ('6', '15');
INSERT INTO `role_permission` VALUES ('7', '15');
INSERT INTO `role_permission` VALUES ('8', '15');
INSERT INTO `role_permission` VALUES ('9', '15');
INSERT INTO `role_permission` VALUES ('10', '15');
INSERT INTO `role_permission` VALUES ('11', '15');
INSERT INTO `role_permission` VALUES ('12', '15');
INSERT INTO `role_permission` VALUES ('13', '15');
INSERT INTO `role_permission` VALUES ('14', '15');
INSERT INTO `role_permission` VALUES ('15', '15');
INSERT INTO `role_permission` VALUES ('16', '15');
INSERT INTO `role_permission` VALUES ('17', '15');
INSERT INTO `role_permission` VALUES ('18', '15');
INSERT INTO `role_permission` VALUES ('1', '16');
INSERT INTO `role_permission` VALUES ('2', '16');
INSERT INTO `role_permission` VALUES ('3', '16');
INSERT INTO `role_permission` VALUES ('4', '16');
INSERT INTO `role_permission` VALUES ('5', '16');
INSERT INTO `role_permission` VALUES ('6', '16');
INSERT INTO `role_permission` VALUES ('7', '16');
INSERT INTO `role_permission` VALUES ('8', '16');
INSERT INTO `role_permission` VALUES ('9', '16');
INSERT INTO `role_permission` VALUES ('10', '16');
INSERT INTO `role_permission` VALUES ('11', '16');
INSERT INTO `role_permission` VALUES ('12', '16');
INSERT INTO `role_permission` VALUES ('13', '16');
INSERT INTO `role_permission` VALUES ('14', '16');
INSERT INTO `role_permission` VALUES ('15', '16');
INSERT INTO `role_permission` VALUES ('16', '16');
INSERT INTO `role_permission` VALUES ('17', '16');
INSERT INTO `role_permission` VALUES ('18', '16');
INSERT INTO `role_permission` VALUES ('1', '17');
INSERT INTO `role_permission` VALUES ('2', '17');
INSERT INTO `role_permission` VALUES ('3', '17');
INSERT INTO `role_permission` VALUES ('4', '17');
INSERT INTO `role_permission` VALUES ('5', '17');
INSERT INTO `role_permission` VALUES ('6', '17');
INSERT INTO `role_permission` VALUES ('7', '17');
INSERT INTO `role_permission` VALUES ('8', '17');
INSERT INTO `role_permission` VALUES ('9', '17');
INSERT INTO `role_permission` VALUES ('10', '17');
INSERT INTO `role_permission` VALUES ('11', '17');
INSERT INTO `role_permission` VALUES ('12', '17');
INSERT INTO `role_permission` VALUES ('13', '17');
INSERT INTO `role_permission` VALUES ('14', '17');
INSERT INTO `role_permission` VALUES ('15', '17');
INSERT INTO `role_permission` VALUES ('16', '17');
INSERT INTO `role_permission` VALUES ('17', '17');
INSERT INTO `role_permission` VALUES ('18', '17');
INSERT INTO `role_permission` VALUES ('1', '18');
INSERT INTO `role_permission` VALUES ('2', '18');
INSERT INTO `role_permission` VALUES ('3', '18');
INSERT INTO `role_permission` VALUES ('4', '18');
INSERT INTO `role_permission` VALUES ('5', '18');
INSERT INTO `role_permission` VALUES ('6', '18');
INSERT INTO `role_permission` VALUES ('7', '18');
INSERT INTO `role_permission` VALUES ('8', '18');
INSERT INTO `role_permission` VALUES ('9', '18');
INSERT INTO `role_permission` VALUES ('10', '18');
INSERT INTO `role_permission` VALUES ('11', '18');
INSERT INTO `role_permission` VALUES ('12', '18');
INSERT INTO `role_permission` VALUES ('13', '18');
INSERT INTO `role_permission` VALUES ('14', '18');
INSERT INTO `role_permission` VALUES ('15', '18');
INSERT INTO `role_permission` VALUES ('16', '18');
INSERT INTO `role_permission` VALUES ('17', '18');
INSERT INTO `role_permission` VALUES ('18', '18');
INSERT INTO `role_permission` VALUES ('1', '19');
INSERT INTO `role_permission` VALUES ('2', '19');
INSERT INTO `role_permission` VALUES ('3', '19');
INSERT INTO `role_permission` VALUES ('4', '19');
INSERT INTO `role_permission` VALUES ('5', '19');
INSERT INTO `role_permission` VALUES ('6', '19');
INSERT INTO `role_permission` VALUES ('7', '19');
INSERT INTO `role_permission` VALUES ('8', '19');
INSERT INTO `role_permission` VALUES ('9', '19');
INSERT INTO `role_permission` VALUES ('10', '19');
INSERT INTO `role_permission` VALUES ('11', '19');
INSERT INTO `role_permission` VALUES ('12', '19');
INSERT INTO `role_permission` VALUES ('13', '19');
INSERT INTO `role_permission` VALUES ('14', '19');
INSERT INTO `role_permission` VALUES ('15', '19');
INSERT INTO `role_permission` VALUES ('16', '19');
INSERT INTO `role_permission` VALUES ('17', '19');
INSERT INTO `role_permission` VALUES ('18', '19');
INSERT INTO `role_permission` VALUES ('1', '20');
INSERT INTO `role_permission` VALUES ('2', '20');
INSERT INTO `role_permission` VALUES ('3', '20');
INSERT INTO `role_permission` VALUES ('4', '20');
INSERT INTO `role_permission` VALUES ('5', '20');
INSERT INTO `role_permission` VALUES ('6', '20');
INSERT INTO `role_permission` VALUES ('7', '20');
INSERT INTO `role_permission` VALUES ('8', '20');
INSERT INTO `role_permission` VALUES ('9', '20');
INSERT INTO `role_permission` VALUES ('10', '20');
INSERT INTO `role_permission` VALUES ('11', '20');
INSERT INTO `role_permission` VALUES ('12', '20');
INSERT INTO `role_permission` VALUES ('13', '20');
INSERT INTO `role_permission` VALUES ('14', '20');
INSERT INTO `role_permission` VALUES ('15', '20');
INSERT INTO `role_permission` VALUES ('16', '20');
INSERT INTO `role_permission` VALUES ('17', '20');
INSERT INTO `role_permission` VALUES ('18', '20');
INSERT INTO `role_permission` VALUES ('1', '21');
INSERT INTO `role_permission` VALUES ('2', '21');
INSERT INTO `role_permission` VALUES ('3', '21');
INSERT INTO `role_permission` VALUES ('4', '21');
INSERT INTO `role_permission` VALUES ('5', '21');
INSERT INTO `role_permission` VALUES ('6', '21');
INSERT INTO `role_permission` VALUES ('7', '21');
INSERT INTO `role_permission` VALUES ('8', '21');
INSERT INTO `role_permission` VALUES ('9', '21');
INSERT INTO `role_permission` VALUES ('10', '21');
INSERT INTO `role_permission` VALUES ('11', '21');
INSERT INTO `role_permission` VALUES ('12', '21');
INSERT INTO `role_permission` VALUES ('13', '21');
INSERT INTO `role_permission` VALUES ('14', '21');
INSERT INTO `role_permission` VALUES ('15', '21');
INSERT INTO `role_permission` VALUES ('16', '21');
INSERT INTO `role_permission` VALUES ('17', '21');
INSERT INTO `role_permission` VALUES ('18', '21');
INSERT INTO `role_permission` VALUES ('1', '22');
INSERT INTO `role_permission` VALUES ('2', '22');
INSERT INTO `role_permission` VALUES ('3', '22');
INSERT INTO `role_permission` VALUES ('4', '22');
INSERT INTO `role_permission` VALUES ('5', '22');
INSERT INTO `role_permission` VALUES ('6', '22');
INSERT INTO `role_permission` VALUES ('7', '22');
INSERT INTO `role_permission` VALUES ('8', '22');
INSERT INTO `role_permission` VALUES ('9', '22');
INSERT INTO `role_permission` VALUES ('10', '22');
INSERT INTO `role_permission` VALUES ('11', '22');
INSERT INTO `role_permission` VALUES ('12', '22');
INSERT INTO `role_permission` VALUES ('13', '22');
INSERT INTO `role_permission` VALUES ('14', '22');
INSERT INTO `role_permission` VALUES ('15', '22');
INSERT INTO `role_permission` VALUES ('16', '22');
INSERT INTO `role_permission` VALUES ('17', '22');
INSERT INTO `role_permission` VALUES ('18', '22');
INSERT INTO `role_permission` VALUES ('1', '23');
INSERT INTO `role_permission` VALUES ('2', '23');
INSERT INTO `role_permission` VALUES ('3', '23');
INSERT INTO `role_permission` VALUES ('4', '23');
INSERT INTO `role_permission` VALUES ('5', '23');
INSERT INTO `role_permission` VALUES ('6', '23');
INSERT INTO `role_permission` VALUES ('7', '23');
INSERT INTO `role_permission` VALUES ('8', '23');
INSERT INTO `role_permission` VALUES ('9', '23');
INSERT INTO `role_permission` VALUES ('10', '23');
INSERT INTO `role_permission` VALUES ('11', '23');
INSERT INTO `role_permission` VALUES ('12', '23');
INSERT INTO `role_permission` VALUES ('13', '23');
INSERT INTO `role_permission` VALUES ('14', '23');
INSERT INTO `role_permission` VALUES ('15', '23');
INSERT INTO `role_permission` VALUES ('16', '23');
INSERT INTO `role_permission` VALUES ('17', '23');
INSERT INTO `role_permission` VALUES ('18', '23');
INSERT INTO `role_permission` VALUES ('1', '24');
INSERT INTO `role_permission` VALUES ('2', '24');
INSERT INTO `role_permission` VALUES ('3', '24');
INSERT INTO `role_permission` VALUES ('4', '24');
INSERT INTO `role_permission` VALUES ('5', '24');
INSERT INTO `role_permission` VALUES ('6', '24');
INSERT INTO `role_permission` VALUES ('7', '24');
INSERT INTO `role_permission` VALUES ('8', '24');
INSERT INTO `role_permission` VALUES ('9', '24');
INSERT INTO `role_permission` VALUES ('10', '24');
INSERT INTO `role_permission` VALUES ('11', '24');
INSERT INTO `role_permission` VALUES ('12', '24');
INSERT INTO `role_permission` VALUES ('13', '24');
INSERT INTO `role_permission` VALUES ('14', '24');
INSERT INTO `role_permission` VALUES ('15', '24');
INSERT INTO `role_permission` VALUES ('16', '24');
INSERT INTO `role_permission` VALUES ('17', '24');
INSERT INTO `role_permission` VALUES ('18', '24');
INSERT INTO `role_permission` VALUES ('1', '25');
INSERT INTO `role_permission` VALUES ('2', '25');
INSERT INTO `role_permission` VALUES ('3', '25');
INSERT INTO `role_permission` VALUES ('4', '25');
INSERT INTO `role_permission` VALUES ('5', '25');
INSERT INTO `role_permission` VALUES ('6', '25');
INSERT INTO `role_permission` VALUES ('7', '25');
INSERT INTO `role_permission` VALUES ('8', '25');
INSERT INTO `role_permission` VALUES ('9', '25');
INSERT INTO `role_permission` VALUES ('10', '25');
INSERT INTO `role_permission` VALUES ('11', '25');
INSERT INTO `role_permission` VALUES ('12', '25');
INSERT INTO `role_permission` VALUES ('13', '25');
INSERT INTO `role_permission` VALUES ('14', '25');
INSERT INTO `role_permission` VALUES ('15', '25');
INSERT INTO `role_permission` VALUES ('16', '25');
INSERT INTO `role_permission` VALUES ('17', '25');
INSERT INTO `role_permission` VALUES ('18', '25');
INSERT INTO `role_permission` VALUES ('1', '26');
INSERT INTO `role_permission` VALUES ('2', '26');
INSERT INTO `role_permission` VALUES ('3', '26');
INSERT INTO `role_permission` VALUES ('4', '26');
INSERT INTO `role_permission` VALUES ('5', '26');
INSERT INTO `role_permission` VALUES ('6', '26');
INSERT INTO `role_permission` VALUES ('7', '26');
INSERT INTO `role_permission` VALUES ('8', '26');
INSERT INTO `role_permission` VALUES ('9', '26');
INSERT INTO `role_permission` VALUES ('10', '26');
INSERT INTO `role_permission` VALUES ('11', '26');
INSERT INTO `role_permission` VALUES ('12', '26');
INSERT INTO `role_permission` VALUES ('13', '26');
INSERT INTO `role_permission` VALUES ('14', '26');
INSERT INTO `role_permission` VALUES ('15', '26');
INSERT INTO `role_permission` VALUES ('16', '26');
INSERT INTO `role_permission` VALUES ('17', '26');
INSERT INTO `role_permission` VALUES ('18', '26');
INSERT INTO `role_permission` VALUES ('1', '27');
INSERT INTO `role_permission` VALUES ('2', '27');
INSERT INTO `role_permission` VALUES ('3', '27');
INSERT INTO `role_permission` VALUES ('4', '27');
INSERT INTO `role_permission` VALUES ('5', '27');
INSERT INTO `role_permission` VALUES ('6', '27');
INSERT INTO `role_permission` VALUES ('7', '27');
INSERT INTO `role_permission` VALUES ('8', '27');
INSERT INTO `role_permission` VALUES ('9', '27');
INSERT INTO `role_permission` VALUES ('10', '27');
INSERT INTO `role_permission` VALUES ('11', '27');
INSERT INTO `role_permission` VALUES ('12', '27');
INSERT INTO `role_permission` VALUES ('13', '27');
INSERT INTO `role_permission` VALUES ('14', '27');
INSERT INTO `role_permission` VALUES ('15', '27');
INSERT INTO `role_permission` VALUES ('16', '27');
INSERT INTO `role_permission` VALUES ('17', '27');
INSERT INTO `role_permission` VALUES ('18', '27');
INSERT INTO `role_permission` VALUES ('1', '28');
INSERT INTO `role_permission` VALUES ('2', '28');
INSERT INTO `role_permission` VALUES ('3', '28');
INSERT INTO `role_permission` VALUES ('4', '28');
INSERT INTO `role_permission` VALUES ('5', '28');
INSERT INTO `role_permission` VALUES ('6', '28');
INSERT INTO `role_permission` VALUES ('7', '28');
INSERT INTO `role_permission` VALUES ('8', '28');
INSERT INTO `role_permission` VALUES ('9', '28');
INSERT INTO `role_permission` VALUES ('10', '28');
INSERT INTO `role_permission` VALUES ('11', '28');
INSERT INTO `role_permission` VALUES ('12', '28');
INSERT INTO `role_permission` VALUES ('13', '28');
INSERT INTO `role_permission` VALUES ('14', '28');
INSERT INTO `role_permission` VALUES ('15', '28');
INSERT INTO `role_permission` VALUES ('16', '28');
INSERT INTO `role_permission` VALUES ('17', '28');
INSERT INTO `role_permission` VALUES ('18', '28');
INSERT INTO `role_permission` VALUES ('1', '29');
INSERT INTO `role_permission` VALUES ('2', '29');
INSERT INTO `role_permission` VALUES ('3', '29');
INSERT INTO `role_permission` VALUES ('4', '29');
INSERT INTO `role_permission` VALUES ('5', '29');
INSERT INTO `role_permission` VALUES ('6', '29');
INSERT INTO `role_permission` VALUES ('7', '29');
INSERT INTO `role_permission` VALUES ('8', '29');
INSERT INTO `role_permission` VALUES ('9', '29');
INSERT INTO `role_permission` VALUES ('10', '29');
INSERT INTO `role_permission` VALUES ('11', '29');
INSERT INTO `role_permission` VALUES ('12', '29');
INSERT INTO `role_permission` VALUES ('13', '29');
INSERT INTO `role_permission` VALUES ('14', '29');
INSERT INTO `role_permission` VALUES ('15', '29');
INSERT INTO `role_permission` VALUES ('16', '29');
INSERT INTO `role_permission` VALUES ('17', '29');
INSERT INTO `role_permission` VALUES ('18', '29');
INSERT INTO `role_permission` VALUES ('1', '30');
INSERT INTO `role_permission` VALUES ('2', '30');
INSERT INTO `role_permission` VALUES ('3', '30');
INSERT INTO `role_permission` VALUES ('4', '30');
INSERT INTO `role_permission` VALUES ('5', '30');
INSERT INTO `role_permission` VALUES ('6', '30');
INSERT INTO `role_permission` VALUES ('7', '30');
INSERT INTO `role_permission` VALUES ('8', '30');
INSERT INTO `role_permission` VALUES ('9', '30');
INSERT INTO `role_permission` VALUES ('10', '30');
INSERT INTO `role_permission` VALUES ('11', '30');
INSERT INTO `role_permission` VALUES ('12', '30');
INSERT INTO `role_permission` VALUES ('13', '30');
INSERT INTO `role_permission` VALUES ('14', '30');
INSERT INTO `role_permission` VALUES ('15', '30');
INSERT INTO `role_permission` VALUES ('16', '30');
INSERT INTO `role_permission` VALUES ('17', '30');
INSERT INTO `role_permission` VALUES ('18', '30');
INSERT INTO `role_permission` VALUES ('1', '31');
INSERT INTO `role_permission` VALUES ('2', '31');
INSERT INTO `role_permission` VALUES ('3', '31');
INSERT INTO `role_permission` VALUES ('4', '31');
INSERT INTO `role_permission` VALUES ('5', '31');
INSERT INTO `role_permission` VALUES ('6', '31');
INSERT INTO `role_permission` VALUES ('7', '31');
INSERT INTO `role_permission` VALUES ('8', '31');
INSERT INTO `role_permission` VALUES ('9', '31');
INSERT INTO `role_permission` VALUES ('10', '31');
INSERT INTO `role_permission` VALUES ('11', '31');
INSERT INTO `role_permission` VALUES ('12', '31');
INSERT INTO `role_permission` VALUES ('13', '31');
INSERT INTO `role_permission` VALUES ('14', '31');
INSERT INTO `role_permission` VALUES ('15', '31');
INSERT INTO `role_permission` VALUES ('16', '31');
INSERT INTO `role_permission` VALUES ('17', '31');
INSERT INTO `role_permission` VALUES ('18', '31');
INSERT INTO `role_permission` VALUES ('1', '32');
INSERT INTO `role_permission` VALUES ('2', '32');
INSERT INTO `role_permission` VALUES ('3', '32');
INSERT INTO `role_permission` VALUES ('4', '32');
INSERT INTO `role_permission` VALUES ('5', '32');
INSERT INTO `role_permission` VALUES ('6', '32');
INSERT INTO `role_permission` VALUES ('7', '32');
INSERT INTO `role_permission` VALUES ('8', '32');
INSERT INTO `role_permission` VALUES ('9', '32');
INSERT INTO `role_permission` VALUES ('10', '32');
INSERT INTO `role_permission` VALUES ('11', '32');
INSERT INTO `role_permission` VALUES ('12', '32');
INSERT INTO `role_permission` VALUES ('13', '32');
INSERT INTO `role_permission` VALUES ('14', '32');
INSERT INTO `role_permission` VALUES ('15', '32');
INSERT INTO `role_permission` VALUES ('16', '32');
INSERT INTO `role_permission` VALUES ('17', '32');
INSERT INTO `role_permission` VALUES ('18', '32');
INSERT INTO `role_permission` VALUES ('1', '33');
INSERT INTO `role_permission` VALUES ('2', '33');
INSERT INTO `role_permission` VALUES ('3', '33');
INSERT INTO `role_permission` VALUES ('4', '33');
INSERT INTO `role_permission` VALUES ('5', '33');
INSERT INTO `role_permission` VALUES ('6', '33');
INSERT INTO `role_permission` VALUES ('7', '33');
INSERT INTO `role_permission` VALUES ('8', '33');
INSERT INTO `role_permission` VALUES ('9', '33');
INSERT INTO `role_permission` VALUES ('10', '33');
INSERT INTO `role_permission` VALUES ('11', '33');
INSERT INTO `role_permission` VALUES ('12', '33');
INSERT INTO `role_permission` VALUES ('13', '33');
INSERT INTO `role_permission` VALUES ('14', '33');
INSERT INTO `role_permission` VALUES ('15', '33');
INSERT INTO `role_permission` VALUES ('16', '33');
INSERT INTO `role_permission` VALUES ('17', '33');
INSERT INTO `role_permission` VALUES ('18', '33');
INSERT INTO `role_permission` VALUES ('1', '34');
INSERT INTO `role_permission` VALUES ('2', '34');
INSERT INTO `role_permission` VALUES ('3', '34');
INSERT INTO `role_permission` VALUES ('4', '34');
INSERT INTO `role_permission` VALUES ('5', '34');
INSERT INTO `role_permission` VALUES ('6', '34');
INSERT INTO `role_permission` VALUES ('7', '34');
INSERT INTO `role_permission` VALUES ('8', '34');
INSERT INTO `role_permission` VALUES ('9', '34');
INSERT INTO `role_permission` VALUES ('10', '34');
INSERT INTO `role_permission` VALUES ('11', '34');
INSERT INTO `role_permission` VALUES ('12', '34');
INSERT INTO `role_permission` VALUES ('13', '34');
INSERT INTO `role_permission` VALUES ('14', '34');
INSERT INTO `role_permission` VALUES ('15', '34');
INSERT INTO `role_permission` VALUES ('16', '34');
INSERT INTO `role_permission` VALUES ('17', '34');
INSERT INTO `role_permission` VALUES ('18', '34');
INSERT INTO `role_permission` VALUES ('1', '35');
INSERT INTO `role_permission` VALUES ('2', '35');
INSERT INTO `role_permission` VALUES ('3', '35');
INSERT INTO `role_permission` VALUES ('4', '35');
INSERT INTO `role_permission` VALUES ('5', '35');
INSERT INTO `role_permission` VALUES ('6', '35');
INSERT INTO `role_permission` VALUES ('7', '35');
INSERT INTO `role_permission` VALUES ('8', '35');
INSERT INTO `role_permission` VALUES ('9', '35');
INSERT INTO `role_permission` VALUES ('10', '35');
INSERT INTO `role_permission` VALUES ('11', '35');
INSERT INTO `role_permission` VALUES ('12', '35');
INSERT INTO `role_permission` VALUES ('13', '35');
INSERT INTO `role_permission` VALUES ('14', '35');
INSERT INTO `role_permission` VALUES ('15', '35');
INSERT INTO `role_permission` VALUES ('16', '35');
INSERT INTO `role_permission` VALUES ('17', '35');
INSERT INTO `role_permission` VALUES ('18', '35');
INSERT INTO `role_permission` VALUES ('1', '36');
INSERT INTO `role_permission` VALUES ('2', '36');
INSERT INTO `role_permission` VALUES ('3', '36');
INSERT INTO `role_permission` VALUES ('4', '36');
INSERT INTO `role_permission` VALUES ('5', '36');
INSERT INTO `role_permission` VALUES ('6', '36');
INSERT INTO `role_permission` VALUES ('7', '36');
INSERT INTO `role_permission` VALUES ('8', '36');
INSERT INTO `role_permission` VALUES ('9', '36');
INSERT INTO `role_permission` VALUES ('10', '36');
INSERT INTO `role_permission` VALUES ('11', '36');
INSERT INTO `role_permission` VALUES ('12', '36');
INSERT INTO `role_permission` VALUES ('13', '36');
INSERT INTO `role_permission` VALUES ('14', '36');
INSERT INTO `role_permission` VALUES ('15', '36');
INSERT INTO `role_permission` VALUES ('16', '36');
INSERT INTO `role_permission` VALUES ('17', '36');
INSERT INTO `role_permission` VALUES ('18', '36');

insert  into `role_permission`(`role_id`,`permission_id`) values (1,1);

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `task_code` varchar(20) DEFAULT NULL COMMENT '工单编号',
  `procinst_id` int(11) DEFAULT NULL COMMENT '流程实例ID',
  `procinst_code` varchar(20) DEFAULT NULL COMMENT '流程实例编号',
  `proc_type` varchar(20) DEFAULT NULL COMMENT '流程类型',
  `up_node_id` varchar(255) DEFAULT NULL COMMENT '上一节点ID',
  `next_node_id` varchar(255) DEFAULT NULL COMMENT '下一节点ID',
  `node_id` int(11) DEFAULT NULL COMMENT '节点ID',
  `node_name` varchar(20) DEFAULT NULL COMMENT '节点名称',
  `node_type` varchar(20) DEFAULT NULL COMMENT '节点类型',
  `time_limit` varchar(50) DEFAULT NULL COMMENT '限时时间',
  `status` varchar(20) DEFAULT '0' COMMENT '完成状态',
  `comment` text COMMENT '备注',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务完成时间',
  `start_person_id` int(11) DEFAULT NULL COMMENT '发起人ID',
  `start_person_role_id` int(11) DEFAULT NULL COMMENT '发起人角色ID',
  `start_dept_id` int(11) DEFAULT NULL COMMENT '发起人部门ID',
  `dispose_persion_id` int(11) DEFAULT NULL COMMENT '处理人',
  `dispose_time` datetime DEFAULT NULL COMMENT '处理时间',
  `back_persion_id` varchar(20) DEFAULT NULL COMMENT '退回人ID',
  `back_persion` varchar(20) DEFAULT NULL COMMENT '退回人',
  `back_time` datetime DEFAULT NULL COMMENT '退回时间',
  `back_comment` text COMMENT '退回备注',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `is_back` varchar(20) DEFAULT '0' COMMENT '退回标记',
  `up_task_id` int(11) DEFAULT NULL COMMENT '上一工单ID',
  `approve_task_id` int(11) DEFAULT NULL COMMENT '审批工单ID',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task` */

/*Table structure for table `task_log` */

DROP TABLE IF EXISTS `task_log`;

CREATE TABLE `task_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `procinst_id` int(11) DEFAULT NULL COMMENT '实例ID',
  `task_id` int(11) DEFAULT NULL COMMENT '工单ID',
  `node_name` varchar(50) DEFAULT NULL COMMENT '节点名称',
  `person` varchar(50) DEFAULT NULL COMMENT '受理人',
  `node_time` datetime DEFAULT NULL COMMENT '时间',
  `node_role` varchar(50) DEFAULT NULL COMMENT '节点角色',
  `node_handle` varchar(50) DEFAULT NULL COMMENT '处理节点',
  `node_action` varchar(50) DEFAULT NULL COMMENT '节点动作',
  `up_task_id` int(11) DEFAULT NULL COMMENT '上一工单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `task_log` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(30) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL,
  `secret_key` varchar(64) DEFAULT NULL,
  `employee_code` varchar(50) DEFAULT NULL COMMENT '工号',
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `create_by_id` int(11) DEFAULT '1',
  `create_at` datetime DEFAULT NULL,
  `is_deleted` varchar(10) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  KEY `index_login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('1', 'admin', '7394630bee36f2bd8b793f88320efdf8', 'cb7e52304f0d11e6965c00ff2c2e2b3f', '1', '系统管理员', NULL, '男', NULL, '1', '1', '2015-10-10 12:14:17', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('2', 'azs', 'da262599275f9f8dc4458cc1d66382d0', '4b55373bd2024349a147660c90eb1e0b', '2', '安质室人', NULL, '男', NULL, '4', '1', '2016-09-19 16:43:17', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('3', 'azb', '643b25334110777f2acc840e594712f3', '569111ca4c2d4afeafd9aa1ea5ac4677', '3', '安质部人', NULL, '男', NULL, '4', '1', '2016-09-19 16:44:31', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('4', 'scdds', 'f5c4e819195272e3d3b3d2fc990074b3', '27eae64866ba40e7a6d343e7506537cd', '4', '生产调度室人', NULL, '男', NULL, '2', '1', '2016-09-23 11:52:19', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('5', 'wxdds', 'e23cea2af9e0c23b19a79f82e24e0776', 'add2ac708e6c4c1cada8ca1c7b9556f2', '5', '维修调度室人', NULL, '男', NULL, '8', '1', '2016-09-23 11:54:09', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('6', 'xmb1', 'c7be34a586076bce1e066047b518d733', '79725e6d6a734955a150fd99c1088dcd', '6', '项目部1人', NULL, '男', NULL, '11', '1', '2016-09-14 11:34:11', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('7', 'xmb2', '1b417e6ad299e27a1a6c0c049fe969e5', '812567491dd44504b40f352e95fd21b9', '7', '项目部2人', NULL, '男', NULL, '12', '1', '2016-09-14 11:34:14', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('8', 'xmb3', '8f4b4f899dbd28a13cbe4a0fb6abaee5', 'aac76c80f6c6417fbea771bd3fb592d0', '8', '项目部3人', NULL, '男', NULL, '13', '1', '2016-09-14 11:34:16', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('9', 'xmb4', 'e48cb7a597e5a2792f877a0fd7034cb1', '241e0747fbe542c892bc054cc0f78cd8', '9', '项目部4人', NULL, '男', NULL, '14', '1', '2016-09-14 11:34:19', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('10', 'xmb5', '12fd93e7264cad681377777d48470760', '745f8502d99242e3af06ab48c3aff764', '10', '项目部5人', NULL, '男', NULL, '15', '1', '2016-09-14 11:34:24', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('11', 'xmb6', '51437ad82434ba2dc6e3f8bcae514bf4', '2e0fb90f3bd1431e85d15087ff1bebc2', '11', '项目部6人', NULL, '男', NULL, '16', '1', '2016-09-14 11:34:21', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('12', 'xmb7', '1afa38aa4b739740e0fa62b75d172e26', '1ec1f416ccfc4574aaf79907e146e736', '12', '项目部7人', NULL, '男', NULL, '17', '1', '2016-09-14 11:34:27', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('13', 'xmb8', '207518173e8c26325af7e71b76664d8c', '71c8ddee55d24f98b5411aaaad044480', '13', '项目部8人', NULL, '男', NULL, '18', '1', '2016-09-14 11:34:29', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('14', 'xmb9', 'b41a62d96179d84e37c0845def4434de', 'bc26a37fa5b2418da8cbc15e354aa31b', '14', '项目部9人', NULL, '男', NULL, '10', '1', '2016-09-14 11:34:32', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('23', 'wzb', '4ee0ed768925f03c9be94253c8dc21d4', '2b1a5190fdc641768aabee2b6e55ac48', '15', '物资部人', '', '男', '', '3', '6', '2016-10-14 11:36:04', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('24', 'cwb', '8d5082b3c60942e36ed63a20bbd7b7d8', '337ec97cec5e42269da4a0e855e5bc3f', '16', '财务部人', '', '男', '', '5', '6', '2016-10-14 11:36:43', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('25', 'pxbzr', 'b3d75449bb9b85a634681b81133d8b88', 'fe057c7fc00345cbaf206c019b3d8cae', '17', '培训副主任', '111', '男', '11111', '8', '3', '2016-10-24 16:49:23', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('26', 'kfg', '1c092585c3e0ea271c82cc43fa38f1bf', 'fc50e42c92b346b89f068fcedce0c652', '18', '开发岗', '1111', '男', '111111', '8', '3', '2016-10-24 16:50:27', '0');
INSERT INTO `pms`.`user` (`user_id`, `login_name`, `password`, `secret_key`, `employee_code`, `name`, `email`, `gender`, `phone`, `department_id`, `create_by_id`, `create_at`, `is_deleted`) VALUES ('27', 'cwb1', '7021710963b9bb9672476a64a9239471', '91d1526248264d84894e3279a6db3d1f', '111', '财务部一', '1222', '女', '1232323', '5', '24', '2016-10-27 16:35:38', '0');


/*Table structure for table `user_log` */

DROP TABLE IF EXISTS `user_log`;

CREATE TABLE `user_log` (
  `user_id` int(11) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `login_ip` varchar(100) DEFAULT NULL,
  `local_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_log` */

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('2', '2');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('3', '3');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('4', '4');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('5', '5');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('6', '6');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('7', '7');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('8', '8');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('9', '9');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('10', '10');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('11', '11');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('12', '12');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('13', '13');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('14', '14');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('24', '15');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('27', '15');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('23', '16');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('25', '17');
INSERT INTO `pms`.`user_role` (`user_id`, `role_id`) VALUES ('26', '18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
