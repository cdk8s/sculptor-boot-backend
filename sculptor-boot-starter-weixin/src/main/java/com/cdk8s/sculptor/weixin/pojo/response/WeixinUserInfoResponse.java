/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：WeixinUserInfoResponse.java
 * 项目名称：sculptor-boot-starter-weixin
 * 项目描述：sculptor-boot-starter-weixin
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.weixin.pojo.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class WeixinUserInfoResponse implements Serializable {

	private static final long serialVersionUID = -1L;

	private int subscribe;
	private String openid;
	private String nickname;
	private int sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private int subscribe_time;

	private String unionid;
	private String remark;
	private int groupid;

	private String subscribe_scene;

	private int qr_scene;

	private String qr_scene_str;

	private List<Integer> tagid_list;



}
