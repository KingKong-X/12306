package com.xingyuan.study.cache;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Xingyuan Huang
 * @since 2023/9/13 14:09
 */
public interface Cache {
    /**
     * get cache
     */
    <T> T get(@NotBlank String key, Class<T> clazz);

    /**
     * put in cache
     */
    void put(@NotBlank String key, Object value);

    /**
     * delete cache
     */
    Boolean delete(@NotBlank String key);
}
