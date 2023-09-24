package com.xingyuan.study.userservice.toolkit;

import static com.xingyuan.study.userservice.constant.Index12306Constant.USER_REGISTER_REUSE_SHARDING_COUNT;

/**
 * @author Xingyuan Huang
 * @since 2023/9/23 15:55
 */
public final class UserReuseUtil {
    /**
     * calculate sharding position
     */
    public static int hasShardingIdx(String username) {
        return Math.abs(username.hashCode() % USER_REGISTER_REUSE_SHARDING_COUNT);
    }
}
