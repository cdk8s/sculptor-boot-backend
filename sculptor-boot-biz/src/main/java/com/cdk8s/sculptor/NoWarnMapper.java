/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：NoWarnMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor;

import org.apache.ibatis.annotations.Mapper;

/**
 * 为了让启动的时候不提示一个警告
 * No MyBatis mapper was found in '[com.cdk8s.demo]' package. Please check your configuration
 */
@Mapper
public interface NoWarnMapper {


}
