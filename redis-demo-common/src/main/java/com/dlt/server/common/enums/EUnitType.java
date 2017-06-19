package com.dlt.server.common.enums;

/**
 * 单位类型：电力公司、施工公司、设计公司
 * Created by xuliugen on 2017/5/29.
 */
public enum EUnitType implements IEnum {

    POWER_COMPANY(1, "电力公司"),
    CONSTRUCTION_COMPANY(2, "施工公司"),
    DESIGN_COMPANY(3, "设计公司");

    public Integer code;
    public String value;

    EUnitType(Integer value, String desc) {
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
