package com.cdk8s.sculptor.pojo.bo.mapper.sysuser;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysUserPageQueryMapperBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String username;
	private String realName;
	private String userPassword;
	private String passwordSalt;
	private String userEmail;
	private String telephone;
	private String mobilePhone;
	private Integer genderEnum;
	private Integer registerTypeEnum;
	private Integer registerOriginEnum;
	private Integer stateEnum;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();

	// ==============非 entity 属性 start==============

	private Long deptId;
	private List<Long> deptIdList;

	private Long roleId;

	// ==============非 entity 属性 end==============
}
