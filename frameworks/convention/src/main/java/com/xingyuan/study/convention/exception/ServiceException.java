package com.xingyuan.study.convention.exception;

import com.xingyuan.study.convention.errorcode.BaseErrorCode;
import com.xingyuan.study.convention.errorcode.IErrorCode;

import java.util.Optional;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 20:13
 */
public class ServiceException extends AbstractException{
    public ServiceException(String message) {
        super(message, null, BaseErrorCode.SERVICE_ERROR);
    }

    public ServiceException(String message, IErrorCode errorCode) {
        super(message, null, errorCode);
    }

    public ServiceException(IErrorCode errorCode) {
        super(null, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message()), throwable, errorCode);
    }
}
