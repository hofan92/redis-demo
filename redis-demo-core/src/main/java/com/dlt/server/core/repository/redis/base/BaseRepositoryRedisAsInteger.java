package com.dlt.server.core.repository.redis.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dlt.server.common.constant.ConstPunctuation;
import com.dlt.server.common.domain.BaseEntityIdAsInteger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xuliugen on 16/1/10.
 * @param <T>
 */
@Named
public abstract class BaseRepositoryRedisAsInteger<T extends BaseEntityIdAsInteger> {

    //构造redis中key的时候id的位数
    public static final int KEY_ID_NUM = 10;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseRepositoryRedisAsInteger.class);
    @Inject
    private StringRedisTemplate stringRedisTemplate;

    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 根据id删除Redis中的数据
     */
    public void deleteByPrimaryKey(String keyPrefix, Integer id) {
        String keyPattern = assembleKeyPatternIdAsPostFix(keyPrefix, id);
        logger.info(keyPattern);

        //取出所有与该模式匹配的key
        Set<String> keySet = stringRedisTemplate.keys(keyPattern);
        logger.debug("keySet-{}", keySet);
        stringRedisTemplate.delete(keySet);
    }

    public void deleteByPrimaryKey(Integer id) {
        String keyPattern = assembleKeyPatternIdAsPostFix(id);
        logger.info(keyPattern);

        //取出所有与该模式匹配的key
        Set<String> keySet = stringRedisTemplate.keys(keyPattern);
        logger.debug("keySet-{}", keySet);
        stringRedisTemplate.delete(keySet);
    }

    /**
     * 批量删除
     */
    public void deleteInBatch(String keyPrefix, Set<Integer> idSet) {
        for (Integer id : idSet) {
            deleteByPrimaryKey(keyPrefix, id);
        }
    }

    /**
     * 判断是否为空之后，插入数据
     */
    public int insertSelective(T entity) {
        Set<T> set = new HashSet<T>();
        set.add(entity);
        insertInBatch(set);
        return 0;
    }

    /**
     * 批量插入
     */
    public void insertInBatch(Set<T> entitySet) {
        if (CollectionUtils.isNotEmpty(entitySet)) {
            for (T entity : entitySet) {
                //获得Reids中存储的key
                String key = assembleRedisKey(entity);
                stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(entity));
            }
        }
    }

    /**
     * 更新Redis中的缓存
     */
    public void updateByPrimaryKey(String keyPrefix, T entity) {
        //因为传入的参数与Key相关的某些属性已经发生变化，所以此时的组装出来的key已经与原Key不一样。
        // 但ID是不变的且唯一的，所以通过ID找到原Key，删除原Key，插入新Key
        String keyPattern = assembleKeyPatternIdAsPostFix(keyPrefix, entity.getId());
        Set<String> keySet = stringRedisTemplate.keys(keyPattern);
        stringRedisTemplate.delete(keySet);

        String key = assembleRedisKey(entity);
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(entity));
    }

    /**
     * 更新Redis中的缓存
     */
    public void updateByPrimaryKey(T entity) {
        String keyPrefix = assembleRedisKey(entity);
        //因为传入的参数与Key相关的某些属性已经发生变化，所以此时的组装出来的key已经与原Key不一样。
        // 但ID是不变的且唯一的，所以通过ID找到原Key，删除原Key，插入新Key
        String keyPattern = assembleKeyPatternIdAsPostFix(keyPrefix, entity.getId());
        Set<String> keySet = stringRedisTemplate.keys(keyPattern);
        stringRedisTemplate.delete(keySet);

        String key = assembleRedisKey(entity);
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(entity));
    }


    /**
     * 根据条件进行查询
     */
    public Set<T> listByCondition(T entity) {
        Set<T> entitySet = new HashSet<T>();
        if (entity != null) {
            String keyPattern = assembleRedisKeyPattern(entity);
            logger.info(keyPattern);

            //取出所有与该模式匹配的key
            Set<String> keySet = stringRedisTemplate.keys(keyPattern);
            if (CollectionUtils.isNotEmpty(keySet)) {
                for (String key : keySet) {
                    String value = stringRedisTemplate.boundValueOps(key).get();
                    logger.debug("key-{},value-{}", key, value);
                    if (value != null) {
                        T entityFromCache = (T) JSON.parseObject(value, getEntityClassType());
                        entitySet.add(entityFromCache);
                    }
                }
            }
        }
        return entitySet;
    }

    /**
     * 批量更新
     */
    public void updateByPrimaryKeyInBatch(String keyPrefix, Set<T> entitySet) {
        for (T entity : entitySet) {
            updateByPrimaryKey(keyPrefix, entity);
        }
    }

    /**
     * 根据id的长度构造key需要的id
     * @param id
     * @return
     */
    public String assemberIdForKey(Integer id) {
        int idLen = (id + "").length();
        int zeroNum = KEY_ID_NUM - idLen;

        StringBuilder zeroNumString = new StringBuilder();

        for (int i = 0; i < zeroNum; i++) {
            zeroNumString.append("0");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(zeroNumString).append(id);

        return sb.toString();
    }

    /**
     * 组装存入Redis的Key的抽象方法，有子类实现具体的逻辑
     */
    protected abstract String assembleRedisKey(T entity);

    /**
     * 获取当前实体对象的Class
     */
    protected abstract Class getEntityClassType();

    /**
     * 组装Key的Pattern用于查询匹配的Redis的Key集合
     */
    protected abstract String assembleRedisKeyPattern(T entity);

    /**
     * 组装Id为后缀的KeyPattern,例如： PREFIX*Id
     */
    protected String assembleKeyPatternIdAsPostFix(String keyPrefix, Integer id) {
        String keyPattern = keyPrefix + ConstPunctuation.ASTERISK + id;
        logger.debug(keyPattern);
        return keyPattern;
    }

    protected String assembleKeyPatternIdAsPostFix(Integer id) {
        String keyPattern = ConstPunctuation.ASTERISK + id;
        logger.debug(keyPattern);
        return keyPattern;
    }
}
