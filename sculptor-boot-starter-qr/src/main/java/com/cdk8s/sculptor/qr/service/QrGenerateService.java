/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QrGenerateService.java
 * 项目名称：sculptor-boot-starter-qr
 * 项目描述：sculptor-boot-starter-qr
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.qr.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;

import javax.servlet.http.HttpServletResponse;


@Slf4j
public class QrGenerateService {

	@SneakyThrows
	public void generateQrToOutputStream(String content, HttpServletResponse response) {
		new SimpleQrcodeGenerator().generate(content).toStream(response.getOutputStream());
	}

	@SneakyThrows
	public void generateQrToFile(String content, String fileFullPath) {
		new SimpleQrcodeGenerator().generate(content).toFile(fileFullPath);
	}
}
