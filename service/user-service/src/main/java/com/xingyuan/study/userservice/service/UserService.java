package com.xingyuan.study.userservice.service;

import com.xingyuan.study.userservice.dto.req.UserRegisterReqDTO;
import com.xingyuan.study.userservice.dto.resp.UserQueryRespDTO;
import com.xingyuan.study.userservice.dto.resp.UserRegisterRespDTO;
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

    /**
     * query the number of account cancellations
     *
     * @param idType certificate type
     * @param idCard certificate number
     *
     * @return the number of account cancellations
     */
    Integer queryUserDeletionNum(Integer idType, String idCard);
}
