/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CodecUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util.code;

import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 编码与解码操作工具类
 */
@Slf4j
public final class CodecUtil {

	public static final String SHA1 = "sha1Hex";
	public static final String SHA256 = "sha256Hex";
	public static final String SHA384 = "sha384Hex";
	public static final String SHA512 = "sha512Hex";

	public static String getSignature(SortedMap<String, Object> param) {
		StringBuffer stringBuffer = new StringBuffer();
		Set entrySet = param.entrySet();  //所有参与传参的参数按照 ASCII 排序（升序）
		Iterator it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			//空值不传递，不参与签名组串
			if (null != v && !"".equals(v)) {
				stringBuffer.append(k + "=" + v + "&");
			}
		}
		String result = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
		return result;
	}


	public static String SHA(String shaType, String str) {
		String strSha = null;
		if (shaType.equals(SHA1)) {
			// 官网不推荐使用
			strSha = DigestUtils.shaHex(str);
		}
		if (shaType.equals(SHA256)) {
			strSha = DigestUtils.sha256Hex(str);
		}
		if (shaType.equals(SHA384)) {
			strSha = DigestUtils.sha384Hex(str);
		}
		if (shaType.equals(SHA512)) {
			strSha = DigestUtils.sha512Hex(str);
		}
		return strSha;
	}

	// 编码：转成url可传输的  可读→可传输
	public static String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			log.error("encode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	// 解码   可传输→可读
	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "UTF-8");
		} catch (Exception e) {
			log.error("decode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 解码
	 */
	public static String base64DecodeBySafe(final String string) {
		return new String(Base64.decodeBase64(string));
	}

	/**
	 * 编码（不会有等于号）
	 */
	public static String base64EncodeBySafe(final String string) {
		return Base64.encodeBase64URLSafeString(string.getBytes());

	}

	/**
	 * 把base64字符串解码为源字符串数组(通过Google guava)
	 *
	 * @return
	 */
	public static byte[] base64StringToSourceByteByGuava(final String base64String) {
		if (StringUtil.isNotBlank(base64String)) {
			try {
				return BaseEncoding.base64().decode(base64String);
			} catch (IllegalArgumentException e) {
				log.error("--------------------------------把base64字符串解码为源字符数组出错，数据格式错误");
				ExceptionUtil.printStackTraceAsString(e);
			}
		}
		return null;
	}

	/**
	 * 把字节编码为base64进制字符串(通过Google guava)
	 *
	 * @return
	 */
	public static String byteToBase64StringByGuava(final byte[] bytes) {
		if (null != bytes) {
			return BaseEncoding.base64().encode(bytes);
		}
		return null;
	}

	public static String base64EncodeByAliyunWatermark(final String string) {
		if (null != string) {
			String result = byteToBase64StringByGuava(string.getBytes());
			// 阿里云规定部分字符要替换：https://help.aliyun.com/document_detail/44957.html
			// 将结果中的加号（+）替换成连接号（-）
			// 将结果中的正斜线（/）替换成下划线（_）
			// 将结果中尾部的等号（=）全部保留
			result = StringUtil.replace(result, "+", "-");
			result = StringUtil.replace(result, "/", "_");
			return result;
		}
		return null;
	}

}
