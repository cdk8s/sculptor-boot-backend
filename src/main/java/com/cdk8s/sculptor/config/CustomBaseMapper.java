package com.cdk8s.sculptor.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 该类不能被 MapperScan 扫描到
 *
 * @param <T>
 */
public interface CustomBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
