/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：IpService.java
 * 项目名称：sculptor-boot-common-ip
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.ip.service;

import cn.hutool.core.net.NetUtil;

import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
import com.cdk8s.sculptor.util.ip.pojo.IpRegionBO;
import com.cdk8s.sculptor.util.ip.pojo.TaobaoIpInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class IpService {

	@Autowired
	private TaobaoIPService taobaoIPService;

	//=====================================业务处理 start=====================================

	public IpRegionBO getIpRegionBO(String ipAddress) {
		IpRegionBO ipRegionBO = new IpRegionBO();
		ipRegionBO.setIpAddress(ipAddress);

		// 先判断是否是内网 IP
		boolean flag = NetUtil.isInnerIP(ipAddress);
		if (flag) {
			ipRegionBO.setIpRegion("内网IP");
			return ipRegionBO;
		}

		// 默认采用 ip2region 方式
		String regionInfoByIp = IPUtil.getRegionInfoByIp(ipAddress);
		if (StringUtil.isNotBlank(regionInfoByIp)) {
			ipRegionBO.setIpRegion(regionInfoByIp);
			List<String> split = StringUtil.split(ipAddress, "-");
			if (split.size() >= 4) {
				ipRegionBO.setIpRegionCountry(split.get(0));
				ipRegionBO.setIpRegionProvince(split.get(1));
				ipRegionBO.setIpRegionCity(split.get(2));
				ipRegionBO.setIpRegionIsp(split.get(3));
			}
			if (split.size() == 3) {
				ipRegionBO.setIpRegionCountry(split.get(0));
				ipRegionBO.setIpRegionProvince(split.get(1));
				ipRegionBO.setIpRegionCity(split.get(2));
			}
			if (split.size() == 2) {
				ipRegionBO.setIpRegionCountry(split.get(0));
				ipRegionBO.setIpRegionProvince(split.get(1));
			}
			if (split.size() == 1) {
				ipRegionBO.setIpRegionCountry(split.get(0));
			}
			return ipRegionBO;
		}

		// 使用淘宝 IP
		TaobaoIpInfo taobaoIpInfo = taobaoIPService.getTaobaoIpInfo(ipAddress);
		if (null == taobaoIpInfo || null == taobaoIpInfo.getData()) {
			// 淘宝 IP 也为空那就只能不处理
			return null;
		}

		TaobaoIpInfo.DataBean dataBean = taobaoIpInfo.getData();
		String country = dataBean.getCountry();
		String region = dataBean.getRegion();
		String city = dataBean.getCity();
		String isp = dataBean.getIsp();

		StringBuilder stringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(country)) {
			stringBuilder.append(country);
			stringBuilder.append("-");
			ipRegionBO.setIpRegionCountry(country);
		}
		if (StringUtil.isNotBlank(region)) {
			stringBuilder.append(region);
			stringBuilder.append("-");
			ipRegionBO.setIpRegionProvince(region);
		}
		if (StringUtil.isNotBlank(city)) {
			stringBuilder.append(city);
			stringBuilder.append("-");
			ipRegionBO.setIpRegionCity(city);
		}
		if (StringUtil.isNotBlank(isp)) {
			stringBuilder.append(isp);
			stringBuilder.append("-");
			ipRegionBO.setIpRegionIsp(isp);
		}
		ipRegionBO.setIpRegion(StringUtil.removeEnd(stringBuilder.toString(), "-"));
		return ipRegionBO;

	}


	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================

}
