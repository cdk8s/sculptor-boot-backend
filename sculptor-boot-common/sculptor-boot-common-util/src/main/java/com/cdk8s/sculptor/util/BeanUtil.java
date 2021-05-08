/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BeanUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Slf4j
public final class BeanUtil {

	/**
	 * 忽略 source 的 null 值，只会拷贝那些有值的属性
	 */
	public static <T, D> void copyProperties(T sourceObject, D targetObject) {
		cn.hutool.core.bean.BeanUtil.copyProperties(sourceObject, targetObject, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
	}


	/**
	 * 拷贝指定源列表 到 指定目标bean类型，并返回目标bean列表
	 * List<UserDto> userDtoList = BeanUtil.copyPropertiesForList(userDoList, UserDto::new);
	 */
	public static <T, D> List<T> copyPropertiesForList(List<D> sourceList, Supplier<T> targetSupplier) {
		if (CollectionUtil.isEmpty(sourceList) || Objects.isNull(targetSupplier)) {
			return null;
		}
		return sourceList.stream().filter(Objects::nonNull).map(d ->
				BeanUtil.copyForBean(d, targetSupplier))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 * 拷贝指定bean 到目标bean
	 * 用法：
	 * UserDto userDto = BeanUtil.copyForBean(useDo, UserDto::new);
	 */
	public static <T, D> T copyForBean(D source, Supplier<T> targetSupplier) {
		if (Objects.isNull(targetSupplier) || Objects.isNull(source)) {
			return null;
		}
		T target;
		target = targetSupplier.get();
		if (Objects.nonNull(target)) {
			// source 的 null 值，也会拷贝
			BeanUtils.copyProperties(source, target);
		}
		return target;
	}


}
