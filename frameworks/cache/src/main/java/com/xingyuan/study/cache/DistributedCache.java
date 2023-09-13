package com.xingyuan.study.cache;

import jakarta.validation.constraints.NotBlank;

import java.util.concurrent.TimeUnit;

/**
 * @author Xingyuan Huang
 * @since 2023/9/13 14:12
 */
public interface DistributedCache extends Cache{

    void put(@NotBlank String key, Object value, long timeout);

    void put(@NotBlank String key, Object value, long timeout, TimeUnit timeUnit);
}
