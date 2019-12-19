package com.cdk8s.sculptor.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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

	//=====================================Apache Common 包  end=====================================

	//=====================================Guava 包  end=====================================

	public static <T> ArrayList<T> newList(T... elements) {
		return Lists.newArrayList(elements);
	}

	public static <T> HashSet<T> newSet(T... elements) {
		return Sets.newHashSet(elements);
	}
	//=====================================Guava 包  end=====================================

	//=====================================其他包 start=====================================

	public static <T> List<T> setToList(Set<T> set) {
		return new ArrayList<>(set);
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
	 * List 转换成数组
	 */
	public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
		final T[] array = newArray(componentType, collection.size());
		return collection.toArray(array);
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



