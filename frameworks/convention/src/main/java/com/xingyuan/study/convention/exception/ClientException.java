package com.xingyuan.study.convention.exception;

import com.xingyuan.study.convention.errorcode.BaseErrorCode;
import com.xingyuan.study.convention.errorcode.IErrorCode;

import java.util.Optional;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 19:40
 */
public class ClientException extends AbstractException{
    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message()), throwable, errorCode);
    }

    public ClientException(IErrorCode errorCode) {
        super(null, null, errorCode);
    }

    public ClientException(String message) {
        super(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        super(message, null, errorCode);
    }
}
