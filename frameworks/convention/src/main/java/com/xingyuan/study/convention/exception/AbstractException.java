package com.xingyuan.study.convention.exception;

import com.xingyuan.study.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 19:36
 */
@Getter
public abstract class AbstractException extends RuntimeException{
    private final String errorCode;

    private final String message;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.message = Optional.ofNullable(StringUtils.hasLength(message) ? message : null)
                .orElse(errorCode.message());
    }
}
