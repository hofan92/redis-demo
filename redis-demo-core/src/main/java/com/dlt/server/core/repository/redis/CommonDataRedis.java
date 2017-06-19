package com.dlt.server.core.repository.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

/**
 * 简单的Key-Value数据类型存储
 * Created by xuliugen on 2017/6/5.
 */
@Named
public class CommonDataRedis {

    /**
     * 默认失效过期时间30分钟，单位为second
     */
    private static final Long EXPIRE_TIME_30_MINUTE = 30 * 60L;

    @Inject
    private StringRedisTemplate stringRedisTemplate;

    public void saveKeyAndValue(final String smsCodeToken, final String codeNum) {

        //stringRedisTemplate.opsForValue().set(smsCodeToken, codeNum, EXPIRE_TIME_30_MINUTE, TimeUnit.SECONDS);

        /**
         * 添加事务的使用
         */
        Object results = stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.multi();
                ValueOperations valueOperations = operations.opsForValue();
                valueOperations.set(smsCodeToken, codeNum, EXPIRE_TIME_30_MINUTE, TimeUnit.SECONDS);
                return operations.exec();
            }
        });
    }

    public String getValueByKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
