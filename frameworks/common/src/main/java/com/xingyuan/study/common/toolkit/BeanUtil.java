package com.xingyuan.study.common.toolkit;

import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author Xingyuan Huang
 * @since 2023/9/19 16:14
 */
@RequiredArgsConstructor
public class BeanUtil {
    private static Mapper BEAN_MAPPER_BUILDER;

    /**
     * copy single object
     *
     * @param source data object
     * @param clazz target type
     * @return the converted object
     */
    public static <T, S> T convert(S source, Class<T> clazz) {
        return Optional.ofNullable(source)
                .map(each -> BEAN_MAPPER_BUILDER.map(each, clazz))
                .orElse(null);
    }
}
