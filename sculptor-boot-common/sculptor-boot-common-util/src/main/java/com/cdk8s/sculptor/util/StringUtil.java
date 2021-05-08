/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：StringUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承StringUtils类
 */
@Slf4j
public final class StringUtil {

	public static final String EMPTY = "";

	//=====================================Apache Common 包 start=====================================

	public static String format(String pattern, Object... arguments) {
		return MessageFormat.format(pattern, arguments);
	}

	public static boolean isNotBlank(final String str) {
		return StringUtils.isNotBlank(str);
	}

	public static boolean isBlank(final String str) {
		return StringUtils.isBlank(str);
	}

	public static boolean containsAny(final String str, final CharSequence... search) {
		return StringUtils.containsAny(str, search);
	}

	public static boolean containsIgnoreCase(final String str, final String search) {
		return StringUtils.containsIgnoreCase(str, search);
	}


	/**
	 * 根据下标截取字符串
	 */
	public static String substring(final String str, final int start, final int end) {
		return StringUtils.substring(str, start, end);
	}

	/**
	 * 截取第一个 search 匹配到之后的字符串，不包含 search 字符
	 * 比如：(abbccddef,c) 得到的结果就是：cddef
	 */
	public static String substringAfter(final String str, final String search) {
		return StringUtils.substringAfter(str, search);
	}

	/**
	 * 截取最后一个 search 匹配到之后的字符串，不包含 search 字符
	 * 比如：(abbccddef,c) 得到的结果就是：ddef
	 */
	public static String substringAfterLast(final String str, final String search) {
		return StringUtils.substringAfterLast(str, search);
	}

	/**
	 * 删除前后空格
	 */
	public static String trim(final String str) {
		return StringUtils.trim(str);
	}

	public static boolean equals(final String str1, final String str2) {
		return StringUtils.equals(str1, str2);
	}

	public static boolean notEquals(final String str1, final String str2) {
		return !StringUtils.equals(str1, str2);
	}

	public static boolean equalsIgnoreCase(final String str1, final String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static boolean notEqualsIgnoreCase(final String str1, final String str2) {
		return !StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static String lowerCase(final String str1) {
		return StringUtils.lowerCase(str1);
	}

	public static String upperCase(final String str1) {
		return StringUtils.upperCase(str1);
	}

	/**
	 * 检查字符串是否以某个字符串开头
	 */
	public static boolean startsWith(final String str, final String prefix) {
		return StringUtils.startsWith(str, prefix);
	}

	/**
	 * 检查字符串是否以某个字符串结尾
	 */
	public static boolean endsWith(final String str, final String suffix) {
		return StringUtils.endsWith(str, suffix);
	}

	/**
	 * 如果有多个位置被匹配到，也只有第一个匹配会被替换
	 */
	public static String replaceOnce(final String text, final String searchString, final String replacement) {
		return StringUtils.replaceOnce(text, searchString, replacement);
	}

	/**
	 * 如果有多个位置被匹配到，都会被替换
	 */
	public static String replace(final String text, final String searchString, final String replacement) {
		return StringUtils.replace(text, searchString, replacement);
	}

	/**
	 * 如果有多个位置被匹配到，则根据 max 次数替换。如果 max = 2，则只表示匹配两次替换即可
	 */
	public static String replace(final String text, final String searchString, final String replacement, final int max) {
		return StringUtils.replace(text, searchString, replacement, max);
	}

	public static String replaceIgnoreCase(final String text, final String searchString, final String replacement) {
		return StringUtils.replaceIgnoreCase(text, searchString, replacement);
	}

	/**
	 * 被删除的部分必须是在最尾巴，不然不会被删除，原值返回
	 */
	public static String removeEnd(final String text, final String searchString) {
		return StringUtils.removeEnd(text, searchString);
	}

	public static String removeStart(final String text, final String searchString) {
		return StringUtils.removeStart(text, searchString);
	}

	public static String remove(final String text, final String searchString) {
		return StringUtils.remove(text, searchString);
	}

	/**
	 * 比如：(aa#bb#, #) = aa,bb（一共 2 个元素）
	 */
	public static List<String> split(String str, String separator) {
		return CollectionUtil.toList(StringUtils.split(str, separator));
	}

	//=====================================Apache Common 包  end=====================================

	//=====================================其他包 start=====================================

	/**
	 * 大写开头的驼峰改为小写开头的驼峰
	 */
	public static String upperCamelToLowerCamel(String str) {
		CaseFormat fromFormat = CaseFormat.UPPER_CAMEL;//
		CaseFormat toFormat = CaseFormat.LOWER_CAMEL;
		return fromFormat.to(toFormat, str);
	}


	/**
	 * 去除 html 标签
	 */
	public static String removeHtml(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
	}

	/**
	 * 验证value是否包含Xss 包含返回false不包含返回true
	 */
	public static boolean checkXss(String value) {

		if (isBlank(value)) {
			return true;
		}

		Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain script");
			return false;
		}

		// Remove any lonesome </script> tag
		scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain script");
			return false;
		}

		// Remove any lonesome <script ...> tag
		scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain script");
			return false;
		}

		scriptPattern = Pattern.compile("<iframe(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain iframe");
			return false;
		}

		scriptPattern = Pattern.compile("<form(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain form");
			return false;
		}

		scriptPattern = Pattern.compile("<base(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain base");
			return false;
		}

		// Avoid eval(...) e­xpressions
		scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain eval");
			return false;
		}

		// Avoid e­xpression(...) e­xpressions
		scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain e­xpression");
			return false;
		}

		// Avoid javascript:... e­xpressions
		scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain javascript");
			return false;
		}

		// Avoid vbscript:... e­xpressions
		scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain vbscript");
			return false;
		}

		// Avoid onload= e­xpressions
		scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			log.error("------zch------xss contain onload");
			return false;
		}

		// 单引号
		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			if (StringUtil.containsIgnoreCase(value, ".js")) {
				log.error("------zch------xss contain src and .js");
				// img 标签必然会有 src，所以这里只能简单判断是否含有 .js 文件
				return false;
			}
			// return false;
		}

		// 双引号
		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(value).find()) {
			if (StringUtil.containsIgnoreCase(value, ".js")) {
				log.error("------zch------xss contain src and .js");
				// img 标签必然会有 src，所以这里只能简单判断是否含有 .js 文件
				return false;
			}
			// return false;
		}

		value = value.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		value = value.replaceAll("\\+", "%2B");
		String valueByURLDecoder = null;
		try {
			valueByURLDecoder = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return true;
		}

		scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain script");
			return false;
		}

		scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain script");
			return false;
		}

		scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain eval");
			return false;
		}

		scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain e­xpression");
			return false;
		}

		scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain javascript");
			return false;
		}

		scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain vbscript");
			return false;
		}

		scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			log.error("------zch------xss contain onload");
			return false;
		}

		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			if (StringUtil.containsIgnoreCase(value, ".js")) {
				log.error("------zch------xss contain src and .js");
				// img 标签必然会有 src，所以这里只能简单判断是否含有 .js 文件
				return false;
			}
			// return false;
		}

		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		if (scriptPattern.matcher(valueByURLDecoder).find()) {
			if (StringUtil.containsIgnoreCase(value, ".js")) {
				log.error("------zch------xss contain src and .js");
				// img 标签必然会有 src，所以这里只能简单判断是否含有 .js 文件
				return false;
			}
			// return false;
		}

		return true;
	}


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}



