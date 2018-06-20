package com.bing.lan.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 蓝兵 on 2018/6/20.
 */

public class RedisClient {

    private RedisTemplate<String, Object> redisTemplate;

    public final Lock lock;
    public final Queue queue;

    public RedisClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.lock = new Lock(redisTemplate);
        this.queue = new Queue(this, redisTemplate);
    }

    public void putString(String key, String value) {
        putObject(key, value);
    }

    /**
     * @param timeout 单位：秒
     */
    public void putString(String key, String value, long timeout) {
        putString(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public String getString(String key) {
        Object obj = getObject(key);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public void delete(String... keys) {
        for (String key : keys) {
            if (key == null)
                continue;
            redisTemplate.delete(key);
        }
    }

    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void putObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void putObject(String key, Object value, long timeout) {
        putObject(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public void pushList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public Object popList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object indexFirstList(String key) {
        return redisTemplate.opsForList().index(key, 0);
    }

    public long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public List<Object> getList(String key, Long size) {
        return redisTemplate.opsForList().range(key, 0, size);
    }

    public void pushList(String key, Object value, Long timeout) {
        redisTemplate.opsForList().rightPush(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
}
