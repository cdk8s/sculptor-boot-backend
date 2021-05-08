/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TaobaoIpInfo.java
 * 项目名称：sculptor-boot-common-ip
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.ip.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 完整格式：
 * {"code":0,"data":{"ip":"144.202.105.250","country":"美国","area":"","region":"加利福尼亚","city":"圣何塞","county":"XX","isp":"XX","country_id":"US","area_id":"","region_id":"US_104","city_id":"US_1035","county_id":"xx","isp_id":"xx"}}
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TaobaoIpInfo {


	/**
	 * code : 0
	 * data : {"ip":"210.75.225.254","country":"中国","area":"华北","region":"北京市","city":"北京市","county":"","isp":"电信","country_id":"86","area_id":"100000","region_id":"110000","city_id":"110000","county_id":"-1","isp_id":"100017"}
	 */
	private int code;
	private DataBean data;

	@NoArgsConstructor
	@Setter
	@Getter
	@ToString
	public static class DataBean {

		/**
		 * ip=113.111.36.215,
		 * country=中国,
		 * area=,
		 * region=广东
		 * city=广州,
		 * county=XX,
		 * isp=电信,
		 * country_id=CN,
		 * area_id=,
		 * region_id=440000,
		 * city_id=440100,
		 * county_id=xx,
		 * isp_id=100017
		 */
		private String ip;
		private String country;
		private String area;
		private String region;
		private String city;
		private String county;
		private String isp;
		private String country_id;
		private String area_id;
		private String region_id;
		private String city_id;
		private String county_id;
		private String isp_id;
	}
}
