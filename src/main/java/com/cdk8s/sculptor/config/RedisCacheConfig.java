package com.cdk8s.sculptor.config;

import com.cdk8s.sculptor.util.code.Md5Util;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("all")
@Slf4j
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();

		// default 缓存配置（2小时）
		// 当不指定 cacheNames 或者指定的 cacheName 的名字跟下面 map 的 key 不一样的时候就会采用该配置
		RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofHours(2))
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer()))
				.disableCachingNullValues().prefixKeysWith("SPRING_CACHE::DEFAULT:");

		// 其他配置
		// 必须配置项:RedisCacheConfiguration(不能修改该注释)
		RedisCacheConfiguration SysParamTypeService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysParamTypeService:");
		RedisCacheConfiguration SysDictItemService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysDictItemService:");
		RedisCacheConfiguration SysDictService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysDictService:");
		RedisCacheConfiguration SysEventLogService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysEventLogService:");
		RedisCacheConfiguration RelPermissionRoleService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:RelPermissionRoleService:");
		RedisCacheConfiguration SysPermissionService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysPermissionService:");
		RedisCacheConfiguration RelDeptUserService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:RelDeptUserService:");
		RedisCacheConfiguration SysDeptService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysDeptService:");
		RedisCacheConfiguration SysEmployeeService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysEmployeeService:");
		RedisCacheConfiguration RelRoleUserService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:RelRoleUserService:");
		RedisCacheConfiguration SysRoleService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysRoleService:");
		RedisCacheConfiguration SysLoginLogService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysLoginLogService:");
		RedisCacheConfiguration SysUserService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysUserService:");
		RedisCacheConfiguration SysParamService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysParamService:");


		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new LinkedHashMap<String, RedisCacheConfiguration>(30) {
			private static final long serialVersionUID = -1543764675265322850L;

			{
				// map 的 key 是代表 cacheNames
				// 必须配置项:cacheNamesMap(不能修改该注释)
				put("SysParamTypeService", SysParamTypeService);
				put("SysDictItemService", SysDictItemService);
				put("SysDictService", SysDictService);
				put("SysEventLogService", SysEventLogService);
				put("RelPermissionRoleService", RelPermissionRoleService);
				put("SysPermissionService", SysPermissionService);
				put("RelDeptUserService", RelDeptUserService);
				put("SysDeptService", SysDeptService);
				put("SysEmployeeService", SysEmployeeService);
				put("RelRoleUserService", RelRoleUserService);
				put("SysRoleService", SysRoleService);
				put("SysLoginLogService", SysLoginLogService);
				put("SysUserService", SysUserService);
				put("SysParamService", SysParamService);
			}
		};

		return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory), defaultCacheConfiguration, redisCacheConfigurationMap);
	}

	/**
	 * 异常处理 当Redis缓存相关操作发生异常时 打印日志 程序正常走
	 */
	@Override
	public CacheErrorHandler errorHandler() {

		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
			@Override
			public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
				log.warn("Redis CacheErrorHandler handleCacheGetError：key: <{}>", key);
			}

			@Override
			public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
				log.warn("Redis CacheErrorHandler handleCachePutError：key: <{}>；value: <{}>", key, value);
			}

			@Override
			public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
				log.warn("Redis CacheErrorHandler handleCacheEvictError：key: <{}>", key);
			}

			@Override
			public void handleCacheClearError(RuntimeException e, Cache cache) {
				log.warn("Redis CacheErrorHandler handleCacheClearError");
			}
		};
		return cacheErrorHandler;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = jsonSerializer();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);

		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * 基础类型包括：
	 * boolean、char、byte、short、int、long、float、double
	 * java.lang.String
	 * java.lang.Boolean
	 * java.lang.Character
	 * java.lang.Byte
	 * java.lang.Short
	 * java.lang.Integer
	 * java.lang.Long
	 * java.lang.Float
	 * java.lang.Double
	 * 其他，则都是非基础类型
	 */
	@Bean
	public KeyGenerator keyGeneratorToServiceParam() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder finalResult = new StringBuilder();
				finalResult.append(method.getName());
				finalResult.append(":");

				if (params.length == 0) {
					finalResult.append("noParams");
					return finalResult.toString();
				}

				// 只含有一个参数位置，并且是基础类型，则进行特殊处理
				if (params.length == 1) {
					Object param = params[0];
					Class<?> clazz = param.getClass();
					if (checkClassBasicType(clazz)) {
						finalResult.append(param.toString());
						return finalResult.toString();
					}
				}

				// 非基础类型或多参数的场景
				StringBuilder paramString = new StringBuilder();
				for (int i = 0; i < params.length; i++) {
					paramString.append(params[i].toString());
					if (i != params.length - 1) {
						paramString.append(":");
					}
				}

				String finalParam = paramString.toString();
				String md5ByGuava = Md5Util.md5ByGuava(finalParam);

				log.debug("------zch------keyGeneratorToServiceParam <{}>, MD5 <{}>", finalParam, md5ByGuava);

				finalResult.append(md5ByGuava);
				return finalResult.toString();
			}
		};
	}

	/**
	 * 自定义默认键的生成策略
	 * 当不指定 key 值的时候可以这样用：@Cacheable(cacheNames = "sysUserCache", keyGenerator = "keyGeneratorByObject")
	 */
	@Bean
	public KeyGenerator keyGeneratorByObject() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder finalResult = new StringBuilder();
				// 相比 ToService 的生成策略，这个特点在于这里会获取类名
				finalResult.append(target.getClass().getSimpleName());
				finalResult.append(":");
				finalResult.append(method.getName());
				finalResult.append(":");

				if (params.length == 0) {
					finalResult.append("noParams");
					return finalResult.toString();
				}

				StringBuilder paramString = new StringBuilder();
				for (int i = 0; i < params.length; i++) {
					paramString.append(params[i].toString());
					if (i != params.length - 1) {
						paramString.append(":");
					}
				}

				String finalParam = paramString.toString();
				String md5ByGuava = Md5Util.md5ByGuava(finalParam);

				log.debug("------zch------customKeyGenerator <{}>, MD5 <{}>", finalParam, md5ByGuava);

				finalResult.append(md5ByGuava);
				return finalResult.toString();
			}
		};
	}

	// ======================================================

	private Jackson2JsonRedisSerializer jsonSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}

	private Boolean checkClassBasicType(Class<?> clazz) {
		// 判断基本类型（boolean、char、byte、short、int、long、float、double）
		if (clazz.isPrimitive()) {
			return true;
		}

		// 判断原始类型
		String classTypeName = clazz.getName();
		ArrayList<String> basicTypeList = Lists.newArrayList(
				"java.lang.String",
				"java.lang.Boolean",
				"java.lang.Character",
				"java.lang.Byte",
				"java.lang.Short",
				"java.lang.Integer",
				"java.lang.Long",
				"java.lang.Float",
				"java.lang.Double"
		);

		if (basicTypeList.contains(classTypeName)) {
			return true;
		}
		return false;
	}
}
