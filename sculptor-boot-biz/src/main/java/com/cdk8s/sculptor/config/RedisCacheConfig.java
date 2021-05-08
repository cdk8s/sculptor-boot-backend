/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RedisCacheConfig.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.config;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.util.JsonUtil;
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
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Slf4j
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

	public static List<String> CACHE_SERVICE_BEAN_NAME_LIST = new ArrayList<>();

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();

		// default 缓存配置（2小时）
		// 当不指定 cacheNames 或者指定的 cacheName 的名字跟下面 map 的 key 不一样的时候就会采用该配置
		RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofHours(2))
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer()))
				.disableCachingNullValues().prefixKeysWith(GlobalConstant.DEFAULT_REDIS_CACHE_KEY_PREFIX);

		// 其他配置
		// 必须配置项:RedisCacheConfiguration(不能修改该注释)
		RedisCacheConfiguration SysUserInfoService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysUserInfoService:");
		RedisCacheConfiguration SysBannerService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysBannerService:");
		RedisCacheConfiguration SysBigTextService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysBigTextService:");
		RedisCacheConfiguration PmsSpuInfoDescService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsSpuInfoDescService:");
		RedisCacheConfiguration PmsSpuInfoService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsSpuInfoService:");
		RedisCacheConfiguration PmsSpuImagesService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsSpuImagesService:");
		RedisCacheConfiguration PmsSkuSaleAttrValueService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsSkuSaleAttrValueService:");
		RedisCacheConfiguration PmsSkuInfoService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsSkuInfoService:");
		RedisCacheConfiguration PmsSkuImagesService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsSkuImagesService:");
		RedisCacheConfiguration PmsProductAttrValueService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsProductAttrValueService:");
		RedisCacheConfiguration PmsCategoryBrandRelationService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsCategoryBrandRelationService:");
		RedisCacheConfiguration PmsCategoryService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsCategoryService:");
		RedisCacheConfiguration PmsBrandService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsBrandService:");
		RedisCacheConfiguration PmsAttrGroupService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsAttrGroupService:");
		RedisCacheConfiguration PmsAttrAttrgroupRelationService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsAttrAttrgroupRelationService:");
		RedisCacheConfiguration PmsAttrService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:PmsAttrService:");
		RedisCacheConfiguration SysJobService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysJobService:");
		RedisCacheConfiguration SysTenantService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysTenantService:");
		RedisCacheConfiguration SysCityAreaService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysCityAreaService:");
		RedisCacheConfiguration CmsProductService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:CmsProductService:");
		RedisCacheConfiguration CmsProductCategoryService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:CmsProductCategoryService:");
		RedisCacheConfiguration CmsTableContentShowService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:CmsTableContentShowService:");
		RedisCacheConfiguration CmsGuestBookService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:CmsGuestBookService:");
		RedisCacheConfiguration SysFileInfoService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysFileInfoService:");
		RedisCacheConfiguration SysFolderInfoService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysFolderInfoService:");
		RedisCacheConfiguration CmsArticleService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:CmsArticleService:");
		RedisCacheConfiguration CmsArticleCategoryService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:CmsArticleCategoryService:");
		RedisCacheConfiguration SysParamTypeService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysParamTypeService:");
		RedisCacheConfiguration SysDictItemService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysDictItemService:");
		RedisCacheConfiguration SysDictService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysDictService:");
		RedisCacheConfiguration SysPermissionService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysPermissionService:");
		RedisCacheConfiguration SysDeptService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysDeptService:");
		RedisCacheConfiguration SysEmployeeService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysEmployeeService:");
		RedisCacheConfiguration SysRoleService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysRoleService:");
		RedisCacheConfiguration SysUserService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysUserService:");
		RedisCacheConfiguration SysParamService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:SysParamService:");
		RedisCacheConfiguration FreemarkerService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:FreemarkerService:");
		RedisCacheConfiguration UploadService = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixKeysWith("SPRING_CACHE:UploadService:");


		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new LinkedHashMap<String, RedisCacheConfiguration>(30) {
			private static final long serialVersionUID = -1543764675265322850L;

			{
				// map 的 key 是代表 cacheNames
				// 必须配置项:cacheNamesMap(不能修改该注释)
				put("SysUserInfoService", SysUserInfoService);
				put("SysBannerService", SysBannerService);
				put("SysBigTextService", SysBigTextService);
				put("PmsSpuInfoDescService", PmsSpuInfoDescService);
				put("PmsSpuInfoService", PmsSpuInfoService);
				put("PmsSpuImagesService", PmsSpuImagesService);
				put("PmsSkuSaleAttrValueService", PmsSkuSaleAttrValueService);
				put("PmsSkuInfoService", PmsSkuInfoService);
				put("PmsSkuImagesService", PmsSkuImagesService);
				put("PmsProductAttrValueService", PmsProductAttrValueService);
				put("PmsCategoryBrandRelationService", PmsCategoryBrandRelationService);
				put("PmsCategoryService", PmsCategoryService);
				put("PmsBrandService", PmsBrandService);
				put("PmsAttrGroupService", PmsAttrGroupService);
				put("PmsAttrAttrgroupRelationService", PmsAttrAttrgroupRelationService);
				put("PmsAttrService", PmsAttrService);
				put("SysJobService", SysJobService);
				put("SysTenantService", SysTenantService);
				put("SysCityAreaService", SysCityAreaService);
				put("CmsProductService", CmsProductService);
				put("CmsProductCategoryService", CmsProductCategoryService);
				put("CmsTableContentShowService", CmsTableContentShowService);
				put("CmsGuestBookService", CmsGuestBookService);
				put("SysFileInfoService", SysFileInfoService);
				put("SysFolderInfoService", SysFolderInfoService);
				put("CmsArticleService", CmsArticleService);
				put("CmsArticleCategoryService", CmsArticleCategoryService);
				put("SysParamTypeService", SysParamTypeService);
				put("SysDictItemService", SysDictItemService);
				put("SysDictService", SysDictService);
				put("SysPermissionService", SysPermissionService);
				put("SysDeptService", SysDeptService);
				put("SysEmployeeService", SysEmployeeService);
				put("SysRoleService", SysRoleService);
				put("SysUserService", SysUserService);
				put("SysParamService", SysParamService);
				put("FreemarkerService", FreemarkerService);
				put("UploadService", UploadService);
				put("DefaultCache", defaultCacheConfiguration);
			}
		};

		for (String key : redisCacheConfigurationMap.keySet()) {
			CACHE_SERVICE_BEAN_NAME_LIST.add(key);
		}

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
				log.debug("Redis CacheErrorHandler handleCacheGetError：key: <{}>", key);
			}

			@Override
			public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
				log.debug("Redis CacheErrorHandler handleCachePutError：key: <{}>；value: <{}>", key, value);
			}

			@Override
			public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
				log.debug("Redis CacheErrorHandler handleCacheEvictError：key: <{}>", key);
			}

			@Override
			public void handleCacheClearError(RuntimeException e, Cache cache) {
				log.debug("Redis CacheErrorHandler handleCacheClearError");
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
	 * 自定义 redis key 规则
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
	 * 默认 cache key 效果：SPRING_CACHE:DEFAULT:CmsArticleCategoryService:findListByServiceBO:b9af1f8f88eb723a8725c43cd1a39f37
	 */
	@Bean
	public KeyGenerator keyGeneratorToServiceParam() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder finalResult = new StringBuilder();
				// 必须有类名作为前缀，避免走入 Default 之后取的方法名一样造成无法类型转换
				finalResult.append(target.getClass().getSimpleName());
				finalResult.append(":");
				finalResult.append(method.getName());
				finalResult.append(":");

				if (params.length == 0) {
					finalResult.append("noParams");
					return finalResult.toString();
				}

				// 只含有一个参数位置，并且是基础类型，则进行特殊处理
				if (params.length == 1) {
					Object param = params[0];
					if (null == param) {
						finalResult.append("nullParams");
						return finalResult.toString();
					}
					Class<?> clazz = param.getClass();
					if (checkClassBasicType(clazz)) {
						finalResult.append(param.toString());
						return finalResult.toString();
					}
				}

				// 非基础类型或多参数的场景
				StringBuilder paramString = new StringBuilder();
				for (int i = 0; i < params.length; i++) {
					if (null == params[i]) {
						paramString.append("nullParams");
					} else {
						paramString.append(JsonUtil.toJson(params[i]));
					}
					if (i != params.length - 1) {
						paramString.append(":");
					}
				}

				String finalParam = paramString.toString();
				String md5ByGuava = Md5Util.md5ByGuava(finalParam);

				log.debug("------zch------keyGeneratorToServiceParam Method <{}>, Param <{}> MD5 <{}>", method.getName(), finalParam, md5ByGuava);

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
					paramString.append(JsonUtil.toJson(params[i]));
					if (i != params.length - 1) {
						paramString.append(":");
					}
				}

				String finalParam = paramString.toString();
				String md5ByGuava = Md5Util.md5ByGuava(finalParam);

				log.debug("------zch------customKeyGenerator Method <{}>, Param <{}> MD5 <{}>", method.getName(), finalParam, md5ByGuava);

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
