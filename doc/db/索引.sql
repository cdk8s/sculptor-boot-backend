
ALTER TABLE `sys_user` ADD INDEX `index_most` (`delete_enum`, `state_enum`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_user` ADD UNIQUE KEY `unique_username` (`username`) USING BTREE COMMENT '登录用户名唯一';

ALTER TABLE `sys_login_log` ADD INDEX `index_most` (`bool_login_success_enum`, `user_id`, `login_date`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_login_log` ADD INDEX `index_token` (`token`) USING BTREE COMMENT 'token';

ALTER TABLE `sys_event_log` ADD INDEX `index_most` (`user_id`, `request_date`) USING BTREE COMMENT 'index_most';

ALTER TABLE `sys_dict` ADD INDEX `index_most` (`delete_enum`, `state_enum`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_dict` ADD UNIQUE KEY `unique_dictCode` (`dict_code`) USING BTREE COMMENT '字典编码唯一';

ALTER TABLE `sys_dict_item` ADD UNIQUE KEY `unique_itemCode` (`item_code`) USING BTREE COMMENT '字典子项编码唯一';
ALTER TABLE `sys_dict_item` ADD INDEX `index_most` (`delete_enum`, `state_enum`, `dict_id`, `dict_code`) USING BTREE COMMENT 'index_most';

ALTER TABLE `sys_param_type` ADD INDEX `index_most` (`delete_enum`, `state_enum`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_param_type` ADD UNIQUE KEY `unique_typeCode` (`type_code`) USING BTREE COMMENT '类型编码唯一';

ALTER TABLE `sys_param` ADD INDEX `index_most` (`delete_enum`, `state_enum`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_param` ADD UNIQUE KEY `unique_paramCode` (`param_code`) USING BTREE COMMENT '参数编码唯一';

ALTER TABLE `sys_role` ADD INDEX `index_most` (`delete_enum`, `state_enum`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_role` ADD UNIQUE KEY `unique_roleCode` (`role_code`) USING BTREE COMMENT '角色编码唯一';

ALTER TABLE `sys_employee` ADD INDEX `index_most` (`delete_enum`, `user_id`) USING BTREE COMMENT 'index_most';

ALTER TABLE `sys_dept` ADD INDEX `index_most` (`delete_enum`, `state_enum`, `parent_id`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_dept` ADD UNIQUE KEY `unique_deptCode` (`dept_code`) USING BTREE COMMENT '部门编码唯一';


ALTER TABLE `sys_permission` ADD INDEX `index_parentId_deleteEnum_stateEnum` (`delete_enum`, `state_enum`, `parent_id`) USING BTREE COMMENT 'index_most';
ALTER TABLE `sys_permission` ADD UNIQUE KEY `unique_permissionCode` (`permission_code`) USING BTREE COMMENT '权限标识码唯一';

ALTER TABLE `rel_role_user` ADD INDEX `index_most` (`user_id`, `role_id`) USING BTREE COMMENT 'index_most';
ALTER TABLE `rel_dept_user` ADD INDEX `index_most` (`user_id`, `dept_id`) USING BTREE COMMENT 'index_most';
ALTER TABLE `rel_permission_role` ADD INDEX `index_roleId` (`role_id`,`permission_id`) USING BTREE COMMENT 'index_most';


