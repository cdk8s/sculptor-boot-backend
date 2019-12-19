package com.cdk8s.sculptor.service.oauth;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.bo.cache.oauth.OauthClientToRedisBO;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OauthClientService {

	@Autowired
	private StringRedisService<String, OauthClientToRedisBO> clientRedisService;

	//=====================================业务处理 start=====================================

	public OauthClientToRedisBO findByClientId(String clientId) {
		String clientIdRedisKey = GlobalConstant.REDIS_CLIENT_ID_KEY_PREFIX + clientId;
		OauthClientToRedisBO result = clientRedisService.get(clientIdRedisKey);
		if (null == result) {
			throw new OauthApiException("client_id 不存在");
		}
		return result;
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}
