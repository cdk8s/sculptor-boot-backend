package com.cdk8s.sculptor.controller;


import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.service.ValidateCodeService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
@RequestMapping("/validate/code")
public class ValidateCodeController {

	@Autowired
	private ValidateCodeService validateCodeService;

	/**
	 * 创建图形验证码
	 */
	@SneakyThrows
	@RequestMapping(value = "/imageCode", method = RequestMethod.GET)
	public void createCode(@RequestParam String deviceId, HttpServletResponse response) {
		if (StringUtil.isBlank(deviceId) || StringUtil.equalsIgnoreCase(deviceId, "undefined")) {
			throw new BusinessException("机器码不能为空");
		}
		CaptchaUtil.setHeader(response);
		GifCaptcha gifCaptcha = new GifCaptcha(100, 35, 4);
		gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
		validateCodeService.saveImageCode(deviceId, gifCaptcha.text().toLowerCase());
		gifCaptcha.out(response.getOutputStream());
	}

	/**
	 * 发送手机验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/smsCode/", method = RequestMethod.GET)
	public ResponseEntity<?> createCode(@RequestParam String mobilePhone) {
		if (StringUtil.isBlank(mobilePhone)) {
			throw new BusinessException("手机号不能为空");
		}
		validateCodeService.sendSmsCode(mobilePhone);
		return R.success();

	}
}

