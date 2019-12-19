package com.cdk8s.sculptor.pojo.bo.service.sysemployee;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysEmployeeUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long userId;
	private String workCardId;
	private String jobPosition;
	private Long updateDate;
	private Long updateUserId;

}
