package com.dlt.server.common.domain;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Id为String类型的BaseEntity
 */
public class BaseEntityIdAsInteger extends CommonEntity {

    /**
     * 实体Id
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntityIdAsInteger that = (BaseEntityIdAsInteger) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<Integer> getIdSetBySelfCollection(Collection<? extends BaseEntityIdAsInteger> baseEntityCollection) {
        Set<Integer> idSet = new HashSet<Integer>();
        if (CollectionUtils.isNotEmpty(baseEntityCollection)) {
            for (BaseEntityIdAsInteger baseEntity : baseEntityCollection) {
                idSet.add(baseEntity.getId());
            }
        }
        return idSet;
    }

    public Map<Integer, ? extends BaseEntityIdAsInteger> getIdAndSelfMapBySelfCollection(Collection<? extends BaseEntityIdAsInteger> baseEntityCollection) {
        Map<Integer, BaseEntityIdAsInteger> idAndSelfMap = new HashMap<Integer, BaseEntityIdAsInteger>();
        if (CollectionUtils.isNotEmpty(baseEntityCollection)) {
            for (BaseEntityIdAsInteger baseEntity : baseEntityCollection) {
                idAndSelfMap.put(baseEntity.getId(), baseEntity);
            }
        }
        return idAndSelfMap;
    }
}
