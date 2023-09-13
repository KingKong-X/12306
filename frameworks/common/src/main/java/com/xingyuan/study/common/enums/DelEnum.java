package com.xingyuan.study.common.enums;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 15:30
 */
public enum DelEnum {
    NORMAL(0),

    DELETE(1);

    private final Integer statusCode;

    DelEnum(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer code() {
        return this.statusCode;
    }

    public String strCode() {
        return String.valueOf(this.statusCode);
    }
}
