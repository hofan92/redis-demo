package com.dlt.server.common.enums;

/**
 * 所有枚举类型的基类
 * Created by xuliugen on 2017/4/5.
 */
public interface IEnum {

    /**
     * 获取key值
     * @return
     */
    public Integer getCode();

    /**
     * 获取value值
     * @return
     */
    public String getValue();

}
