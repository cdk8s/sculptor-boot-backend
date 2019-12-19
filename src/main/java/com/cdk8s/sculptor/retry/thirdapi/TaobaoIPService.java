package com.cdk8s.sculptor.retry.thirdapi;


import cn.hutool.core.net.NetUtil;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.pojo.bo.thirdapi.TaobaoIpInfo;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.okhttp.OkHttpResponse;
import com.cdk8s.sculptor.util.okhttp.OkHttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class TaobaoIPService {

	@Autowired
	private OkHttpService okHttpService;

	//=====================================业务处理 retry 逻辑 start=====================================

	/**
	 * 淘宝这个接口有点问题，本地经常返回不了数据
	 */
	@Retryable(value = {Exception.class}, maxAttempts = 2, backoff = @Backoff(delay = 2000L, multiplier = 1))
	public TaobaoIpInfo getTaobaoIpInfo(String ip) {
		return requestTaobaoIpInfoApi(ip);
	}

	@Recover
	public TaobaoIpInfo getTaobaoIpInfoRecover(Exception e) {
		log.error("多次重试调用淘宝获取 IP 地址接口失败=<{}>", e.getMessage());

		TaobaoIpInfo taobaoIpInfo = new TaobaoIpInfo();
		TaobaoIpInfo.DataBean dataBean = new TaobaoIpInfo.DataBean();
		dataBean.setCountry("未知");
		taobaoIpInfo.setData(dataBean);
		return taobaoIpInfo;
	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private TaobaoIpInfo requestTaobaoIpInfoApi(String ip) {
		log.debug("调用淘宝获取 IP 地址接口开始");
		TaobaoIpInfo taobaoIpInfo = new TaobaoIpInfo();
		if (StringUtil.isBlank(ip)) {
			return taobaoIpInfo;
		}

		// 先判断是否是内网 IP
		boolean flag = NetUtil.isInnerIP(ip);
		if (flag) {
			TaobaoIpInfo.DataBean dataBean = new TaobaoIpInfo.DataBean();
			dataBean.setCountry("内网IP");
			taobaoIpInfo.setData(dataBean);
			return taobaoIpInfo;
		}


		String url = "http://ip.taobao.com/service/getIpInfo.php";

		// 加个请求头，模拟浏览器，不然基本无法访问
		Map<String, String> queries = new HashMap<>();
		queries.put("ip", ip);
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");

		OkHttpResponse okHttpResponse = okHttpService.get(url, queries, headers);
		if (null == okHttpResponse || okHttpResponse.getStatus() != HttpStatus.OK.value()) {
			throw new SystemException("调用淘宝获取 IP 地址接口没有数据需要重试");
		}

		String response = okHttpResponse.getResponse();
		if (StringUtil.containsIgnoreCase(response, "\"code\":1,")) {
			// code 为 1 表示调用失败
			throw new SystemException("调用淘宝获取 IP 地址接口没有数据需要重试");
		}

		try {
			taobaoIpInfo = JsonUtil.toObject(response, TaobaoIpInfo.class);
		} catch (Exception e) {
			log.error("调用淘宝获取 IP 地址接口 JSON 转换异常失败，JSON 信息=<{}>", response);
		}

		if (null == taobaoIpInfo.getData()) {
			throw new SystemException("调用淘宝获取 IP 地址接口没有数据需要重试");
		}

		log.debug("调用淘宝获取 IP 地址接口最终返回：<{}>", taobaoIpInfo.toString());
		return taobaoIpInfo;
	}

	//=====================================私有方法  end=====================================

}
