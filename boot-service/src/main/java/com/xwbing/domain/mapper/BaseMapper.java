package com.xwbing.domain.mapper;

import java.util.List;
import java.util.Map;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/6/2 10:32
 * 作者: xiangwb
 * 说明: 基础mapper
 */
public interface BaseMapper<T> {
    int save(T t);

    int saveBatch(List<T> list);

    int deleteById(String id);

    int deleteByIds(String[] ids);

    int delete(Map<String, Object> map);

    int update(T t);

    int updateBatch(List<T> list);

    T findById(String id);

    List<T> find(Map<String, Object> map);

    List<T> findAll();
}
