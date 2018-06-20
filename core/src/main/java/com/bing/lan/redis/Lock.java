package com.bing.lan.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by 蓝兵 on 2018/6/20.
 */

public class Lock {

    private long lockTimeout = 5;//锁的过期时间，单位：秒
    private long lockSleepTime = 100;//未取到锁时的sleep时间，单位：毫秒
    private RedisTemplate<String, Object> redisTemplate;

    Lock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 直到获取到锁才返回
     */
    public void lock(String key) {
        lock(key, lockTimeout, lockSleepTime);
    }

    /**
     * 直到获取到锁才返回
     */
    public void lock(String key, long lockTimeout, long lockSleepTime) {
        while (true) {
            long time = System.currentTimeMillis();
            boolean isSet = redisTemplate.opsForValue().setIfAbsent(key, time);
            if (isSet) {
                redisTemplate.expire(key, lockTimeout, TimeUnit.SECONDS);
                return;
            } else {
                if (redisTemplate.getExpire(key) < 0) {
                    redisTemplate.expire(key, lockTimeout, TimeUnit.SECONDS);
                }
            }
            try {
                Thread.sleep(lockSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取到锁返回true,未获取到返回false
     */
    public boolean lock(String key, long lockTimeout) {
        long time = System.currentTimeMillis();
        boolean isSet = redisTemplate.opsForValue().setIfAbsent(key, time);
        if (isSet) {
            redisTemplate.expire(key, lockTimeout, TimeUnit.SECONDS);
            return true;
        } else {
            if (redisTemplate.getExpire(key) < 0) {
                redisTemplate.expire(key, lockTimeout, TimeUnit.SECONDS);
            }
        }
        return false;
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
