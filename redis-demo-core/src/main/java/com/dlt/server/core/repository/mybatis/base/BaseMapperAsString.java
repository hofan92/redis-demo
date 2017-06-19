package com.dlt.server.core.repository.mybatis.base;

import com.dlt.server.common.domain.BaseEntityIdAsString;

import java.util.List;

/**
 * Created by xuliugen on 2016/3/13.
 */
public interface BaseMapperAsString<T> {

    int deleteByPrimaryKey(String entityId);

    <T extends BaseEntityIdAsString> int insert(T entity);

    <T extends BaseEntityIdAsString> int insertSelective(T entity);

    <T extends BaseEntityIdAsString> T selectByPrimaryKey(String entityId);

    <T extends BaseEntityIdAsString> int updateByPrimaryKeySelective(T entity);

    <T extends BaseEntityIdAsString> int updateByPrimaryKey(T record);

    /**
     * 查找指定类型的所有实体
     */
    <T extends BaseEntityIdAsString> List<T> findAll();

}
