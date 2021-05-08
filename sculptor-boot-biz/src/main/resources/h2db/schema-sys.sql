DROP TABLE IF EXISTS sys_big_text;
CREATE TABLE sys_big_text
(
    id             bigint       NOT NULL COMMENT '主键ID',
    text_title     varchar(250) NOT NULL COMMENT '文本标题:likeParam',
    text_code      varchar(50)  NOT NULL COMMENT '文本编码:oneParam,listParam',
    text_content   text         NOT NULL COMMENT '内容',
    state_enum     smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    update_date    bigint       NOT NULL COMMENT '更新时间',
    update_user_id bigint       NOT NULL COMMENT '更新人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '文本表';

DROP TABLE IF EXISTS sys_banner;
CREATE TABLE sys_banner
(
    id                    bigint         NOT NULL COMMENT '主键ID',
    banner_title          varchar(50)    NOT NULL COMMENT '标题:likeParam',
    banner_description    varchar(250)   NULL COMMENT '介绍',
    banner_code           varchar(100)   NULL COMMENT '编码:oneParam,listParam',
    cover_pc_image_url    varchar(500)   NULL COMMENT 'PC封面图片',
    cover_h5_image_url    varchar(500)   NULL COMMENT '移动端封面图片',
    banner_stand_date     decimal(18, 2) NOT NULL DEFAULT '2.5' COMMENT '停留时间:支持小数',
    banner_jump_type_enum smallint       NOT NULL DEFAULT '1' COMMENT '跳转类型:[1=对象ID=OBJECT_ID, 2=URL=URL]max=2',
    jump_h5_url           varchar(500)   NULL COMMENT '移动端跳转url地址',
    jump_pc_url           varchar(500)   NULL COMMENT 'PC端跳转url地址',
    jump_type_code        varchar(50)    NULL COMMENT '跳转业务类型编码:oneParam',
    jump_object_id        bigint         NULL COMMENT '跳转业务ID:foreignKey',
    ranking               smallint       NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    delete_enum           smallint       NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id             bigint         NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date           bigint         NOT NULL COMMENT '创建时间',
    create_user_id        bigint         NOT NULL COMMENT '创建人',
    update_date           bigint         NOT NULL COMMENT '更新时间',
    update_user_id        bigint         NOT NULL COMMENT '更新人',
    delete_date           bigint         NULL COMMENT '删除时间',
    delete_user_id        bigint         NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = 'banner表';

DROP TABLE IF EXISTS sys_sms_login_log;
CREATE TABLE sys_sms_login_log
(
    id                      bigint       NOT NULL COMMENT '主键ID',
    user_id                 bigint       NOT NULL COMMENT '用户ID:foreignKey',
    user_mobile_phone       varchar(20)  NOT NULL COMMENT '用户手机号:foreignKey',
    verification_code       varchar(10)  NOT NULL COMMENT '短信验证码',
    sms_provider_type_enum  smallint     NOT NULL DEFAULT '1' COMMENT '服务商类型:[1=阿里云=ALIYUN, 2=腾讯云=TENCENT]max=2',
    bool_service_state_enum smallint     NOT NULL DEFAULT '2' COMMENT '服务商是否已验证:[1=是=YES, 2=否=NO]max=2',
    message_content         varchar(50)  NOT NULL COMMENT '短信完整内容',
    bool_use_enum           smallint     NOT NULL DEFAULT '2' COMMENT '是否已验证:[1=是=YES, 2=否=NO]max=2',
    ip_address              varchar(50)  NULL COMMENT 'IP 地址',
    user_agent              varchar(500) NULL COMMENT '浏览器 UserAgent',
    tenant_id               bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date             bigint       NOT NULL COMMENT '创建时间',
    create_user_id          bigint       NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT = '短信登录记录表';


DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job
(
    id                           bigint       NOT NULL COMMENT '主键ID',
    job_name                     varchar(50)  NOT NULL COMMENT '任务名称:oneParam,listParam',
    job_group                    varchar(50)  NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    invoke_target                varchar(500) NOT NULL COMMENT '调用目标字符串',
    cron_expression              varchar(50)  NOT NULL COMMENT 'cron 执行表达式',
    misfire_policy_enum          tinyint      NOT NULL DEFAULT '4' COMMENT '计划执行错误策略:[1=默认=DEFAULT, 2=立即执行=NOW_RUN, 3=执行一次=ONLY_RUN, 4=放弃执行=SUSPEND_RUN]max=4',
    bool_support_concurrent_enum tinyint      NOT NULL DEFAULT '2' COMMENT '是否并发执行:[1=是=YES, 2=否=NO]max=2',
    ranking                      tinyint      NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description                  varchar(500) NULL COMMENT '备注',
    state_enum                   tinyint      NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    tenant_id                    bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date                  bigint       NOT NULL COMMENT '创建时间',
    create_user_id               bigint       NOT NULL COMMENT '创建人',
    update_date                  bigint       NOT NULL COMMENT '更新时间',
    update_user_id               bigint       NOT NULL COMMENT '更新人',
    PRIMARY KEY (id)
) COMMENT = '定时任务调度表';


DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log
(
    id                        bigint       NOT NULL COMMENT '主键ID',
    job_id                    bigint       NOT NULL COMMENT '任务ID:oneToManyKey:foreignKey',
    job_name                  varchar(50)  NOT NULL COMMENT '任务名称:oneParam,listParam',
    job_group                 varchar(50)  NOT NULL COMMENT '任务组名',
    invoke_target             varchar(500) NOT NULL COMMENT '调用目标字符串',
    cron_expression           varchar(50)  NOT NULL COMMENT 'cron 执行表达式',
    job_message               varchar(250) NULL COMMENT '日志信息',
    exception_msg             varchar(250) NULL COMMENT '异常信息',
    tenant_id                 bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    description               varchar(500) NULL COMMENT '备注',
    bool_execute_success_enum tinyint      NOT NULL DEFAULT '1' COMMENT '是否执行成功:[1=是=YES, 2=否=NO]max=2',
    job_start_date            bigint       NULL COMMENT '任务开始时间',
    job_end_date              bigint       NULL COMMENT '任务结束时间',
    execute_time              bigint       NULL COMMENT '执行时间:单位毫秒',
    create_date               bigint       NOT NULL COMMENT '创建时间',
    create_user_id            bigint       NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT = '定时任务调度日志表';



DROP TABLE IF EXISTS sys_city_area;

CREATE TABLE sys_city_area
(
    id             bigint       NOT NULL COMMENT '主键ID:也是地区编码',
    parent_id      bigint       NOT NULL COMMENT '父ID',
    parent_ids     varchar(250) NOT NULL COMMENT '父ID集:多个层级用英文逗号隔开',
    area_name      varchar(100) NOT NULL COMMENT '地区名称:treeName',
    ranking        smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT = '省市区表';


DROP TABLE IF EXISTS sys_tenant;

CREATE TABLE sys_tenant
(
    id             bigint       NOT NULL COMMENT '主键ID',
    tenant_name    varchar(250) NOT NULL COMMENT '租户名称',
    tenant_code    varchar(250) NOT NULL COMMENT '租户编码:oneParam,listParam',
    description    varchar(500) NULL COMMENT '备注',
    state_enum     smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    update_date    bigint       NOT NULL COMMENT '更新时间',
    update_user_id bigint       NOT NULL COMMENT '更新人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '租户表';


DROP TABLE IF EXISTS sys_message_template;

CREATE TABLE sys_message_template
(
    id               bigint       NOT NULL COMMENT '主键ID',
    dict_item_id     bigint       NOT NULL COMMENT '模板类型字典ID',
    template_name    varchar(250) NOT NULL COMMENT '模板名称',
    template_code    varchar(250) NOT NULL COMMENT '模板编码:oneParam,listParam',
    template_content text         NOT NULL COMMENT '模板内容',
    ranking          smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description      varchar(500) NULL COMMENT '备注',
    state_enum       smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum      smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id        bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date      bigint       NOT NULL COMMENT '创建时间',
    create_user_id   bigint       NOT NULL COMMENT '创建人',
    update_date      bigint       NOT NULL COMMENT '更新时间',
    update_user_id   bigint       NOT NULL COMMENT '更新人',
    delete_date      bigint       NULL COMMENT '删除时间',
    delete_user_id   bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '消息模板-freemarker';

DROP TABLE IF EXISTS sys_message_log;

CREATE TABLE sys_message_log
(
    id                        bigint       NOT NULL COMMENT '主键ID',
    receive_user_id           bigint       NOT NULL COMMENT '接收者ID:foreignKey',
    send_user_id              bigint       NOT NULL COMMENT '发送者ID:foreignKey',
    dict_item_id              bigint       NOT NULL COMMENT '模板类型字典ID:foreignKey',
    template_id               bigint       NOT NULL COMMENT '模板ID:foreignKey',
    message_title             varchar(250) NOT NULL COMMENT '消息名称',
    message_content           text         NOT NULL COMMENT '消息内容',
    bool_send_success_enum    smallint     NOT NULL DEFAULT '1' COMMENT '是否发送成功:[1=是=YES, 2=否=NO]max=2',
    bool_receive_success_enum smallint     NOT NULL DEFAULT '1' COMMENT '是否接收成功:[1=是=YES, 2=否=NO]max=2',
    bool_job_send_enum        smallint     NOT NULL DEFAULT '1' COMMENT '是否定时发送:[1=是=YES, 2=否=NO]max=2',
    job_send_date             bigint       NULL COMMENT '定时发送时间',
    state_enum                smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum               smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id                 bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date               bigint       NOT NULL COMMENT '创建时间',
    create_user_id            bigint       NOT NULL COMMENT '创建人',
    update_date               bigint       NOT NULL COMMENT '更新时间',
    update_user_id            bigint       NOT NULL COMMENT '更新人',
    delete_date               bigint       NULL COMMENT '删除时间',
    delete_user_id            bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '消息记录';

DROP TABLE IF EXISTS sys_search_log;

CREATE TABLE sys_search_log
(
    id             bigint       NOT NULL COMMENT '主键ID',
    user_id        bigint       NOT NULL COMMENT '搜索用户ID:foreignKey',
    search_content varchar(250) NOT NULL COMMENT '搜索内容',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '搜索记录';

DROP TABLE IF EXISTS sys_search_recommend;

CREATE TABLE sys_search_recommend
(
    id             bigint       NOT NULL COMMENT '主键ID',
    search_keyword varchar(250) NOT NULL COMMENT '搜索推荐词',
    ranking        smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description    varchar(500) NULL COMMENT '备注',
    state_enum     smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    update_date    bigint       NOT NULL COMMENT '更新时间',
    update_user_id bigint       NOT NULL COMMENT '更新人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '搜索推荐标签';


DROP TABLE IF EXISTS sys_folder_info;

CREATE TABLE sys_folder_info
(
    id             bigint       NOT NULL COMMENT '主键ID',
    parent_id      bigint       NOT NULL COMMENT '父ID',
    parent_ids     varchar(250) NOT NULL COMMENT '父ID集:多个层级用英文逗号隔开',
    folder_name    varchar(50)  NOT NULL COMMENT '文件夹名称:treeName:oneParam,listParam',
    ranking        smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description    varchar(500) NULL COMMENT '备注',
    bool_top_enum  smallint     NOT NULL DEFAULT '2' COMMENT '是否置顶:[1=是=YES, 2=否=NO]max=2',
    state_enum     smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    update_date    bigint       NOT NULL COMMENT '更新时间',
    update_user_id bigint       NOT NULL COMMENT '更新人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '文件夹表';

DROP TABLE IF EXISTS sys_file_info;

CREATE TABLE sys_file_info
(
    id                     bigint       NOT NULL COMMENT '主键ID',
    folder_id              bigint       NOT NULL COMMENT '文件夹ID:foreignKey',
    file_show_name         varchar(200) NOT NULL COMMENT '文件显示名称:oneParam,listParam',
    file_storage_name      varchar(200) NOT NULL COMMENT '文件存储名称:oneParam,listParam',
    file_suffix            varchar(20)  NULL COMMENT '文件后缀:oneParam,listParam',
    file_storage_path      varchar(300) NOT NULL COMMENT '文件存储路径',
    file_full_url          varchar(500) NULL COMMENT '文件完整url路径',
    file_storage_type_enum smallint     NOT NULL DEFAULT '1' COMMENT '存储渠道:[1=阿里云=ALIYUN, 2=又拍云=UPYUN]max=2',
    file_size              bigint       NOT NULL COMMENT '文件大小:单位字节byte',
    ranking                smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description            varchar(500) NULL COMMENT '备注',
    bool_top_enum          smallint     NOT NULL DEFAULT '2' COMMENT '是否置顶:[1=是=YES, 2=否=NO]max=2',
    bool_oss_complete_enum smallint     NOT NULL DEFAULT '1' COMMENT '是否完成上传:[1=是=YES, 2=否=NO]max=2',
    bool_oss_delete_enum   smallint     NOT NULL DEFAULT '2' COMMENT 'OSS真实删除状态:[1=是=YES, 2=否=NO]max=2',
    state_enum             smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum            smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id              bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date            bigint       NOT NULL COMMENT '创建时间',
    create_user_id         bigint       NOT NULL COMMENT '创建人',
    update_date            bigint       NOT NULL COMMENT '更新时间',
    update_user_id         bigint       NOT NULL COMMENT '更新人',
    delete_date            bigint       NULL COMMENT '删除时间',
    delete_user_id         bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '文件表';



DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    id                   bigint       NOT NULL COMMENT '主键ID',
    username             varchar(50)  NOT NULL COMMENT '用户账号:oneParam',
    nickname             varchar(100) NULL COMMENT '昵称:oneParam',
    real_name            varchar(50)  NULL COMMENT '真实姓名:likeParam',
    user_password        varchar(50)  NOT NULL COMMENT '登录密码',
    password_salt        varchar(10)  NOT NULL COMMENT '密码盐:放于密码后面',
    user_email           varchar(50)  NULL COMMENT '邮箱地址:oneParam',
    telephone            varchar(20)  NULL COMMENT '固话',
    mobile_phone         varchar(20)  NULL COMMENT '手机号:oneParam',
    avatar_url           varchar(500) NULL COMMENT '头像',
    gender_enum          smallint     NOT NULL DEFAULT '1' COMMENT '性别:[1=保密=PRIVACY, 2=男性=MALE, 3=女性=FEMALE]max=3',
    user_type_enum       smallint     NOT NULL DEFAULT '1' COMMENT '用户类型:[1=普通用户=USER, 2=后台管理员=ADMIN, 3=租户主账号=TENANT_ADMIN, 4=租户子账号=TENANT_SUB_ACCOUNT]max=4',
    tenant_id            bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    register_type_enum   smallint     NOT NULL DEFAULT '1' COMMENT '注册方式:[1=系统预置=SYSTEM_INIT, 2=后台管理系统新增=MANAGEMENT_ADD, 3=主动注册=REGISTER, 4=被邀请注册=INVITE]max=4',
    register_origin_enum smallint     NOT NULL DEFAULT '1' COMMENT '注册来源:[1=WEB方式=WEB, 2=安卓APP=ANDROID, 3=苹果APP=IOS, 4=H5=H5, 5=微信小程序=WEIXIN_MINI_PROGRAM, 6=微信公众号=WEIXIN_OFFICIAL_ACCOUNT]max=6',
    state_enum           smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum          smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    create_date          bigint       NOT NULL COMMENT '创建时间',
    create_user_id       bigint       NOT NULL COMMENT '创建人',
    update_date          bigint       NOT NULL COMMENT '更新时间',
    update_user_id       bigint       NOT NULL COMMENT '更新人',
    delete_date          bigint       NULL COMMENT '删除时间',
    delete_user_id       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='用户表';

DROP TABLE IF EXISTS sys_user_info;

CREATE TABLE sys_user_info
(
    id              bigint       NOT NULL COMMENT '主键ID',
    user_id         bigint       NOT NULL COMMENT '用户ID:foreignKey',
    weixin_openid   varchar(40)  NULL COMMENT '微信openid:oneParam,listParam',
    weixin_unionid  varchar(40)  NULL COMMENT '微信unionid:oneParam,listParam',
    weixin_userinfo varchar(800) NULL COMMENT '微信用户信息',
    id_card         varchar(30)  NULL COMMENT '身份证号:oneParam,listParam',
    tenant_id       bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date     bigint       NOT NULL COMMENT '创建时间',
    create_user_id  bigint       NOT NULL COMMENT '创建人',
    update_date     bigint       NOT NULL COMMENT '更新时间',
    update_user_id  bigint       NOT NULL COMMENT '更新人',
    PRIMARY KEY (id)
) COMMENT ='用户信息扩展表';


DROP TABLE IF EXISTS sys_login_log;

CREATE TABLE sys_login_log
(
    id                      bigint       NOT NULL COMMENT '主键ID',
    user_id                 bigint       NOT NULL COMMENT '用户ID:foreignKey',
    username                varchar(50)  NOT NULL COMMENT '用户账号:oneParam,listParam',
    client_id               varchar(50)  NULL COMMENT '客户端账号',
    token                   varchar(50)  NULL COMMENT '登录成功令牌:oneParam',
    message                 varchar(250) NULL COMMENT '记录信息',
    login_date              bigint       NULL COMMENT '登录时间',
    logout_date             bigint       NULL COMMENT '登出时间',
    request_url             varchar(500) NULL COMMENT '请求 URL',
    bool_login_success_enum smallint     NOT NULL DEFAULT '1' COMMENT '是否登录成功:[1=是=YES, 2=否=NO]max=2',
    bool_now_online_enum    smallint     NOT NULL DEFAULT '1' COMMENT '当前是否在线:[1=是=YES, 2=否=NO]max=2',
    offline_type_enum       smallint     NULL COMMENT '登出方式:[1=主动登出=PEOPLE_LOGOUT, 2=过期登出=EXPIRE_LOGOUT, 3=后台踢出=BACKEND_LOGOUT]max=2',
    exception_msg           varchar(250) NULL COMMENT '失败异常信息',
    tenant_id               bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    ip_address              varchar(50)  NULL COMMENT 'IP 地址',
    ip_region               varchar(100) NULL COMMENT 'IP 信息',
    ip_region_country       varchar(50)  NULL COMMENT 'IP 地址对应的国家',
    ip_region_province      varchar(50)  NULL COMMENT 'IP 地址对应的省',
    ip_region_city          varchar(50)  NULL COMMENT 'IP 地址对应的市',
    ip_region_isp           varchar(50)  NULL COMMENT 'IP 地址对应的网络提供商',
    user_agent              varchar(500) NULL COMMENT '浏览器 UserAgent',
    device_name             varchar(50)  NULL COMMENT '设备名称',
    os_name                 varchar(50)  NULL COMMENT '系统名称',
    browser_name            varchar(50)  NULL COMMENT '浏览器',
    browser_locale          varchar(50)  NULL COMMENT '语言区域',
    bool_new_user_enum      smallint     NOT NULL DEFAULT '1' COMMENT '是否是新用户:[1=是=YES, 2=否=NO]max=2',
    login_origin_enum       smallint     NOT NULL DEFAULT '1' COMMENT '登录来源:[1=WEB方式=WEB, 2=安卓APP=ANDROID, 3=苹果APP=IOS, 4=H5=H5, 5=微信小程序=WEIXIN_MINI_PROGRAM, 6=微信公众号=WEIXIN_OFFICIAL_ACCOUNT]max=6',
    create_date             bigint       NOT NULL COMMENT '创建时间',
    create_user_id          bigint       NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT ='登录事件记录表';


DROP TABLE IF EXISTS sys_event_log;

CREATE TABLE sys_event_log
(
    id                        bigint       NOT NULL COMMENT '主键ID',
    user_id                   bigint       NULL COMMENT '用户ID:foreignKey',
    username                  varchar(50)  NULL COMMENT '用户账号:oneParam,listParam',
    message                   varchar(250) NULL COMMENT '记录信息',
    execute_time              bigint       NULL COMMENT '执行时间:单位毫秒',
    request_date              bigint       NULL COMMENT '访问时间',
    request_url               varchar(500) NULL COMMENT '请求 URL',
    request_method            varchar(50)  NULL COMMENT '请求方法名',
    request_param             varchar(500) NULL COMMENT '请求参数',
    bool_execute_success_enum smallint     NOT NULL DEFAULT '1' COMMENT '是否执行成功:[1=是=YES, 2=否=NO]max=2',
    operate_type_enum         smallint     NOT NULL DEFAULT '1' COMMENT '事件类型:[1=查询=QUERY, 2=创建=CREATE, 3=修改对象=UPDATE_INFO,4=修改状态=UPDATE_STATE,5=删除=DELETE,6=导入=IMPORT,7=导出=EXPORT]max=7',
    exception_msg             varchar(250) NULL COMMENT '失败异常信息',
    tenant_id                 bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    ip_address                varchar(50)  NULL COMMENT 'IP 地址',
    ip_region                 varchar(100) NULL COMMENT 'IP 信息',
    ip_region_country         varchar(50)  NULL COMMENT 'IP 地址对应的国家',
    ip_region_province        varchar(50)  NULL COMMENT 'IP 地址对应的省',
    ip_region_city            varchar(50)  NULL COMMENT 'IP 地址对应的市',
    ip_region_isp             varchar(50)  NULL COMMENT 'IP 地址对应的网络提供商',
    user_agent                varchar(500) NULL COMMENT '浏览器 UserAgent',
    device_name               varchar(50)  NULL COMMENT '设备名称',
    os_name                   varchar(50)  NULL COMMENT '系统名称',
    browser_name              varchar(50)  NULL COMMENT '浏览器',
    browser_locale            varchar(50)  NULL COMMENT '语言区域',
    create_date               bigint       NOT NULL COMMENT '创建时间',
    create_user_id            bigint       NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT ='操作事件记录表';



DROP TABLE IF EXISTS sys_dict;

CREATE TABLE sys_dict
(
    id                   bigint       NOT NULL COMMENT '主键ID',
    dict_name            varchar(50)  NOT NULL COMMENT '字典名称',
    dict_code            varchar(100) NOT NULL COMMENT '字典编码:oneParam,listParam',
    dict_value_type_enum smallint     NOT NULL DEFAULT '1' COMMENT '字典值类型:[1=java.lang.String=String, 2=java.lang.Boolean=Boolean, 3=java.lang.Integer=Integer, 4=java.lang.Long=Long, 5=java.lang.Double=Double]max=5',
    ranking              smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description          varchar(500) NULL COMMENT '备注',
    state_enum           smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum          smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id            bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date          bigint       NOT NULL COMMENT '创建时间',
    create_user_id       bigint       NOT NULL COMMENT '创建人',
    update_date          bigint       NOT NULL COMMENT '更新时间',
    update_user_id       bigint       NOT NULL COMMENT '更新人',
    delete_date          bigint       NULL COMMENT '删除时间',
    delete_user_id       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='字典表';

DROP TABLE IF EXISTS sys_dict_item;

CREATE TABLE sys_dict_item
(
    id                   bigint       NOT NULL COMMENT '主键ID',
    dict_id              bigint       NOT NULL COMMENT '字典ID:foreignKey',
    dict_code            varchar(100) NOT NULL COMMENT '字典编码:oneParam,listParam',
    item_name            varchar(50)  NOT NULL COMMENT '字典子项名称',
    item_code            varchar(100) NOT NULL COMMENT '字典子项编码:oneParam,listParam',
    item_value           varchar(250) NULL COMMENT '字典子项值',
    dict_value_type_enum smallint     NOT NULL DEFAULT '1' COMMENT '字典值类型:[1=java.lang.String=String, 2=java.lang.Boolean=Boolean, 3=java.lang.Integer=Integer, 4=java.lang.Long=Long, 5=java.lang.Double=Double]max=5',
    ranking              smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description          varchar(500) NULL COMMENT '备注',
    state_enum           smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum          smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id            bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date          bigint       NOT NULL COMMENT '创建时间',
    create_user_id       bigint       NOT NULL COMMENT '创建人',
    update_date          bigint       NOT NULL COMMENT '更新时间',
    update_user_id       bigint       NOT NULL COMMENT '更新人',
    delete_date          bigint       NULL COMMENT '删除时间',
    delete_user_id       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='字典子项表';

DROP TABLE IF EXISTS sys_param_type;

CREATE TABLE sys_param_type
(
    id             bigint       NOT NULL COMMENT '主键ID',
    type_name      varchar(50)  NOT NULL COMMENT '类型名称',
    type_code      varchar(100) NOT NULL COMMENT '类型编码:oneParam,listParam',
    ranking        smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description    varchar(500) NULL COMMENT '备注',
    state_enum     smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    update_date    bigint       NOT NULL COMMENT '更新时间',
    update_user_id bigint       NOT NULL COMMENT '更新人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='参数类型表';

DROP TABLE IF EXISTS sys_param;

CREATE TABLE sys_param
(
    id                    bigint       NOT NULL COMMENT '主键ID',
    type_id               bigint       NOT NULL COMMENT '参数类型ID:foreignKey',
    type_code             varchar(100) NOT NULL COMMENT '参数类型编码:oneParam,listParam',
    param_name            varchar(50)  NOT NULL COMMENT '参数名称',
    param_code            varchar(100) NOT NULL COMMENT '参数编码:oneParam,listParam',
    param_value           varchar(500) NULL COMMENT '参数值',
    ranking               smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description           varchar(500) NULL COMMENT '备注',
    param_value_type_enum smallint     NOT NULL DEFAULT '1' COMMENT '参数值类型:[1=java.lang.String=String, 2=java.lang.Boolean=Boolean, 3=java.lang.Integer=Integer, 4=java.lang.Long=Long, 5=java.lang.Double=Double]max=5',
    state_enum            smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum           smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id             bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date           bigint       NOT NULL COMMENT '创建时间',
    create_user_id        bigint       NOT NULL COMMENT '创建人',
    update_date           bigint       NOT NULL COMMENT '更新时间',
    update_user_id        bigint       NOT NULL COMMENT '更新人',
    delete_date           bigint       NULL COMMENT '删除时间',
    delete_user_id        bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='参数表';

DROP TABLE IF EXISTS sys_role;

CREATE TABLE sys_role
(
    id                 bigint       NOT NULL COMMENT '主键ID',
    role_name          varchar(50)  NOT NULL COMMENT '角色名称',
    role_code          varchar(50)  NOT NULL COMMENT '角色编码:oneParam,listParam',
    ranking            smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description        varchar(500) NULL COMMENT '备注',
    bool_template_enum smallint     NOT NULL DEFAULT '1' COMMENT '是否是租户模板角色:[1=是=YES, 2=否=NO]max=2',
    state_enum         smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum        smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id          bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date        bigint       NOT NULL COMMENT '创建时间',
    create_user_id     bigint       NOT NULL COMMENT '创建人',
    update_date        bigint       NOT NULL COMMENT '更新时间',
    update_user_id     bigint       NOT NULL COMMENT '更新人',
    delete_date        bigint       NULL COMMENT '删除时间',
    delete_user_id     bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '角色表';

DROP TABLE IF EXISTS rel_role_user;

CREATE TABLE rel_role_user
(
    id             bigint NOT NULL COMMENT '主键ID',
    role_id        bigint NOT NULL COMMENT '角色ID:foreignKey',
    user_id        bigint NOT NULL COMMENT '用户ID:foreignKey',
    tenant_id      bigint NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint NOT NULL COMMENT '创建时间',
    create_user_id bigint NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT = '角色用户中间表';

DROP TABLE IF EXISTS sys_employee;

CREATE TABLE sys_employee
(
    id             bigint      NOT NULL COMMENT '主键ID',
    user_id        bigint      NOT NULL COMMENT '用户ID:foreignKey',
    work_card_id   varchar(50) NULL COMMENT '工号',
    job_position   varchar(50) NULL COMMENT '职位',
    delete_enum    smallint    NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint      NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint      NOT NULL COMMENT '创建时间',
    create_user_id bigint      NOT NULL COMMENT '创建人',
    update_date    bigint      NOT NULL COMMENT '更新时间',
    update_user_id bigint      NOT NULL COMMENT '更新人',
    delete_date    bigint      NULL COMMENT '删除时间',
    delete_user_id bigint      NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '员工信息表';

DROP TABLE IF EXISTS sys_dept;

CREATE TABLE sys_dept
(
    id             bigint       NOT NULL COMMENT '主键ID',
    dept_name      varchar(50)  NOT NULL COMMENT '部门名称:treeName',
    dept_code      varchar(50)  NOT NULL COMMENT '部门编码:oneParam,listParam',
    parent_id      bigint       NOT NULL COMMENT '父ID',
    parent_ids     varchar(250) NOT NULL COMMENT '父ID集:多个层级用英文逗号隔开',
    leader_user_id bigint       NULL COMMENT '部门领导用户ID',
    telephone      varchar(50)  NULL COMMENT '固话',
    mobile_phone   varchar(50)  NULL COMMENT '手机号',
    dept_fax       varchar(50)  NULL COMMENT '传真',
    dept_address   varchar(250) NULL COMMENT '地址',
    ranking        smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description    varchar(500) NULL COMMENT '备注',
    state_enum     smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum    smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id      bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint       NOT NULL COMMENT '创建时间',
    create_user_id bigint       NOT NULL COMMENT '创建人',
    update_date    bigint       NOT NULL COMMENT '更新时间',
    update_user_id bigint       NOT NULL COMMENT '更新人',
    delete_date    bigint       NULL COMMENT '删除时间',
    delete_user_id bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT = '部门表';

DROP TABLE IF EXISTS rel_dept_user;

CREATE TABLE rel_dept_user
(
    id             bigint NOT NULL COMMENT '主键ID',
    dept_id        bigint NOT NULL COMMENT '部门ID:foreignKey',
    user_id        bigint NOT NULL COMMENT '用户ID:foreignKey',
    tenant_id      bigint NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint NOT NULL COMMENT '创建时间',
    create_user_id bigint NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT = '部门用户中间表';


DROP TABLE IF EXISTS sys_permission;

CREATE TABLE sys_permission
(
    id                   bigint       NOT NULL COMMENT '主键ID',
    permission_name      varchar(50)  NOT NULL COMMENT '权限名称:treeName',
    permission_code      varchar(50)  NOT NULL COMMENT '权限标识码:oneParam,listParam',
    permission_url       varchar(500) NULL COMMENT '路由地址:如果是外部链接则代表链接地址',
    belong_type_enum     smallint     NOT NULL DEFAULT '1' COMMENT '归属类型:[1=所以=ALL, 2=管理后台=ADMIN, 3=租户=TENANT]max=3',
    permission_type_enum smallint     NOT NULL DEFAULT '1' COMMENT '权限类型:[1=一级目录=TOP_DIRECTORY, 2=菜单=MENU, 3=按钮=BUTTON]max=3',
    parent_id            bigint       NOT NULL COMMENT '父ID',
    parent_ids           varchar(250) NOT NULL COMMENT '父ID集',
    icon_class           varchar(250) NULL COMMENT '权限图标',
    visible_enum         smallint     NOT NULL DEFAULT '1' COMMENT '显示状态:[1=显示=DISPLAY, 2=隐藏=HIDDEN]max=2',
    bool_ext_link_enum   smallint     NOT NULL DEFAULT '2' COMMENT '是否外链:[1=是=YES, 2=否=NO]max=2',
    bool_new_tab_enum    smallint     NOT NULL DEFAULT '2' COMMENT '是否新标签打开:[1=是=YES, 2=否=NO]max=2',
    ranking              smallint     NOT NULL DEFAULT '100' COMMENT '排序:排序值越小越排前,max=100',
    description          varchar(500) NULL COMMENT '备注',
    state_enum           smallint     NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum          smallint     NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    tenant_id            bigint       NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date          bigint       NOT NULL COMMENT '创建时间',
    create_user_id       bigint       NOT NULL COMMENT '创建人',
    update_date          bigint       NOT NULL COMMENT '更新时间',
    update_user_id       bigint       NOT NULL COMMENT '更新人',
    delete_date          bigint       NULL COMMENT '删除时间',
    delete_user_id       bigint       NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='权限资源表';

DROP TABLE IF EXISTS rel_permission_role;

CREATE TABLE rel_permission_role
(
    id             bigint NOT NULL COMMENT '主键ID',
    permission_id  bigint NOT NULL COMMENT '权限ID:foreignKey',
    role_id        bigint NOT NULL COMMENT '角色ID:foreignKey',
    tenant_id      bigint NOT NULL DEFAULT '1' COMMENT '所属租户',
    create_date    bigint NOT NULL COMMENT '创建时间',
    create_user_id bigint NOT NULL COMMENT '创建人',
    PRIMARY KEY (id)
) COMMENT = '权限角色中间表';










