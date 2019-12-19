package com.cdk8s.sculptor.pojo.bo.service.sysparam;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysParamUpdateServiceBO implements Serializable {

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
	private Long updateDate;
	private Long updateUserId;

}
