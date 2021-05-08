/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CollectionUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 集合工具类
 */
public final class CollectionUtil {

	public static <T> List<T> emptyList() {
		return Collections.emptyList();
	}

	public static <T> Set<T> emptySet() {
		return Collections.emptySet();
	}

	public static Collection emptyCollection() {
		return CollectionUtils.EMPTY_COLLECTION;
	}

	public static <K, V> Map<K, V> emptyMap() {
		return Collections.emptyMap();
	}

	//=====================================Apache Common 包 start=====================================


	public static boolean isNotEmpty(final Collection coll) {
		return CollectionUtils.isNotEmpty(coll);
	}

	public static boolean isEmpty(final Collection coll) {
		return CollectionUtils.isEmpty(coll);
	}


	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}

	public static <T> T[] addAll(final T[] array1, final T... array2) {
		return ArrayUtils.addAll(array1,array2);
	}

	/**
	 * 把字符串转换成 list
	 */
	public static List<String> stringToList(String str, String separator) {
		String[] split = StringUtils.split(str, separator);
		return toList(split);
	}

	//=====================================Apache Common 包  end=====================================

	//=====================================Guava 包  end=====================================

	/**
	 * 把 map 用分隔符组装成一个字符串
	 * 假如：
	 * map.put("John", 1000);
	 * map.put("Jane", 1500);
	 * 当 keyValueSeparator 为 =
	 * 当 elementSeparator 为 ,
	 * 结果：John=1000,Jane=1500
	 */
	public static String mapToString(Map map, String keyValueSeparator, String elementSeparator) {
		return Joiner.on(elementSeparator).withKeyValueSeparator(keyValueSeparator).join(map);
	}


	//=====================================Guava 包  end=====================================

	//=====================================其他包 start=====================================

	public static <T> List<T> setToList(Set<T> set) {
		return new ArrayList<>(set);
	}

	public static <T> Set<T> listToSet(List<T> list) {
		return new HashSet<>(list);
	}

	/**
	 * 并集
	 */
	public static <T> List<T> union(List<T> list1, List<T> list2) {
		return new ArrayList<>(CollectionUtils.union(list1, list2));
	}

	/**
	 * 交集
	 */
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		return new ArrayList<>(CollectionUtils.intersection(list1, list2));
	}


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	/**
	 * 数组转换成 List
	 */
	public static <T> List<T> toList(T[] arrays) {
		List<T> list = new ArrayList<>();
		CollectionUtils.addAll(list, arrays);
		return list;
	}

	/**
	 * 新建一个空数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> componentType, int newSize) {
		return (T[]) Array.newInstance(componentType, newSize);
	}

	//=====================================私有方法  end=====================================

}



