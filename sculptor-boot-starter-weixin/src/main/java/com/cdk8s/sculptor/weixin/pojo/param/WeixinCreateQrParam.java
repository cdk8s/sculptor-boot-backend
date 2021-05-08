/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinCreateQrParam.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.pojo.param;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class WeixinCreateQrParam implements Serializable {

	private static final long serialVersionUID = -1L;

	private int expire_seconds;
	private String action_name;
	private ActionInfoBean action_info;

	@NoArgsConstructor
	@Data
	public static class ActionInfoBean {
		private SceneBean scene;

		@NoArgsConstructor
		@Data
		public static class SceneBean {
			private String scene_str;
			private Integer scene_id;
		}
	}
}
