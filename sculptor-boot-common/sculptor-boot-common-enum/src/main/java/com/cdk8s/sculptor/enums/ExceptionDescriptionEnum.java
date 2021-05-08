/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ExceptionDescriptionEnum.java
 * 项目名称：sculptor-boot-common-enum
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.enums;

public enum ExceptionDescriptionEnum {

	SIGNATURE_DOES_NOT_MATCH_EXPRESSION("SignatureDoesNotMatch", "阿里云签名不匹配，请联系管理员"),
	EXCEPTION_EVALUATING_SPRING_EL_EXPRESSION("Exception evaluating SpringEL expression", "模板有表达式错误，请联系管理员"),
	TEMPLATE_MIGHT_NOT_EXIST("template might not exist", "找不到该模板页面"),
	GET_REQUEST_METHOD_NOT_SUPPORTED("Request method 'GET' not supported", "该方法不支持 GET 请求"),
	POST_REQUEST_METHOD_NOT_SUPPORTED("Request method 'POST' not supported", "该方法不支持 POST 请求"),
	REDISSON_UNLOCK("attempt to unlock lock", "Redisson 解锁失败，请联系管理员"),
	SQLSYNTAX_ERROR_EXCEPTION("java.sql.SQLSyntaxErrorException", "SQL 语法有问题，请联系管理员"),
	SQL_WHERE_AMBIGUOUS("where clause is ambiguous", "查询条件中字段不明确，如果是关联查询请检查是否存在别名"),
	ALIYUN_VOD_UPLOAD("Currently Video Status is UploadSucc", "视频正在转码中，请稍后进行播放"),
	ALIYUN_VOD_PLAY("Currently Video Status is Transcoding", "视频正在转码中，请稍后进行播放"),
	NOT_OPEN_JDBC_CONNECTION("Could not open JDBC Connection for transaction", "无法打开 JDBC 连接，请联系管理员"),
	NO_GETTER_FOR_PROPERTY_NAMED("nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named", "MyBatis XML 查询条件中的属性在参数类中没有对应的，请联系管理员"),
	INCORRECT_STRING_VALUE("Cause: java.sql.SQLException: Incorrect string value", "暂不支持插入 emoji，请联系管理员"),
	DATA_INTEGRITY_VIOLATION_EXCEPTION("DataIntegrityViolationException", "数据完整性约束缺失，请联系管理员"),
	DATA_INTEGRITY_VIOLATION_EXCEPTION2("JdbcSQLIntegrityConstraintViolationException", "数据完整性约束缺失，请联系管理员"),
	JDBC_SQL_SYNTAX_ERROR_EXCEPTION("JdbcSQLSyntaxErrorException", "数据库语法错误，请联系管理员"),
	SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION("SQLIntegrityConstraintViolationException", "SQL完整性约束缺失，请联系管理员"),
	REQUEST_BODY_IS_MISSING("Required request body is missing", "缺少 POST 请求参数"),
	CANNOT_BE_CAST_TO("cannot be cast to", "系统转换对象异常，请联系管理员进行处理"),
	REDIS_CONNECTION_EXCEPTION("RedisConnectionException", "连接缓存失败，请联系管理员进行处理"),
	ERROR_QUERYING_DATABASE("Error querying database", "查询数据库失败，请联系管理员进行处理"),
	REQUEST_CONTENT_TYPE_NOT_SUPPORTED("Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported", "请求内容类型不支持"),
	UNRECOGNIZED_PROPERTY_EXCEPTION("com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException", "JSON 序列化异常，有无法识别的属性"),
	NO_HANDLER_FOUND("No handler found for", "请求地址不存在"),
	JSON_PAUSE_ERROR("JSON parse error", "JSON 转换失败"),
	SQL_DEFAULT_VALUE("doesn't have a default value", "缺少指定字段值"),
	SQL_QUERY_ERROR("Error querying database", "查询数据库失败"),
	INPUT_FORMAT_ERROR("For input string:", "输入格式有误"),
	DATA_SIZE_TOO_LONG("java.sql.SQLException: Data too long for column", "存在某个字段过长"),
	DATABASE_UPDATE_ERROR("Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException", "操作数据失败，违背完整性约束"),
	SERVICE_REFUSE("java.net.ConnectException: Connection refused (Connection refused)", "连接某些服务被拒接"),
	SERVICE_NOT_AVAILABLE("com.netflix.client.ClientException: Load balancer does not have available server for client", "存在某些服务不可用"),
	SERVICE_TIMEOUT("java.util.concurrent.TimeoutException", "10000服务连接超时"),
	MYSQL_CONNECTION_TIMEOUT("Could not open JDBC Connection for transaction; nested exception is com.mysql.jdbc", "JDBC 服务连接超时"),
	REDIS_CONNECTION_TIMEOUT(" redis.clients.jedis.exceptions.JedisConnectionException", "Redis 服务连接超时"),
	RABBITMQ_CONNECTION_TIMEOUT("org.springframework.amqp.AmqpTimeoutException: java.util.concurrent.TimeoutException", "MQ 服务连接超时"),
	FEIGN_FAIL_AND_NO_FALLBACK("failed and no fallback available", "Feign 连接失败且无回退方法"),
	FEIGN_FAIL_AND_FALLBACK_AVAILABLE("failed and fallback failed", "Feign 连接失败且回退失败");

	private String keyWord;
	private String description;

	public String getKeyWord() {
		return keyWord;
	}

	public String getDescription() {
		return description;
	}

	ExceptionDescriptionEnum(final String keyWord, final String description) {
		this.keyWord = keyWord;
		this.description = description;
	}

}
