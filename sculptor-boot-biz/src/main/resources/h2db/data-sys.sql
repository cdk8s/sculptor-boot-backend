INSERT INTO sys_job(id, job_name, job_group, invoke_target, cron_expression, misfire_policy_enum, bool_support_concurrent_enum, ranking, description, state_enum, tenant_id, create_date, create_user_id, update_date, update_user_id)
VALUES (111111111111111111, '无参任务名称', '默认任务组', 'myTestTask.ryNoParams', '0 0 10 * * ?', 4, 2, 100, NULL, 1, 1, 1573839167674, 111111111111111111, 1573839167674, 111111111111111111);
INSERT INTO sys_job(id, job_name, job_group, invoke_target, cron_expression, misfire_policy_enum, bool_support_concurrent_enum, ranking, description, state_enum, tenant_id, create_date, create_user_id, update_date, update_user_id)
VALUES (222222222222222222, '有参任务名称', '默认任务组', 'myTestTask.ryParams(\"ry\")', '0 0 10 * * ?', 4, 2, 100, NULL, 1, 1, 1573839167674, 111111111111111111, 1573839167674, 111111111111111111);
INSERT INTO sys_job(id, job_name, job_group, invoke_target, cron_expression, misfire_policy_enum, bool_support_concurrent_enum, ranking, description, state_enum, tenant_id, create_date, create_user_id, update_date, update_user_id)
VALUES (333333333333333333, '多参任务名称', '默认任务组', 'myTestTask.ryMultipleParams(\"ry\", true, 2000L, 316.50D, 100)', '0 0 10 * * ?', 4, 2, 100, NULL, 1, 1, 1573839167674, 111111111111111111, 1573839167674, 111111111111111111);

INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, 1, '1', '文件夹1', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);
INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111112, 111111111111111111, '1,111111111111111111', '文件夹11', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);
INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111113, 111111111111111111, '1,111111111111111111', '文件夹12', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);
INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111114, 111111111111111111, '1,111111111111111111', '文件夹13', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);
INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111115, 1, '1', '文件夹2', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);
INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111116, 111111111111111115, '1,111111111111111115', '文件夹21', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);
INSERT INTO sys_folder_info(id, parent_id, parent_ids, folder_name, ranking, description, bool_top_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111117, 111111111111111115, '1,111111111111111115', '文件夹3', 100, '文件夹1', 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);

INSERT INTO sys_file_info(id, folder_id, file_show_name, file_storage_name, file_suffix, file_storage_path, file_full_url, file_storage_type_enum, file_size, ranking, description, bool_top_enum, bool_oss_complete_enum, bool_oss_delete_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, 111111111111111111, '这是文件中文名11.png', 'def4b804d91c4775b206a94d248d63dd.png', 'png', '/code-upload/2019-12/30/17', 'https://cdk8s.oss-cn-shenzhen.aliyuncs.com/code-upload/2019-12/30/17/def4b804d91c4775b206a94d248d63dd.png', 1, 394, 100, NULL, 2, 1, 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL),
       (111111111111111112, 111111111111111112, '这是文件中文名12.png', 'def4b804d91c4775b206a94d248d63dd.png', 'png', '/code-upload/2019-12/30/17', 'https://cdk8s.oss-cn-shenzhen.aliyuncs.com/code-upload/2019-12/30/17/def4b804d91c4775b206a94d248d63dd.png', 1, 394, 100, NULL, 2, 1, 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL),
       (111111111111111113, 111111111111111112, '这是文件中文名13.png', 'def4b804d91c4775b206a94d248d63dd.png', 'png', '/code-upload/2019-12/30/17', 'https://cdk8s.oss-cn-shenzhen.aliyuncs.com/code-upload/2019-12/30/17/def4b804d91c4775b206a94d248d63dd.png', 1, 394, 100, NULL, 2, 1, 2, 1, 1, 1, 1574663813780, 111111111111111111, 1574663813780, 111111111111111111, NULL, NULL);

INSERT INTO sys_tenant(id, tenant_name, tenant_code, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (1, '默认租户', 'system', NULL, 1, 1, 1, 1574739322708, 111111111111111111, 1574739322708, 111111111111111111, NULL, NULL);

INSERT INTO sys_user(id, username, real_name, user_password, password_salt, user_email, telephone, mobile_phone, avatar_url, gender_enum, user_type_enum, tenant_id, register_type_enum, register_origin_enum, state_enum, delete_enum, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, 'admin', 'admin', '42c224b3c8899047460f5a6d1c041411', '1234567890', 'cdk8s@qq.com', '13800000000', '13800000000', 'https://dummyimage.com/60x60.png', 1, 2, 1, 1, 1, 1, 1, 1514736123456, 111111111111111111, 1514736123456, 111111111111111111, NULL, NULL);
INSERT INTO sys_user(id, username, real_name, user_password, password_salt, user_email, telephone, mobile_phone, avatar_url, gender_enum, user_type_enum, tenant_id, register_type_enum, register_origin_enum, state_enum, delete_enum, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (222222222222222222, 'demo', 'demo', '42c224b3c8899047460f5a6d1c041411', '1234567890', 'cdk8s-demo@qq.com', '13800000011', '13800000011', 'https://dummyimage.com/60x60.png', 1, 1, 1, 1, 1, 1, 1, 1514736123456, 111111111111111111, 1514736123456, 111111111111111111, NULL, NULL);
INSERT INTO sys_user(id, username, real_name, user_password, password_salt, user_email, telephone, mobile_phone, avatar_url, gender_enum, user_type_enum, tenant_id, register_type_enum, register_origin_enum, state_enum, delete_enum, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (333333333333333331, 'demo1', '李四老师', '42c224b3c8899047460f5a6d1c041411', '1234567890', 'cdk8s-1@qq.com', '13800000001', '13800000001', 'https://dummyimage.com/60x60.png', 1, 1, 1, 1, 1, 1, 1, 1514736123456, 111111111111111111, 1514736123456, 111111111111111111, NULL, NULL);

INSERT INTO sys_employee(id, user_id, work_card_id, job_position, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, 111111111111111111, '11', '总裁', 1, 1, 1514736123456, 111111111111111111, 1514736123456, 111111111111111111, NULL, NULL);
INSERT INTO sys_employee(id, user_id, work_card_id, job_position, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (222222222222222222, 222222222222222222, '22', '员工', 1, 1, 1514736123456, 111111111111111111, 1514736123456, 111111111111111111, NULL, NULL);
INSERT INTO sys_employee(id, user_id, work_card_id, job_position, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (333333333333333331, 333333333333333331, '31', '员工', 1, 1, 1514736123456, 111111111111111111, 1514736123456, 111111111111111111, NULL, NULL);

INSERT INTO sys_role(id, role_name, role_code, ranking, description, bool_template_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, '超级管理员', 'top_admin_role', 1, '超级管理员，预设数据，无法删除和修改', 1, 1, 1, 1, 1574663780828, 111111111111111111, 1574663780828, 111111111111111111, NULL, NULL);
INSERT INTO sys_role(id, role_name, role_code, ranking, description, bool_template_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (222222222222222222, '演示用户', 'demo_user_role', 1, '演示用户，预设数据，无法删除和修改', 1, 1, 1, 1, 1574663780828, 111111111111111111, 1574663780828, 111111111111111111, NULL, NULL);
INSERT INTO sys_role(id, role_name, role_code, ranking, description, bool_template_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (333333333333333333, '普通用户', 'normal_user_role', 1, '普通用户，预设数据，无法删除和修改', 1, 1, 1, 1, 1574663780828, 111111111111111111, 1574663780828, 111111111111111111, NULL, NULL);

INSERT INTO rel_role_user(id, role_id, user_id, tenant_id, create_date, create_user_id)
VALUES (111111111111111111, 111111111111111111, 111111111111111111, 1, 1574663798481, 111111111111111111);
INSERT INTO rel_role_user(id, role_id, user_id, tenant_id, create_date, create_user_id)
VALUES (222222222222222222, 222222222222222222, 222222222222222222, 1, 1574663798481, 111111111111111111);
INSERT INTO rel_role_user(id, role_id, user_id, tenant_id, create_date, create_user_id)
VALUES (333333333333333331, 333333333333333333, 333333333333333331, 1, 1574663798481, 111111111111111111);


INSERT INTO sys_dept(id, dept_name, dept_code, parent_id, parent_ids, leader_user_id, telephone, mobile_phone, dept_fax, dept_address, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, '超级管理员部门', 'top_admin_dept', 1, '1', 111111111111111111, '87600771', '13800000000', '87600772', '广东广州', 1, '这是描述', 1, 1, 1, 1574739322708, 111111111111111111, 1574739322708, 111111111111111111, NULL, NULL);
INSERT INTO sys_dept(id, dept_name, dept_code, parent_id, parent_ids, leader_user_id, telephone, mobile_phone, dept_fax, dept_address, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (222222222222222222, '演示用户部门', 'demo_user_dept', 1, '1', 111111111111111111, '87600771', '13800000000', '87600772', '广东广州', 1, '这是描述', 1, 1, 1, 1574739322708, 111111111111111111, 1574739322708, 111111111111111111, NULL, NULL);
INSERT INTO sys_dept(id, dept_name, dept_code, parent_id, parent_ids, leader_user_id, telephone, mobile_phone, dept_fax, dept_address, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (333333333333333333, '普通用户部门', 'normal_user_dept', 1, '1', 111111111111111111, '87600771', '13800000000', '87600772', '广东广州', 1, '这是描述', 1, 1, 1, 1574739322708, 111111111111111111, 1574739322708, 111111111111111111, NULL, NULL);


INSERT INTO rel_dept_user(id, dept_id, user_id, tenant_id, create_date, create_user_id)
VALUES (111111111111111111, 111111111111111111, 111111111111111111, 1, 1574739347804, 111111111111111111);
INSERT INTO rel_dept_user(id, dept_id, user_id, tenant_id, create_date, create_user_id)
VALUES (222222222222222222, 222222222222222222, 111111111111111111, 1, 1574739347804, 111111111111111111);
INSERT INTO rel_dept_user(id, dept_id, user_id, tenant_id, create_date, create_user_id)
VALUES (333333333333333333, 222222222222222222, 222222222222222222, 1, 1574739347804, 111111111111111111);
INSERT INTO rel_dept_user(id, dept_id, user_id, tenant_id, create_date, create_user_id)
VALUES (444444444444444441, 333333333333333333, 333333333333333331, 1, 1574739347804, 111111111111111111);

INSERT INTO sys_dict(id, dict_name, dict_code, dict_value_type_enum, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, '职称', 'job_title', 1, 100, '公司内部职称', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL);


INSERT INTO sys_dict_item(id, dict_id, dict_code, item_name, item_code, item_value, dict_value_type_enum, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111111, 111111111111111111, 'job_title', '初级', 'beginner_level', '1', 1, 100, '初级', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL);
INSERT INTO sys_dict_item(id, dict_id, dict_code, item_name, item_code, item_value, dict_value_type_enum, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (222222222222222222, 111111111111111111, 'job_title', '中级', 'intermediate_level', '2', 1, 100, '中级', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL);
INSERT INTO sys_dict_item(id, dict_id, dict_code, item_name, item_code, item_value, dict_value_type_enum, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (333333333333333333, 111111111111111111, 'job_title', '高级', 'advanced_level', '3', 1, 100, '高级', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL);

INSERT INTO sys_param_type(id, type_name, type_code, ranking, description, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111110, '网站配置', 'website_config', 100, '网站配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111111, '系统配置', 'system_config', 100, '系统配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111112, '图片水印配置', 'image_watermark_config', 100, '图片水印配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111113, '文字水印配置', 'text_watermark_config', 100, '文字水印配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111114, '图片上传配置', 'image_upload_config', 100, '图片上传配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111115, '又拍云配置', 'upyun_config', 100, '又拍云配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111116, '阿里云OSS配置', 'aliyun_oss_config', 100, '阿里云OSS配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111117, '页头页尾代码配置', 'header_footer_code_config', 100, '页头页尾代码配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111118, '素材库配置', 'material_library_config', 100, '素材库配置', 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL);



INSERT INTO sys_param(id, type_id, type_code, param_name, param_code, param_value, ranking, description, param_value_type_enum, state_enum, delete_enum, tenant_id, create_date, create_user_id, update_date, update_user_id, delete_date, delete_user_id)
VALUES (111111111111111001, 111111111111111110, 'website_config', '网站根域名', 'url.websiteInfo.string', 'http://www.YouMeek.com', 100, '网站根域名，尾巴不带斜杠', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111002, 111111111111111110, 'website_config', '网站标题', 'title.websiteInfo.string', '这是网站标题', 100, '网站标题', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111003, 111111111111111110, 'website_config', '网站关键字', 'keyword.websiteInfo.string', '关键字1,关键字2', 100, '网站关键字，多个用英文逗号隔开', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111004, 111111111111111110, 'website_config', '网站描述', 'desc.websiteInfo.string', '这是网站描述', 100, '网站描述', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111005, 111111111111111110, 'website_config', 'PC端logo图片地址', 'url.websiteInfoPcLogoUrl.string', 'https://img.alicdn.com/tps/i4/TB1k8m3rVT7gK0jSZFpSuuTkpXa.jpg_240x240q90.jpg', 100, 'PC端logo图片地址', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111006, 111111111111111110, 'website_config', 'H5端logo图片地址', 'url.websiteInfoH5LogoUrl.string', 'https://img.alicdn.com/tps/i4/TB1k8m3rVT7gK0jSZFpSuuTkpXa.jpg_240x240q90.jpg', 100, 'H5端logo图片地址', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111007, 111111111111111110, 'website_config', '联系电话', 'telephone.websiteInfo.string', '13800000000', 100, '联系电话', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111008, 111111111111111110, 'website_config', '联系邮箱', 'email.websiteInfo.string', 'cdk8s@qq.com', 100, '联系邮箱', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111009, 111111111111111110, 'website_config', '微信二维码图片地址', 'qr.websiteInfoWeixin.string', 'https://img.alicdn.com/tps/i4/TB1k8m3rVT7gK0jSZFpSuuTkpXa.jpg_240x240q90.jpg', 100, '微信二维码图片地址', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111010, 111111111111111110, 'website_config', '联系地址', 'address.websiteInfo.string', '广东广州', 100, '联系地址', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111011, 111111111111111110, 'website_config', '公司地图代码', 'iframe.websiteInfoMapApi.string', '<iframe width=\"604\" height=\"885\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" src=\"http://j.map.baidu.com/s/iciIFb\"></iframe>', 100, '公司地图代码', 1, 1, 1, 1,
        1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111012, 111111111111111110, 'website_config', '主题名称', 'name.websiteTheme.string', 'default', 100, '主题名称', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111111, 111111111111111111, 'system_config', '登录验证码开关', 'switch.loginCaptcha.boolean', 'true', 100, '登录过程需要输入验证码：true 需要输入', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111112, 111111111111111111, 'system_config', '演示模式编辑权限开关', 'switch.demoModeEdit.boolean', 'true', 100, '演示模式是否开启编辑功能：true 可以编辑', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111113, 111111111111111111, 'system_config', '一天错误登录多少次显示验证码', 'number.loginErrorShowCaptcha.integer', '0', 100, '如果是 0 次则表示永远显示', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111114, 111111111111111111, 'system_config', '正在维护公告开关', 'switch.underConstructionMode.boolean', 'false', 100, '开启后，所有请求都无法访问', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111115, 111111111111111111, 'system_config', '正在维护公告提示内容', 'content.underConstructionMode.string', '这是维护公告内容', 100, '正在维护公告提示内容', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111116, 111111111111111111, 'system_config', '是否开启留言功能', 'switch.guestBook.boolean', 'true', 100, '开启留言功能前台才会出现留言输入框', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (111111111111111117, 111111111111111111, 'system_config', '是否开启留言审批功能', 'switch.guestBookApproval.boolean', 'true', 100, '开启审批功能后，所有留言必须设置会显示才能再前台显示', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222221, 111111111111111112, 'image_watermark_config', '图片上传水印图片开关', 'switch.imageWatermark.boolean', 'true', 100, '图片上传是否开启水印图片', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222222, 111111111111111112, 'image_watermark_config', '水印图片存放路径', 'path.imageWatermark.string', '/watermark/taobao-watermark.png', 100, '水印图片必须和待处理图片在同一服务名下', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222223, 111111111111111112, 'image_watermark_config', '水印图片放置方位', 'align.imageWatermark.string', 'southeast', 100, '一共九个方位：northwest,north,northeast,west,center,east,southwest,south,southeast', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222224, 111111111111111112, 'image_watermark_config', '水印图片透明度', 'transparency.imageWatermark.integer', '100', 100, '文字水印透明度，取值区间[0,100]', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222225, 111111111111111112, 'image_watermark_config', '水印图片水平边距', 'margin.imageWatermarkMarginX.integer', '10', 100, '文字水印水平边距', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222226, 111111111111111112, 'image_watermark_config', '水印图片垂直边距', 'margin.imageWatermarkMarginY.integer', '10', 100, '文字水印垂直边距', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (222222222222222227, 111111111111111112, 'image_watermark_config', '水印图片根据主图的缩放比例', 'percent.imageWatermark.integer', '10', 100, '水印图片根据主图的缩放比例，取值区间[0,100]', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333331, 111111111111111113, 'text_watermark_config', '图片上传文字水印开关', 'switch.textWatermark.boolean', 'true', 100, '图片上传是否开启文字水印', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333332, 111111111111111113, 'text_watermark_config', '文字水印内容', 'content.textWatermark.string', '这是文字水印', 100, '文字水印内容', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333333, 111111111111111113, 'text_watermark_config', '文字水印字体大小', 'size.textWatermark.integer', '30', 100, '水印文字大小', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333334, 111111111111111113, 'text_watermark_config', '文字水印字体颜色', 'color.textWatermark.string', '000000', 100, '格式：RRGGBB', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333335, 111111111111111113, 'text_watermark_config', '文字水印放置方位', 'align.textWatermark.string', 'northwest', 100, '一共九个方位：northwest,north,northeast,west,center,east,southwest,south,southeast', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333336, 111111111111111113, 'text_watermark_config', '文字水印透明度', 'transparency.textWatermark.integer', '100', 100, '文字水印透明度，取值区间[0,100]', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333337, 111111111111111113, 'text_watermark_config', '文字水印水平边距', 'margin.textWatermarkMarginX.integer', '10', 100, '文字水印水平边距', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (333333333333333338, 111111111111111113, 'text_watermark_config', '文字水印垂直边距', 'margin.textWatermarkMarginY.integer', '10', 100, '文字水印垂直边距', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (444444444444444442, 111111111111111114, 'image_upload_config', '是否开启原图保护', 'switch.originalImageProtect.boolean', 'true', 100, '是否开启原图保护', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (444444444444444443, 111111111111111114, 'image_upload_config', '本地存储图片URL前缀', 'url.localFileUrlPrefix.string', 'http://sculptor.cdk8s.com:9091/sculptor-boot-backend', 100, '本地存储图片URL前缀', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (444444444444444444, 111111111111111114, 'image_upload_config', '本地存储图片是否走Nginx访问', 'switch.localFileUrlNginx.boolean', 'false', 100, '本地存储图片是否走Nginx访问', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (555555555555555551, 111111111111111115, 'upyun_config', '用户绑定域名', 'url.upyunFileUrlPrefix.string', 'http://img.gitnavi.com', 100, '用户绑定域名，不能以斜杠结尾', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (555555555555555552, 111111111111111115, 'upyun_config', '又拍云文件上传根目录', 'path.upyunRootPath.string', 'code-upload', 100, '又拍云文件上传根目录', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (555555555555555553, 111111111111111115, 'upyun_config', '又拍云开启原图保护，没开水印下的参数', 'param.upyunOriginalImageProtect.string', '/unsharp/false', 100, '又拍云开启原图保护，没开水印下的参数，这里设置不启用锐化', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (555555555555555554, 111111111111111115, 'upyun_config', '又拍云原图保护密钥', 'secret.upyunOriginalImageProtect.string', '123456', 100, '要访问原图必须带上密钥才能访问，格式：xx.png!123456', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (622477281297432577, 111111111111111118, 'material_library_config', '素材库图片目录展示规格', 'switch.originalImageProtect.boolean', 'true', 100, '是否开启原图保护', 2, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (661477281297432571, 111111111111111118, 'material_library_config', '素材库目录缩略图宽度', 'width.materialLibraryResize.integer', '50', 100, '素材库目录缩略图宽度', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (661477281297432572, 111111111111111118, 'material_library_config', '素材库目录缩略图高度', 'height.materialLibraryResize.integer', '50', 100, '素材库目录缩略图高度', 3, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (666666666666666661, 111111111111111116, 'aliyun_oss_config', '阿里云OSS用户绑定域名', 'url.aliyunFileUrlPrefix.string', 'https://cdk8s.oss-cn-shenzhen.aliyuncs.com', 100, '阿里云OSS用户绑定域名，不能以斜杠结尾', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (666666666666666662, 111111111111111116, 'aliyun_oss_config', '阿里云OSS开启原图保护，没开水印下的参数', 'param.aliyunOriginalImageProtect.string', '/bright,0', 100, '阿里云OSS开启原图保护，没开水印下的参数，这里设置亮度为原图', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (666666666666666663, 111111111111111116, 'aliyun_oss_config', '阿里云OSS文件上传根目录', 'path.aliyunOssRootPath.string', 'code-upload', 100, '阿里云OSS文件上传根目录', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (777777777777777771, 111111111111111117, 'header_footer_code_config', 'PC端页头代码', 'code.pcHeaderImportCode.string', '', 100, 'PC端页头代码', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (777777777777777772, 111111111111111117, 'header_footer_code_config', 'PC端页尾代码', 'code.pcFooterImportCode.string', '', 100, 'PC端页尾代码', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (777777777777777773, 111111111111111117, 'header_footer_code_config', 'H5端页头代码', 'code.h5HeaderImportCode.string', '', 100, 'H5端页头代码', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (777777777777777774, 111111111111111117, 'header_footer_code_config', 'H5端页尾代码', 'code.h5FooterImportCode.string', '', 100, 'H5端页尾代码', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (777777777777777775, 111111111111111117, 'header_footer_code_config', '小程序端页头代码', 'code.weixinHeaderImportCode.string', '', 100, '小程序端页头代码', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL),
       (777777777777777776, 111111111111111117, 'header_footer_code_config', '小程序端页尾代码', 'code.weixinFooterImportCode.string', '', 100, '小程序端页尾代码', 1, 1, 1, 1, 1574853820410, 111111111111111111, 1574853820410, 111111111111111111, NULL, NULL);
