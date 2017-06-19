package com.dlt.server.core.repository.redis.base;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.jredis.JredisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuliugen on 2017/6/5.
 */
public class RedisDemo {

    @Inject
    private RedisTemplate redisTemplate;

    @Inject
    private StringRedisTemplate stringRedisTemplate;

    public void demo1() {
        String key = "spring";
        ListOperations<String, String> lop = redisTemplate.opsForList();
        // 测试RedisTemplate,自主处理key的可读性(String序列号)
        RedisSerializer<String> serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        // rt.setDefaultSerializer(serializer);

        lop.leftPush(key, "aaa");
        lop.leftPush(key, "bbb");
        long size = lop.size(key); // rt.boundListOps(key).size();
    }

    /**
     * 测试便捷对象StringRedisTemplate
     */
    public void demo2() {
        ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
        String key = "string_redis_template";
        String v = "use StringRedisTemplate set k v";
        vop.set(key, v);
        String value = vop.get(key);
    }

    /**
     * 通过RedisTemplate中的方法的参数RedisCallback回调接口来获取RedisConnection，进一步操作Redis，比如事务控制。
     * 需要注意的是如果使用StringRedisTemplate则返回的是StringRedisConnection对象。
     */
    public void demo3() {
        Long dbsize = (Long) stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                return stringRedisConnection.dbSize();
            }
        });
        System.out.println("dbsize:" + dbsize);
    }


    /**
     * multi()和exec()包裹事务
     */
    public void demo4() {
        List<Object> txresult = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForHash().put("hkey", "multikey4", "multivalue4");
                operations.opsForHash().get("hkey", "k1");
                return operations.exec();
            }
        });

        for (Object o : txresult) {
            System.out.println(o);
        }
    }

    /**
     * 测试Lua脚本
     */
    public void demo5() {
        List<String> keys = new ArrayList<String>();
        RedisScript<Long> script = new DefaultRedisScript<Long>("local size = redis.call('dbsize'); return size;", Long.class);
        Long dbsize = stringRedisTemplate.execute(script, keys, new Object[]{});
        System.out.println("sha1:" + script.getSha1());
        System.out.println("Lua:" + script.getScriptAsString());
        System.out.println("dbsize:" + dbsize);
    }

    public void demo52() {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<Boolean>();
        /**
         * isexistskey.lua内容如下：
         *
         * return tonumber(redis.call("exists",KEYS[1])) == 1;
         */
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/isexistskey.lua")));

        script.setResultType(Boolean.class);// Must Set

        System.out.println("script:" + script.getScriptAsString());
        Boolean isExist = stringRedisTemplate.execute(script, Collections.singletonList("k2"), new Object[]{});
    }


    public void demo6(JredisConnectionFactory jredisConnectionFactory) {
        RedisAtomicInteger rai = new RedisAtomicInteger("redis:atomic", jredisConnectionFactory);
        System.out.println(rai.get());
    }


    public void valueOperationSample() {
        ValueOperations<String, User> valueOper = redisTemplate.opsForValue();
        User suser = new User(1, "zhangsan", 12);
        valueOper.set("user:" + suser.getId(), suser);
        System.out.println(valueOper.get("user:" + suser.getId()).getName());
    }

    public void listOperationSample() {
        User suser = new User(1, "zhangsan", 12);
        ListOperations<String, User> listOper = redisTemplate.opsForList();
        listOper.leftPush("user:list", suser);//lpush,head
        listOper.rightPush("user:list", suser);//rpush,tail
    }

    public void boundValueOperationSample() {
        User suser = new User(1, "zhangsan", 12);
        BoundValueOperations<String, User> bvo = redisTemplate.boundValueOps("user:" + suser.getId());
        bvo.set(suser);
        bvo.expire(60, TimeUnit.MINUTES);
    }

    /**
     * 非连接池环境下，事务操作；对于SDR而言，每次操作(例如，get，set)都有会从pool中获取connection；
     * 因此在连接池环境下，使用事务需要注意。
     */
    public void txUnusedPoolSample() {
        User suser = new User(1, "zhangsan", 12);
        redisTemplate.watch("user:" + suser.getId());
        redisTemplate.multi();
        ValueOperations<String, User> tvo = redisTemplate.opsForValue();
        tvo.set("user:" + suser.getId(), suser);
        redisTemplate.exec();
    }

    /**
     * 在连接池环境中，需要借助sessionCallback来绑定connection
     */
    public void txUsedPoolSample() {
        SessionCallback<User> sessionCallback = new SessionCallback<User>() {
            @Override
            public User execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                User user = new User(2, "lisi", 32);
                String key = "user:" + user.getId();
                BoundValueOperations<String, User> oper = operations.boundValueOps(key);
                oper.set(user);
                oper.expire(60, TimeUnit.MINUTES);
                operations.exec();
                return user;
            }
        };
        redisTemplate.execute(sessionCallback);
    }

    /**
     * pipeline : 1，正确使用方式
     */
    public void pipelineSample() {
        final byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");
        //pipeline
        RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                connection.incr(rawKey);
                connection.incr(rawKey);
                return connection.closePipeline();
            }
        };

        List<Object> results = (List<Object>) redisTemplate.execute(pipelineCallback);
        for (Object item : results) {
            System.out.println(item.toString());
        }
    }

    /**
     * pipeline:备用方式
     */
    public void pipelineSampleX() {
        byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection redisConnection = factory.getConnection();
        List<Object> results;
        try {
            redisConnection.openPipeline();
            redisConnection.incr(rawKey);
            results = redisConnection.closePipeline();
        } finally {
            RedisConnectionUtils.releaseConnection(redisConnection, factory);
        }
        if (results == null) {
            return;
        }
        for (Object item : results) {
            System.out.println(item.toString());
        }
    }

    public class User {
        private int id;
        private String name;
        private int age;

        public User() {
        }

        public User(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
