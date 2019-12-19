package com.cdk8s.sculptor;

import org.apache.ibatis.annotations.Mapper;

/**
 * 为了让启动的时候不提示一个警告
 * No MyBatis mapper was found in '[com.cdk8s.demo]' package. Please check your configuration
 */
@Mapper
public interface NoWarnMapper {


}
