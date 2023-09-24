package com.xingyuan.study.userservice.constant;

/**
 * @author Xingyuan Huang
 * @since 2023/9/23 15:52
 */
public final class RedisKeyConstant {
    public static final String LOCK_USER_REGISTER = "index12306-user-service:lock:user-register:";

    /**
     * user registration can reuse username sharding
     */
    public static final String USER_REGISTER_REUSE_SHARDING = "index12306-user-service:user-reuse:";
}
