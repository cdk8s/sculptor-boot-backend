package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.pojo.dto.param.syscommonopen.SysCommonOpenQueryCacheParam;
import com.cdk8s.sculptor.pojo.dto.response.enums.EnumInfoItemDTO;
import com.cdk8s.sculptor.util.EnumUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(value = "SysCommonOpen API")
@Slf4j
@RestController
@RequestMapping("/api/sysCommon/open")
public class SysCommonOpenController {

	private static Map<String, List<EnumInfoItemDTO>> enumList = EnumUtil.getEnumList();

	@Autowired
	private RedisTemplate redisTemplate;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/enumList", method = RequestMethod.GET)
	public ResponseEntity<?> enumList() {
		return R.success(enumList);
	}

	@RequestMapping(value = "/queryCache", method = RequestMethod.POST)
	public ResponseEntity<?> queryCache(@Valid @RequestBody SysCommonOpenQueryCacheParam param) {
		Object result = redisTemplate.opsForValue().get(param.getCacheKey());
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
