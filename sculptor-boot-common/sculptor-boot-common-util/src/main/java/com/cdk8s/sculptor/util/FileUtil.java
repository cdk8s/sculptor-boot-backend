/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：FileUtil.java
 * 项目名称：sculptor-boot-common-util
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.util;

import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件操作工具类
 */
@Slf4j
public final class FileUtil {

	public static String getNewFileNameByUUID() {
		return GenerateIdUtil.getUUID();
	}

	@SneakyThrows
	public static InputStream getStringToInputStream(String str) {
		return IOUtils.toInputStream(str, StandardCharsets.UTF_8.name());
	}

	@SneakyThrows
	public static void writeStringToFile(File file, String content) {
		FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
	}

	public static void checkImageAndVideoFormat(MultipartFile file, Long maxSize) {
		String originalFilename = file.getOriginalFilename();
		if (StringUtil.isBlank(originalFilename)) {
			throw new RuntimeException("文件格式不正确");
		}

		// 校验文件格式
		boolean flag = false;
		if (FileUtil.checkFileTypeByImage(originalFilename)) {
			flag = true;
		}
		if (FileUtil.checkFileTypeByVideo(originalFilename)) {
			flag = true;
		}
		if (!flag) {
			throw new RuntimeException("只能选择图片、视频类型文件");
		}

		if (null == maxSize) {
			maxSize = 20971520L;
		}

		// 校验文件大小，不允许超过 10M，getSize 单位 bytes
		if (file.getSize() > maxSize) {
			String error = "文件过大！请上传小于 " + (maxSize / 1024 / 1024) + "M 的文件";
			throw new RuntimeException(error);
		}
	}

	public static String handleFileName(String fileName) {
		fileName = StringUtil.replace(fileName, " ", "_");
		fileName = StringUtil.replace(fileName, "/", "_");
		fileName = StringUtil.replace(fileName, "#", "_");
		fileName = StringUtil.replace(fileName, "%", "_");
		fileName = StringUtil.replace(fileName, "$", "_");
		fileName = StringUtil.replace(fileName, "&", "_");
		return fileName;
	}

	public static boolean checkFileTypeByImage(String fileName) {
		List<String> ignoreSuffix = new ArrayList<>();
		ignoreSuffix.add(".jpg");
		ignoreSuffix.add(".jpeg");
		ignoreSuffix.add(".gif");
		ignoreSuffix.add(".png");
		ignoreSuffix.add(".bmp");
		ignoreSuffix.add(".ico");
		ignoreSuffix.add(".svg");

		CharSequence[] charSequences = ignoreSuffix.toArray(new CharSequence[ignoreSuffix.size()]);

		return StringUtils.endsWithAny(fileName.toLowerCase(), charSequences);
	}

	public static boolean checkFileTypeByVideo(String fileName) {
		List<String> ignoreSuffix = new ArrayList<>();

		ignoreSuffix.add(".mp4");
		ignoreSuffix.add(".mp3");
		ignoreSuffix.add(".webm");
		ignoreSuffix.add(".flv");
		ignoreSuffix.add(".mkv");
		ignoreSuffix.add(".swf");
		ignoreSuffix.add(".avi");
		ignoreSuffix.add(".rmvb");
		ignoreSuffix.add(".mov");
		ignoreSuffix.add(".wmv");

		CharSequence[] charSequences = ignoreSuffix.toArray(new CharSequence[ignoreSuffix.size()]);

		return StringUtils.endsWithAny(fileName.toLowerCase(), charSequences);
	}

	@SneakyThrows
	public static File multipartToFile(MultipartFile multipart, String fileName) {
		File convFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
		FileUtils.writeByteArrayToFile(convFile, multipart.getBytes());
		return convFile;
	}

	/**
	 * 获取文件格式后缀（自动去掉文件路径）
	 * 没有带点，比如：png
	 */
	public static String getFileExtension(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

	/**
	 * 创建文件
	 */
	public static File createFile(String filePath) {
		File file;
		try {
			file = new File(filePath);
			File parentDir = file.getParentFile();
			if (!parentDir.exists()) {
				FileUtils.forceMkdir(parentDir);
			}
		} catch (Exception e) {
			log.error("create file failure", e);
			throw new RuntimeException(e);
		}
		return file;
	}

	/**
	 * 通过网络 url 获取文件流（必须包含 http 或 https 开头）
	 */
	public static InputStream getInputStreamByFileHttpUrl(String fileHttpUrl) {
		InputStream inputStream = null;
		try {
			String strUrl = fileHttpUrl.trim();
			URL url = new URL(strUrl);
			//打开请求连接
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 取得输入流，并使用Reader读取
			inputStream = httpURLConnection.getInputStream();
			return inputStream;
		} catch (Exception e) {
			log.error("------zch------获取网络图片出现异常，图片 URL：<{}>", fileHttpUrl);
			ExceptionUtil.printStackTraceAsString(e);
		}
		return inputStream;
	}

	public static byte[] inputStreamToByteArray(final InputStream inputStream) throws IOException {
		// return ByteStreams.toByteArray(inputStream);// guava 方案
		return IOUtils.toByteArray(inputStream);// commons-io 方案
	}

	public static void deleteFile(String fileFullPath) {
		cn.hutool.core.io.FileUtil.del(fileFullPath);
	}


}
