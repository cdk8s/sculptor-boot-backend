/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EnumUtil.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums.util;


import cn.hutool.core.util.ClassUtil;
import com.cdk8s.sculptor.enums.BasicEnum;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.*;

public class EnumUtil {

	@SneakyThrows
	public static Map<String, List<EnumInfoItemDTO>> getEnumList() {
		Set<Class<?>> classSet = ClassUtil.scanPackage("com.cdk8s.sculptor.enums");
		Map<String, List<EnumInfoItemDTO>> mapRoot = new HashMap<>();
		for (Class<?> clazz : classSet) {
			Method getAllEnumMethod = ClassUtil.getPublicMethod(clazz, "getAllEnum", null);
			if (null != getAllEnumMethod) {
				List<EnumInfoItemDTO> dtoList = new ArrayList<>();
				Map<Integer, String> map = (Map<Integer, String>) getAllEnumMethod.invoke(null, null);
				for (Map.Entry<Integer, String> entry : map.entrySet()) {
					EnumInfoItemDTO itemDTO = new EnumInfoItemDTO();
					itemDTO.setValue(entry.getKey());
					itemDTO.setLabel(entry.getValue());
					dtoList.add(itemDTO);
				}

				mapRoot.put(StringUtil.upperCamelToLowerCamel(clazz.getSimpleName()), dtoList);
			}
		}
		return mapRoot;
	}

	/**
	 * 根据 code 获取枚举
	 *
	 * @param enumClass
	 * @param code
	 * @return
	 */
	public static <E extends Enum<?> & BasicEnum> E codeOf(Class<E> enumClass, int code) {
		E[] enumConstants = enumClass.getEnumConstants();
		for (E e : enumConstants) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 根据 description 获取枚举
	 *
	 * @param enumClass
	 * @param description
	 * @param <E>
	 * @return
	 */
	public static <E extends Enum<?> & BasicEnum> E descriptionOf(Class<E> enumClass, String description) {
		E[] enumConstants = enumClass.getEnumConstants();
		for (E e : enumConstants) {
			if (e.getDescription().equals(description)) {
				return e;
			}
		}
		return null;
	}


}
