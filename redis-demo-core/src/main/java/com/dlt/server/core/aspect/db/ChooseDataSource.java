package com.dlt.server.core.aspect.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 获取数据源，用于动态切换数据源
 * Created by xuliugen on 2017/5/3.
 */
public class ChooseDataSource extends AbstractRoutingDataSource {

    /**
     * 实现父类中的抽象方法，获取数据源名称
     * @return
     */
    protected Object determineCurrentLookupKey() {
        return DataSourceHandler.getDataSource();
    }
}
