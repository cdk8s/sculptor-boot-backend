/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BeanFactoryAwarePrint.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;


/**
 * 实现了BeanFactoryAware接口的bean，可以直接通过beanFactory来访问spring的容器，当该bean被容器创建以后，会有一个相应的beanFactory的实例引用
 */
@Slf4j
@Configuration
public class BeanFactoryAwarePrint implements BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		log.debug("------zch------beanFactory <{}>", beanFactory);
	}
}
