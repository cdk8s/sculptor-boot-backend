package com.cdk8s.sculptor.pojo.bo.service.sysemployee;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysEmployeeCreateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long userId;
	private String workCardId;
	private String jobPosition;
	private Integer deleteEnum;
	private Long createDate;
	private Long createUserId;
	private Long updateDate;
	private Long updateUserId;
	private Long deleteDate;
	private Long deleteUserId;

}
