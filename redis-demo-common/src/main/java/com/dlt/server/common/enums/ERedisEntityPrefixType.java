package com.dlt.server.common.enums;

/**
 * 将Redis存放实体对象的前缀字符串替换为特殊标志，例如S1、S2、D1、D2等
 * Created by xuliugen on 2017/6/5.
 */
public enum ERedisEntityPrefixType implements IEnum {

    SECURITY_USER(1, "SU1"),
    OTHER(100, "OTHER");

    public Integer code;
    public String value;

    ERedisEntityPrefixType(Integer value, String desc) {
        this.code = value;
        this.value = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
