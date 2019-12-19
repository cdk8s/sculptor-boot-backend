package com.cdk8s.sculptor.pojo.bo.service.sysparam;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysParamPageQueryServiceBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long typeId;
	private String paramName;
	private String paramCode;
	private String paramValue;
	private Integer ranking;
	private String description;
	private Integer paramValueTypeEnum;
	private Integer stateEnum;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
