package com.cdk8s.sculptor.pojo.bo.service.sysdict;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysDictUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String dictName;
	private String dictCode;
	private Integer dictValueTypeEnum;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long updateDate;
	private Long updateUserId;

}
