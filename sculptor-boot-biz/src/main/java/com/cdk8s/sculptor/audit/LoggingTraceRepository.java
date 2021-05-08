/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LoggingTraceRepository.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.audit;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LoggingTraceRepository implements HttpTraceRepository {

	private final HttpTraceRepository httpTraceRepository = new InMemoryHttpTraceRepository();

	//=====================================业务处理 start=====================================

	@Override
	public List<HttpTrace> findAll() {
		return httpTraceRepository.findAll();
	}

	@Override
	public void add(HttpTrace trace) {
		if (checkUri(trace)) {
			return;
		}
		printTimeTakenResult(trace);
		this.httpTraceRepository.add(trace);
	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private Boolean checkUri(HttpTrace trace) {
		HttpTrace.Request request = trace.getRequest();
		String requestURI = StringUtil.lowerCase(request.getUri().toString());

		List<String> ignoreSuffix = ignoreSuffix();

		CharSequence[] charSequences = ignoreSuffix.toArray(new CharSequence[0]);
		return StringUtil.containsAny(requestURI, charSequences);
	}

	private void printTimeTakenResult(HttpTrace trace) {
		Long timeTaken = trace.getTimeTaken();

		HttpTrace.Request request = trace.getRequest();
		String requestMethod = request.getMethod();
		Map<String, List<String>> requestHeaders = request.getHeaders();
		List<String> cookieList = requestHeaders.get("cookie");

		List<String> refererList = requestHeaders.get("referer");
		URI requestUri = request.getUri();


		HttpTrace.Response response = trace.getResponse();
		Map<String, List<String>> responseHeaders = response.getHeaders();
		int responseStatus = response.getStatus();


		if (log.isDebugEnabled()) {
			// 用 info 控制台输出的比较清晰
			if (responseStatus != 200) {
				log.error("------zch------responseStatus 状态码 = <{}>", responseStatus);
			}
			log.info("----------------------HttpTrace requestUri={}", requestUri);
			log.info("----------------------HttpTrace requestMethod={}, responseStatus={}", requestMethod, responseStatus);
			log.info("----------------------HttpTrace cookieList={}", cookieList);
			log.info("----------------------HttpTrace refererList={}", refererList);
			log.info("----------------------HttpTrace responseHeaders={}", responseHeaders.toString());
		}


		if (timeTaken > GlobalConstant.SERIOUS_PERFORMANCE_PROBLEMS_TIME_THRESHOLD) {
			log.error("----------------------HttpTrace requestUri={}----------------------", requestUri);
			log.error("----------------------HttpTrace 严重注意：该方法可能存在严重性能问题={}毫秒----------------------", timeTaken);
		} else if (timeTaken > GlobalConstant.GENERAL_PERFORMANCE_PROBLEMS_TIME_THRESHOLD) {
			log.warn("----------------------HttpTrace requestUri={}----------------------", requestUri);
			log.warn("----------------------HttpTrace 注意：该方法可能存在一般性能问题={}毫秒----------------------", timeTaken);
		} else if (timeTaken > GlobalConstant.NEED_OPTIMIZE_TIME_THRESHOLD) {
			log.info("----------------------HttpTrace requestUri={}----------------------", requestUri);
			log.info("----------------------HttpTrace 提示：检查该方法是否有优化的空间={}毫秒----------------------", timeTaken);
		}
	}

	private List<String> ignoreSuffix() {
		List<String> ignoreSuffix = new ArrayList<>();
		ignoreSuffix.add(".mp3");
		ignoreSuffix.add(".mp4");
		ignoreSuffix.add(".qt");
		ignoreSuffix.add(".flv");
		ignoreSuffix.add(".wmv");
		ignoreSuffix.add(".ogg");
		ignoreSuffix.add(".wav");
		ignoreSuffix.add(".avi");
		ignoreSuffix.add(".mov");
		ignoreSuffix.add(".mpeg");
		ignoreSuffix.add(".webm");
		ignoreSuffix.add(".mkv");
		ignoreSuffix.add(".rar");
		ignoreSuffix.add(".zip");
		ignoreSuffix.add(".7z");
		ignoreSuffix.add(".exe");
		ignoreSuffix.add(".msi");
		ignoreSuffix.add(".map");
		ignoreSuffix.add(".html");
		ignoreSuffix.add(".htm");
		ignoreSuffix.add(".xml");
		ignoreSuffix.add(".pdf");
		ignoreSuffix.add(".doc");
		ignoreSuffix.add(".docx");
		ignoreSuffix.add(".xls");
		ignoreSuffix.add(".xlsx");
		ignoreSuffix.add(".ppt");
		ignoreSuffix.add(".pptx");
		ignoreSuffix.add(".rtf");
		ignoreSuffix.add(".js");
		ignoreSuffix.add(".css");
		ignoreSuffix.add(".less");
		ignoreSuffix.add(".scss");
		ignoreSuffix.add(".sass");
		ignoreSuffix.add(".md");
		ignoreSuffix.add(".jpg");
		ignoreSuffix.add(".jpeg");
		ignoreSuffix.add(".gif");
		ignoreSuffix.add(".tif");
		ignoreSuffix.add(".tiff");
		ignoreSuffix.add(".png");
		ignoreSuffix.add(".bmp");
		ignoreSuffix.add(".swf");
		ignoreSuffix.add(".ico");
		ignoreSuffix.add(".woff");
		ignoreSuffix.add(".woff2");
		ignoreSuffix.add(".ttf");
		ignoreSuffix.add(".eot");
		ignoreSuffix.add(".txt");
		ignoreSuffix.add(".svg");
		ignoreSuffix.add(".svgz");
		ignoreSuffix.add(".psd");
		ignoreSuffix.add(".ai");
		ignoreSuffix.add(".eps");
		ignoreSuffix.add(".ps");
		return ignoreSuffix;
	}

	//=====================================私有方法  end=====================================


}
