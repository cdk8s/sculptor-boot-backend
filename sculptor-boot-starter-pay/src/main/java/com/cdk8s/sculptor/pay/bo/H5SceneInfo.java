/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：H5SceneInfo.java
 * 项目名称：sculptor-boot-starter-pay
 * 项目描述：sculptor-boot-starter-pay
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pay.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class H5SceneInfo {
	private H5 h5_info;

	@Getter
	@Setter
	@ToString(callSuper = true)
	public static class H5 {
		private String type;
		private String app_name;
		private String bundle_id;
		private String package_name;
		private String wap_url;
		private String wap_name;
	}
}
