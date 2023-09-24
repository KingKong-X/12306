package com.xingyuan.designpattern.chian;

import org.springframework.core.Ordered;

/**
 * @author Xingyuan Huang
 * @since 2023/9/21 15:39
 */
public interface AbstractChainHandler<T> extends Ordered {
    /**
     * execute chain of responsibility logic
     * @param requestParam chain of responsibility execution input
     */
    void handler(T requestParam);

    /**
     * @return chain of responsibility component identification
     */
    String mark();
}
