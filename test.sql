/*
Navicat MySQL Data Transfer


Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-01-17 18:27:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for go_config
-- ----------------------------
DROP TABLE IF EXISTS `go_config`;
CREATE TABLE `go_config` (
  `id` varchar(32) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '配置名称',
  `basic_path` varchar(100) NOT NULL COMMENT '项目完整路径（根目录）',
  `src_path` varchar(255) NOT NULL COMMENT '代码相对路径',
  `html_path` varchar(255) NOT NULL COMMENT 'html相对路径',
  `resources_path` varchar(255) NOT NULL COMMENT '配置文件相对路径',
  `db_url` varchar(200) NOT NULL COMMENT '数据库地址',
  `db_user_password` varchar(255) NOT NULL COMMENT '数据库密码',
  `db_user_name` varchar(36) NOT NULL COMMENT '数据库账号',
  `db_name` varchar(255) NOT NULL COMMENT '数据库名称',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(36) DEFAULT NULL COMMENT '修改人',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成环境配置';

-- ----------------------------
-- Table structure for go_field
-- ----------------------------
DROP TABLE IF EXISTS `go_field`;
CREATE TABLE `go_field` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '字段名',
  `un_name` varchar(32) DEFAULT NULL,
  `java_name` varchar(32) NOT NULL COMMENT 'java类名',
  `content` varchar(60) NOT NULL COMMENT '描述',
  `length` int(5) NOT NULL COMMENT '长度',
  `not_null` varchar(5) NOT NULL COMMENT '是否为空',
  `table_id` varchar(32) NOT NULL COMMENT '表id',
  `fieldType` varchar(32) DEFAULT NULL COMMENT '字段类型',
  `type` tinyint(1) DEFAULT NULL COMMENT '1 单对单,2 单对多',
  `pr_table_id` varchar(255) DEFAULT NULL COMMENT '单对多操作时（多对多模型） 不可为空。并确保关联字段存在',
  `db_table_id` varchar(32) DEFAULT NULL COMMENT '数据源表',
  `db_table_show_field` varchar(500) DEFAULT NULL COMMENT '数据源表展示字段 字段逗号分隔',
  `db_table_show_field_delimiter` varchar(6) DEFAULT NULL COMMENT '分隔符 缺省 -',
  `input` varchar(32) NOT NULL COMMENT 'input/radio/checkbox/select',
  `seelect_type` int(1) NOT NULL DEFAULT '1' COMMENT '1 模糊查询， 2 范围查询',
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_table` (`name`,`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字段';

-- ----------------------------
-- Table structure for go_table
-- ----------------------------
DROP TABLE IF EXISTS `go_table`;
CREATE TABLE `go_table` (
  `id` varchar(32) NOT NULL,
  `table_name` varchar(32) NOT NULL COMMENT '表名',
  `name` varchar(32) NOT NULL COMMENT '加工后的 名称 如 userBean',
  `un_name` varchar(32) DEFAULT NULL,
  `package_name` varchar(32) NOT NULL COMMENT '包名',
  `content` varchar(32) NOT NULL COMMENT '描述',
  `config_id` varchar(32) NOT NULL COMMENT '配置id',
  `is_input` tinyint(1) DEFAULT '0' COMMENT '是否启动导入模板',
  `pr_table_id` varchar(32) DEFAULT NULL COMMENT '父表',
  `pr_table_fieId` varchar(32) DEFAULT NULL COMMENT '父表对应主表 字段',
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_config` (`table_name`,`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表信息';

-- ----------------------------
-- Table structure for go_table_un
-- ----------------------------
DROP TABLE IF EXISTS `go_table_un`;
CREATE TABLE `go_table_un` (
  `id` varchar(32) NOT NULL,
  `table_id` varchar(32) NOT NULL,
  `field_id` varchar(32) NOT NULL,
  `un_name` varchar(32) NOT NULL COMMENT '唯一约束的昵称',
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标的唯一约束';

-- ----------------------------
-- Table structure for tab_account
-- ----------------------------
DROP TABLE IF EXISTS `tab_account`;
CREATE TABLE `tab_account` (
  `id` varchar(32) NOT NULL,
  `login_name` varchar(32) NOT NULL COMMENT '登录账号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `password` varchar(36) DEFAULT NULL COMMENT '密码',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(36) DEFAULT NULL COMMENT '修改人',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_appId` (`login_name`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方服务号信息';

-- ----------------------------
-- Table structure for tab_account_type
-- ----------------------------
DROP TABLE IF EXISTS `tab_account_type`;
CREATE TABLE `tab_account_type` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '类型名称',
  `create_by` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '修改人',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for tab_user_son
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_son`;
CREATE TABLE `tab_user_son` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '儿子姓名',
  `code` varchar(255) DEFAULT NULL COMMENT '儿子编号',
  `user_id` varchar(255) DEFAULT NULL COMMENT '爸爸',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for test_user
-- ----------------------------
DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for test_xk
-- ----------------------------
DROP TABLE IF EXISTS `test_xk`;
CREATE TABLE `test_xk` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '学生',
  `pr_user_id` varchar(32) DEFAULT NULL COMMENT '老师',
  `name` varchar(255) DEFAULT NULL COMMENT '学科名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Procedure structure for replaceOrgCode
-- ----------------------------
DROP PROCEDURE IF EXISTS `replaceOrgCode`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `replaceOrgCode`()
BEGIN
DECLARE code_length int DEFAULT 0 ;
DECLARE new_code VARCHAR(500);
DECLARE org_id VARCHAR(500);
DECLARE old_code VARCHAR(500);
DECLARE code_length_index int DEFAULT 1;
DECLARE b int default 0;
DECLARE pro CURSOR for select id,org_code,LENGTH(org_code) from t_s_depart;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET b = 1;
OPEN pro;
FETCH pro into org_id,old_code,code_length;
 while b<>1 do
   set code_length_index=1;
	 set new_code='';
	 while code_length_index<code_length do
     set new_code=CONCAT(new_code,'A',SUBSTR(old_code,code_length_index,2));
     set code_length_index=code_length_index+2;
   end while;
    update t_s_depart set org_code=new_code where id=org_id;
  FETCH pro into org_id,old_code,code_length;
end while;
close pro;
end
;;
DELIMITER ;

-- ----------------------------
-- Event structure for event_tabRecord_alter
-- ----------------------------
DROP EVENT IF EXISTS `event_tabRecord_alter`;
DELIMITER ;;
CREATE DEFINER=`qinyi`@`%` EVENT `event_tabRecord_alter` ON SCHEDULE EVERY 1 DAY STARTS '2017-06-08 11:16:00' ON COMPLETION PRESERVE ENABLE DO BEGIN 

SET @newTableName = CONCAT('tab_operation_record','_',DATE_FORMAT(NOW(),'%m'),'_',DATE_FORMAT(NOW(),'%d'),'_02;');
SET @alterTable = CONCAT('ALTER TABLE tab_operation_record RENAME ',@newTableName);
PREPARE stmt FROM @alterTable;
EXECUTE stmt;

SET @createTable = 'CREATE TABLE tab_operation_record (id VARCHAR(36) NOT NULL,userId varchar(36) NOT NULL,projectId int(11) NOT NULL, ip varchar(20),createTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,day DATE NOT NULL,requestAdd varchar(150), requestParam longtext,loginKey varchar(50), apiTime int(10), time int(15))';
PREPARE stmt1 FROM @createTable;
EXECUTE stmt1;

SET @addindex1 = CONCAT('ALTER TABLE ','tab_operation_record','_',DATE_FORMAT(NOW(),'%m'),'_',DATE_FORMAT(NOW(),'%d'),'_02',' ADD INDEX ik1 (projectId,userId,day,ip)');
PREPARE stmt2 FROM @addindex1;
EXECUTE stmt2;

SET @addindex2 = CONCAT('ALTER TABLE ','tab_operation_record','_',DATE_FORMAT(NOW(),'%m'),'_',DATE_FORMAT(NOW(),'%d'),'_02',' ADD INDEX ik2 (requestAdd)');
PREPARE stmt3 FROM @addindex2;
EXECUTE stmt3;

END
;;
DELIMITER ;
