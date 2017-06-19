package com.dlt.server.common.util;

import com.dlt.server.common.domain.BaseEntityIdAsInteger;
import com.dlt.server.common.domain.CommonEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by xuliugen on 2017/6/19.
 */
public class CollectionUtils {

    public static List<?> setToArrayList(Set<? extends BaseEntityIdAsInteger> set) {
        List<Object> list = new ArrayList<Object>(set.size());
        Iterator<?> it = set.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }
}
