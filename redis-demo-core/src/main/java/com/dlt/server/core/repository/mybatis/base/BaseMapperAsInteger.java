package com.dlt.server.core.repository.mybatis.base;

import com.dlt.server.common.domain.BaseEntityIdAsInteger;

import java.util.List;

/**
 * Created by xuliugen on 2016/3/13.
 */
public interface BaseMapperAsInteger<T> {

    int deleteByPrimaryKey(Integer entityId);

    <T extends BaseEntityIdAsInteger> int insert(T entity);

    <T extends BaseEntityIdAsInteger> int insertSelective(T entity);

    <T extends BaseEntityIdAsInteger> T selectByPrimaryKey(Integer entityId);

    <T extends BaseEntityIdAsInteger> int updateByPrimaryKeySelective(T entity);

    <T extends BaseEntityIdAsInteger> int updateByPrimaryKey(T record);

    /**
     * 查找指定类型的所有实体
     */
    <T extends BaseEntityIdAsInteger> List<T> findAll();

}
