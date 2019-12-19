package com.cdk8s.sculptor.pojo.bo.service.sysdict;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysDictPageQueryServiceBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String dictName;
	private String dictCode;
	private Integer dictValueTypeEnum;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
