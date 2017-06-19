package com.dlt.server.core.aspect.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源(不同方法调用不同数据源)
 * Created by xuliugen on 2017/5/3.
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截的是领域实体的方法,这样的话 粒度要比application小
     */
    @Pointcut("execution(* com.dlt.server.core.service.*.*(..))")
    public void aspect() {
    }

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     */
    @Before("aspect()")
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        logger.info(target.toString() + "--" + method);
        Class<?> classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz.getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(ReadWriteData.class)) {
                ReadWriteData annotation = m.getAnnotation(ReadWriteData.class);
                DataSourceHandler.putDataSource(annotation.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
