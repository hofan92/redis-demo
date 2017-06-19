package com.dlt.server.common.domain;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 符号常量类
 * Created by xuliugen on 16/1/10.
 */
public class BaseEntityIdAsString extends CommonEntity {

    /**
     * 实体Id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntityIdAsString that = (BaseEntityIdAsString) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * 根据集合获取实体ID的集合并返回
     * @param baseEntityCollection 实体的集合
     * @return
     */
    public Set<String> getIdSetBySelfCollection(Collection<? extends BaseEntityIdAsString> baseEntityCollection) {
        Set<String> idSet = new HashSet<String>();
        if (CollectionUtils.isNotEmpty(baseEntityCollection)) {
            for (BaseEntityIdAsString baseEntity : baseEntityCollection) {
                idSet.add(baseEntity.getId());
            }
        }
        return idSet;
    }

    /**
     * 根据集合获取集合内的数据id的Map，Map的key为id，Value为实体本身
     * @param baseEntityCollection 实体的集合
     * @return
     */
    public Map<String, ? extends BaseEntityIdAsString> getIdAndSelfMapBySelfCollection(Collection<? extends BaseEntityIdAsString> baseEntityCollection) {
        Map<String, BaseEntityIdAsString> idAndSelfMap = new HashMap<String, BaseEntityIdAsString>();
        if (CollectionUtils.isNotEmpty(baseEntityCollection)) {
            for (BaseEntityIdAsString baseEntity : baseEntityCollection) {
                idAndSelfMap.put(baseEntity.getId(), baseEntity);
            }
        }
        return idAndSelfMap;
    }
}