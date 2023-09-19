package com.xingyuan.study.cache;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Xingyuan Huang
 * @since 2023/9/13 15:57
 */
@RequiredArgsConstructor
public class StringRedisTemplateProxy implements DistributedCache{
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return JSON.parseObject(value, clazz);
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void put(String key, Object value, long timeout) {

    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        String actual = value instanceof String ? (String) value : JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(key, actual, timeout, timeUnit);
    }

    @Override
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }
}
