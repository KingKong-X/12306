package com.xingyuan.study.convention.errorcode;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 16:05
 */
public enum BaseErrorCode implements IErrorCode{
    CLIENT_ERROR("A000001", "用户端错误"),

    SERVICE_ERROR("B000001", "系统执行出错"),;

    private final String code;

    private final String message;

    BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
