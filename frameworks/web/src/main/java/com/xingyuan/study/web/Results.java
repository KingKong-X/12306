package com.xingyuan.study.web;

import com.xingyuan.study.convention.result.Result;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 9:31
 *
 * global return object constructor
 */
public class Results {
    /**
     * construct success response
     */
    public static Result<Void> success() {
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }

    /**
     * construct success response with data
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(Result.SUCCESS_CODE)
                .setData(data);
    }
}
