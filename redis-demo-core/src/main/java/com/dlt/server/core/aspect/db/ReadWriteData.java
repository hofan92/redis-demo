package com.dlt.server.core.aspect.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 判断是否是读写分离的注解
 * Created by xuliugen on 2017/5/3.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReadWriteData {
    String value();
}
