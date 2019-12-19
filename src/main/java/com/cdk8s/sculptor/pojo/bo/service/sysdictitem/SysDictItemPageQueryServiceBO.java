package com.cdk8s.sculptor.pojo.bo.service.sysdictitem;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysDictItemPageQueryServiceBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long dictId;
	private String dictCode;
	private String itemName;
	private String itemCode;
	private String itemValue;
	private Integer dictValueTypeEnum;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
