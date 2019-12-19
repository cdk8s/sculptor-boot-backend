package com.cdk8s.sculptor.pojo.bo.util;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IpRegionBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private String ipAddress;
	private String ipRegion;
	private String ipRegionCountry;
	private String ipRegionProvince;
	private String ipRegionCity;
	private String ipRegionIsp;

}
