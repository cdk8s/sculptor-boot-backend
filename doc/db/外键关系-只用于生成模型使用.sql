
alter table `sys_login_log` add constraint FK_ID1 foreign key (user_id) REFERENCES `sys_user` (id);
alter table `sys_event_log` add constraint FK_ID2 foreign key (user_id) REFERENCES `sys_user` (id);
alter table `sys_employee` add constraint FK_ID3 foreign key (user_id) REFERENCES `sys_user` (id);
alter table `sys_dict_item` add constraint FK_ID4 foreign key (dict_id) REFERENCES `sys_dict` (id);
alter table `sys_param` add constraint FK_ID5 foreign key (type_id) REFERENCES `sys_param_type` (id);

alter table `rel_role_user` add constraint FK_ID6 foreign key (role_id) REFERENCES `sys_role` (id);
alter table `rel_role_user` add constraint FK_ID7 foreign key (user_id) REFERENCES `sys_user` (id);

alter table `rel_dept_user` add constraint FK_ID8 foreign key (dept_id) REFERENCES `sys_dept` (id);
alter table `rel_dept_user` add constraint FK_ID9 foreign key (user_id) REFERENCES `sys_user` (id);

alter table `rel_permission_role` add constraint FK_ID10 foreign key (permission_id) REFERENCES `sys_permission` (id);
alter table `rel_permission_role` add constraint FK_ID11 foreign key (role_id) REFERENCES `sys_role` (id);


