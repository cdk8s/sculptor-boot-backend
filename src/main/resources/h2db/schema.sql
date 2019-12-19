
DROP TABLE IF EXISTS `sys_demo`;

CREATE TABLE `sys_demo`
(
    `id`             bigint       NOT NULL COMMENT '主键ID',
    `demo_name`      varchar(50)  NOT NULL COMMENT '演示名称',
    `demo_num`       varchar(50)  NOT NULL COMMENT '演示编号:oneParam,listParam',
    `demo_type_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '演示类型:[1=在线演示=ONLINE_DEMO, 2=离线演示=OFFLINE_DEMO]max=2',
    `ranking`        tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`    varchar(250) NULL COMMENT '描述',
    `state_enum`     tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`    tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`    bigint       NOT NULL COMMENT '创建时间',
    `create_user_id` bigint       NOT NULL COMMENT '创建人',
    `update_date`    bigint       NOT NULL COMMENT '更新时间',
    `update_user_id` bigint       NOT NULL COMMENT '更新人',
    `delete_date`    bigint       NULL COMMENT '删除时间',
    `delete_user_id` bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT = '演示表';


DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user`
(
    `id`                   bigint      NOT NULL COMMENT '主键ID',
    `username`             varchar(50) NOT NULL COMMENT '用户账号:oneParam',
    `real_name`            varchar(50) NULL COMMENT '真实姓名',
    `user_password`        varchar(50) NOT NULL COMMENT '登录密码',
    `password_salt`        varchar(10) NOT NULL COMMENT '密码盐:放于密码后面',
    `user_email`           varchar(50) NULL COMMENT '邮箱地址:oneParam',
    `telephone`            varchar(20) NULL COMMENT '固话',
    `mobile_phone`         varchar(20) NULL COMMENT '手机号:oneParam',
    `gender_enum`          tinyint     NOT NULL DEFAULT '1' COMMENT '性别:[1=保密=PRIVACY, 2=男性=MALE, 3=女性=FEMALE, 4=中性=NEUTRAL]max=4',
    `register_type_enum`   tinyint     NOT NULL DEFAULT '1' COMMENT '注册方式:[1=系统预置=SYSTEM_INIT, 2=后台管理系统新增=MANAGEMENT_ADD, 3=主动注册=REGISTER, 4=被邀请注册=INVITE]max=4',
    `register_origin_enum` tinyint     NOT NULL DEFAULT '1' COMMENT '注册来源:[1=WEB方式=WEB, 2=安卓APP=ANDROID, 3=苹果APP=IOS, 4=H5=H5, 5=微信小程序=WECHAT_MINI_PROGRAM, 6=微信公众号=WECHAT_OFFICIAL_ACCOUNT]max=6',
    `state_enum`           tinyint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`          tinyint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`          bigint      NOT NULL COMMENT '创建时间',
    `create_user_id`       bigint      NOT NULL COMMENT '创建人',
    `update_date`          bigint      NOT NULL COMMENT '更新时间',
    `update_user_id`       bigint      NOT NULL COMMENT '更新人',
    `delete_date`          bigint      NULL COMMENT '删除时间',
    `delete_user_id`       bigint      NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT ='用户表';


DROP TABLE IF EXISTS `sys_login_log`;

CREATE TABLE `sys_login_log`
(
    `id`                      bigint       NOT NULL COMMENT '主键ID',
    `user_id`                 bigint       NOT NULL COMMENT '用户ID:foreignKey',
    `username`                varchar(50)  NOT NULL COMMENT '用户账号:oneParam,listParam',
    `client_id`               varchar(50)  NULL COMMENT '客户端账号',
    `token`                   varchar(50)  NULL COMMENT '登录成功令牌:oneParam',
    `message`                 varchar(250) NULL COMMENT '记录信息',
    `login_date`              bigint       NULL COMMENT '登录时间',
    `logout_date`             bigint       NULL COMMENT '登出时间',
    `request_url`             varchar(500) NULL COMMENT '请求 URL',
    `bool_login_success_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '是否登录成功:[1=是=YES, 2=否=NO]max=2',
    `bool_now_online_enum`    tinyint      NOT NULL DEFAULT '1' COMMENT '当前是否在线:[1=是=YES, 2=否=NO]max=2',
    `offline_type_enum`       tinyint      NULL COMMENT '登出方式:[1=主动登出=PEOPLE_LOGOUT, 2=过期登出=EXPIRE_LOGOUT, 3=后台踢出=BACKEND_LOGOUT]max=2',
    `exception_msg`           varchar(250) NULL COMMENT '失败异常信息',
    `ip_address`              varchar(50)  NULL COMMENT 'IP 地址',
    `ip_region`               varchar(100) NULL COMMENT 'IP 信息',
    `ip_region_country`       varchar(50)  NULL COMMENT 'IP 地址对应的国家',
    `ip_region_province`      varchar(50)  NULL COMMENT 'IP 地址对应的省',
    `ip_region_city`          varchar(50)  NULL COMMENT 'IP 地址对应的市',
    `ip_region_isp`           varchar(50)  NULL COMMENT 'IP 地址对应的网络提供商',
    `user_agent`              varchar(250) NULL COMMENT '浏览器 UserAgent',
    `device_name`             varchar(50)  NULL COMMENT '设备名称',
    `os_name`                 varchar(50)  NULL COMMENT '系统名称',
    `browser_name`            varchar(50)  NULL COMMENT '浏览器',
    `browser_locale`          varchar(50)  NULL COMMENT '语言区域',
    `bool_new_user_enum`      tinyint      NOT NULL DEFAULT '1' COMMENT '是否是新用户:[1=是=YES, 2=否=NO]max=2',
    `login_origin_enum`       tinyint      NOT NULL DEFAULT '1' COMMENT '登录来源:[1=WEB方式=WEB, 2=安卓APP=ANDROID, 3=苹果APP=IOS, 4=H5=H5, 5=微信小程序=WECHAT_MINI_PROGRAM, 6=微信公众号=WECHAT_OFFICIAL_ACCOUNT]max=6',
    PRIMARY KEY (`id`)
) COMMENT ='登录事件记录表';


DROP TABLE IF EXISTS `sys_event_log`;

CREATE TABLE `sys_event_log`
(
    `id`                        bigint       NOT NULL COMMENT '主键ID',
    `user_id`                   bigint       NULL COMMENT '用户ID:foreignKey',
    `username`                  varchar(50)  NULL COMMENT '用户账号:oneParam,listParam',
    `message`                   varchar(250) NULL COMMENT '记录信息',
    `execute_time`              bigint       NULL COMMENT '执行时间:单位毫秒',
    `request_date`              bigint       NULL COMMENT '访问时间',
    `request_url`               varchar(500) NULL COMMENT '请求 URL',
    `request_method`            varchar(50)  NULL COMMENT '请求方法名',
    `request_param`             varchar(500) NULL COMMENT '请求参数',
    `bool_execute_success_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '是否执行成功:[1=是=YES, 2=否=NO]max=2',
    `operate_type_enum`         tinyint      NOT NULL DEFAULT '1' COMMENT '事件类型:[1=查询=QUERY, 2=创建=CREATE, 3=修改对象=UPDATE_INFO,4=修改状态=UPDATE_STATE,5=删除=DELETE,6=导入=IMPORT,7=导出=EXPORT]max=7',
    `exception_msg`             varchar(250) NULL COMMENT '失败异常信息',
    `ip_address`                varchar(50)  NULL COMMENT 'IP 地址',
    `ip_region`                 varchar(100) NULL COMMENT 'IP 信息',
    `ip_region_country`         varchar(50)  NULL COMMENT 'IP 地址对应的国家',
    `ip_region_province`        varchar(50)  NULL COMMENT 'IP 地址对应的省',
    `ip_region_city`            varchar(50)  NULL COMMENT 'IP 地址对应的市',
    `ip_region_isp`             varchar(50)  NULL COMMENT 'IP 地址对应的网络提供商',
    `user_agent`                varchar(250) NULL COMMENT '浏览器 UserAgent',
    `device_name`               varchar(50)  NULL COMMENT '设备名称',
    `os_name`                   varchar(50)  NULL COMMENT '系统名称',
    `browser_name`              varchar(50)  NULL COMMENT '浏览器',
    `browser_locale`            varchar(50)  NULL COMMENT '语言区域',
    PRIMARY KEY (`id`)
) COMMENT ='操作事件记录表';



DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict`
(
    `id`                   bigint       NOT NULL COMMENT '主键ID',
    `dict_name`            varchar(50)  NOT NULL COMMENT '字典名称',
    `dict_code`            varchar(100) NOT NULL COMMENT '字典编码:oneParam,listParam',
    `dict_value_type_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '字典值类型:[1=java.lang.String=String, 2=java.lang.Boolean=Boolean, 3=java.lang.Integer=Integer, 4=java.lang.Long=Long, 5=java.lang.Double=Double]max=5',
    `ranking`              tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`          varchar(250) NULL COMMENT '描述',
    `state_enum`           tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`          tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`          bigint       NOT NULL COMMENT '创建时间',
    `create_user_id`       bigint       NOT NULL COMMENT '创建人',
    `update_date`          bigint       NOT NULL COMMENT '更新时间',
    `update_user_id`       bigint       NOT NULL COMMENT '更新人',
    `delete_date`          bigint       NULL COMMENT '删除时间',
    `delete_user_id`       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT ='字典表';

DROP TABLE IF EXISTS `sys_dict_item`;

CREATE TABLE `sys_dict_item`
(
    `id`                   bigint       NOT NULL COMMENT '主键ID',
    `dict_id`              bigint       NOT NULL COMMENT '字典ID:foreignKey',
    `dict_code`            varchar(100) NOT NULL COMMENT '字典编码:oneParam,listParam',
    `item_name`            varchar(50)  NOT NULL COMMENT '字典子项名称',
    `item_code`            varchar(100) NOT NULL COMMENT '字典子项编码:oneParam,listParam',
    `item_value`           varchar(250) NULL COMMENT '字典子项值',
    `dict_value_type_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '字典值类型:[1=java.lang.String=String, 2=java.lang.Boolean=Boolean, 3=java.lang.Integer=Integer, 4=java.lang.Long=Long, 5=java.lang.Double=Double]max=5',
    `ranking`              tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`          varchar(250) NULL COMMENT '描述',
    `state_enum`           tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`          tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`          bigint       NOT NULL COMMENT '创建时间',
    `create_user_id`       bigint       NOT NULL COMMENT '创建人',
    `update_date`          bigint       NOT NULL COMMENT '更新时间',
    `update_user_id`       bigint       NOT NULL COMMENT '更新人',
    `delete_date`          bigint       NULL COMMENT '删除时间',
    `delete_user_id`       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT ='字典子项表';

DROP TABLE IF EXISTS `sys_param_type`;

CREATE TABLE `sys_param_type`
(
    `id`             bigint       NOT NULL COMMENT '主键ID',
    `type_name`      varchar(50)  NOT NULL COMMENT '类型名称',
    `type_code`      varchar(100) NOT NULL COMMENT '类型编码:oneParam,listParam',
    `ranking`        tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`    varchar(250) NULL COMMENT '描述',
    `state_enum`     tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`    tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`    bigint       NOT NULL COMMENT '创建时间',
    `create_user_id` bigint       NOT NULL COMMENT '创建人',
    `update_date`    bigint       NOT NULL COMMENT '更新时间',
    `update_user_id` bigint       NOT NULL COMMENT '更新人',
    `delete_date`    bigint       NULL COMMENT '删除时间',
    `delete_user_id` bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT ='参数类型表';

DROP TABLE IF EXISTS `sys_param`;

CREATE TABLE `sys_param`
(
    `id`                    bigint       NOT NULL COMMENT '主键ID',
    `type_id`               bigint       NOT NULL COMMENT '参数类型ID:foreignKey',
    `type_code`             varchar(100) NOT NULL COMMENT '参数类型编码:oneParam,listParam',
    `param_name`            varchar(50)  NOT NULL COMMENT '参数名称',
    `param_code`            varchar(100) NOT NULL COMMENT '参数编码:oneParam,listParam',
    `param_value`           varchar(250) NULL COMMENT '参数值',
    `ranking`               tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`           varchar(250) NULL COMMENT '描述',
    `param_value_type_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '参数值类型:[1=java.lang.String=String, 2=java.lang.Boolean=Boolean, 3=java.lang.Integer=Integer, 4=java.lang.Long=Long, 5=java.lang.Double=Double]max=5',
    `state_enum`            tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`           tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`           bigint       NOT NULL COMMENT '创建时间',
    `create_user_id`        bigint       NOT NULL COMMENT '创建人',
    `update_date`           bigint       NOT NULL COMMENT '更新时间',
    `update_user_id`        bigint       NOT NULL COMMENT '更新人',
    `delete_date`           bigint       NULL COMMENT '删除时间',
    `delete_user_id`        bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT ='参数表';

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role`
(
    `id`             bigint       NOT NULL COMMENT '主键ID',
    `role_name`      varchar(50)  NOT NULL COMMENT '角色名称',
    `role_code`      varchar(50)  NOT NULL COMMENT '角色编码:oneParam,listParam',
    `ranking`        tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`    varchar(250) NULL COMMENT '描述',
    `state_enum`     tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`    tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`    bigint       NOT NULL COMMENT '创建时间',
    `create_user_id` bigint       NOT NULL COMMENT '创建人',
    `update_date`    bigint       NOT NULL COMMENT '更新时间',
    `update_user_id` bigint       NOT NULL COMMENT '更新人',
    `delete_date`    bigint       NULL COMMENT '删除时间',
    `delete_user_id` bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT = '角色表';

DROP TABLE IF EXISTS `rel_role_user`;

CREATE TABLE `rel_role_user`
(
    `id`             bigint NOT NULL COMMENT '主键ID',
    `role_id`        bigint NOT NULL COMMENT '角色ID:foreignKey',
    `user_id`        bigint NOT NULL COMMENT '用户ID:foreignKey',
    `create_date`    bigint NOT NULL COMMENT '创建时间',
    `create_user_id` bigint NOT NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
) COMMENT = '角色用户中间表';

DROP TABLE IF EXISTS `sys_employee`;

CREATE TABLE `sys_employee`
(
    `id`             bigint      NOT NULL COMMENT '主键ID',
    `user_id`        bigint      NOT NULL COMMENT '用户ID:foreignKey',
    `work_card_id`   varchar(50) NULL COMMENT '工号',
    `job_position`   varchar(50) NULL COMMENT '职位',
    `delete_enum`    tinyint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`    bigint      NOT NULL COMMENT '创建时间',
    `create_user_id` bigint      NOT NULL COMMENT '创建人',
    `update_date`    bigint      NOT NULL COMMENT '更新时间',
    `update_user_id` bigint      NOT NULL COMMENT '更新人',
    `delete_date`    bigint      NULL COMMENT '删除时间',
    `delete_user_id` bigint      NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT = '员工信息表';

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept`
(
    `id`             bigint       NOT NULL COMMENT '主键ID',
    `dept_name`      varchar(50)  NOT NULL COMMENT '部门名称',
    `dept_code`      varchar(50)  NOT NULL COMMENT '部门编码:oneParam,listParam',
    `parent_id`      bigint       NOT NULL COMMENT '父ID',
    `parent_ids`     varchar(250) NOT NULL COMMENT '父ID集:多个层级用英文逗号隔开',
    `leader_user_id` bigint       NULL COMMENT '部门领导用户ID',
    `telephone`      varchar(50)  NULL COMMENT '固话',
    `mobile_phone`   varchar(50)  NULL COMMENT '手机号',
    `dept_fax`       varchar(50)  NULL COMMENT '传真',
    `dept_address`   varchar(250) NULL COMMENT '地址',
    `ranking`        tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`    varchar(250) NULL COMMENT '描述',
    `state_enum`     tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`    tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`    bigint       NOT NULL COMMENT '创建时间',
    `create_user_id` bigint       NOT NULL COMMENT '创建人',
    `update_date`    bigint       NOT NULL COMMENT '更新时间',
    `update_user_id` bigint       NOT NULL COMMENT '更新人',
    `delete_date`    bigint       NULL COMMENT '删除时间',
    `delete_user_id` bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT = '部门表';

DROP TABLE IF EXISTS `rel_dept_user`;

CREATE TABLE `rel_dept_user`
(
    `id`             bigint NOT NULL COMMENT '主键ID',
    `dept_id`        bigint NOT NULL COMMENT '部门ID:foreignKey',
    `user_id`        bigint NOT NULL COMMENT '用户ID:foreignKey',
    `create_date`    bigint NOT NULL COMMENT '创建时间',
    `create_user_id` bigint NOT NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
) COMMENT = '部门用户中间表';


DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission`
(
    `id`                   bigint       NOT NULL COMMENT '主键ID',
    `permission_name`      varchar(50)  NOT NULL COMMENT '权限名称',
    `permission_code`      varchar(50)  NOT NULL COMMENT '权限标识码:oneParam,listParam',
    `permission_url`       varchar(500) NULL COMMENT '路由地址:如果是外部链接则代表链接地址',
    `permission_type_enum` tinyint      NOT NULL DEFAULT '1' COMMENT '权限类型:[1=一级目录=TOP_DIRECTORY, 2=菜单=MENU, 3=按钮=BUTTON]max=3',
    `parent_id`            bigint       NOT NULL COMMENT '父ID',
    `parent_ids`           varchar(250) NOT NULL COMMENT '父ID集',
    `icon_class`           varchar(250) NULL COMMENT '权限图标',
    `visible_enum`         tinyint      NOT NULL DEFAULT '1' COMMENT '显示状态:[1=显示=DISPLAY, 2=隐藏=HIDDEN]max=2',
    `bool_ext_link_enum`   tinyint      NOT NULL DEFAULT '2' COMMENT '是否外链:[1=是=YES, 2=否=NO]max=2',
    `bool_new_tab_enum`    tinyint      NOT NULL DEFAULT '2' COMMENT '是否新标签打开:[1=是=YES, 2=否=NO]max=2',
    `ranking`              tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    `description`          varchar(250) NULL COMMENT '描述',
    `state_enum`           tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    `delete_enum`          tinyint      NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    `create_date`          bigint       NOT NULL COMMENT '创建时间',
    `create_user_id`       bigint       NOT NULL COMMENT '创建人',
    `update_date`          bigint       NOT NULL COMMENT '更新时间',
    `update_user_id`       bigint       NOT NULL COMMENT '更新人',
    `delete_date`          bigint       NULL COMMENT '删除时间',
    `delete_user_id`       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
) COMMENT ='权限资源表';

DROP TABLE IF EXISTS `rel_permission_role`;

CREATE TABLE `rel_permission_role`
(
    `id`             bigint NOT NULL COMMENT '主键ID',
    `permission_id`  bigint NOT NULL COMMENT '权限ID:foreignKey',
    `role_id`        bigint NOT NULL COMMENT '角色ID:foreignKey',
    `create_date`    bigint NOT NULL COMMENT '创建时间',
    `create_user_id` bigint NOT NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
) COMMENT = '权限角色中间表';










