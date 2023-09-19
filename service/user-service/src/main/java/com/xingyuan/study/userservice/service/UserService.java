package com.xingyuan.study.userservice.service;

import com.xingyuan.study.userservice.dto.resp.UserQueryRespDTO;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author Xingyuan Huang
 * @since 2023/9/19 16:02
 */
public interface UserService {
    /**
     * search user information by username
     *
     * @param username username
     * @return user information
     */
    UserQueryRespDTO queryUserByUsername(@NotEmpty String username);
}
