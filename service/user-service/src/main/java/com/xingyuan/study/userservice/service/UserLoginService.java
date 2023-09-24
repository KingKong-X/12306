package com.xingyuan.study.userservice.service;

import com.xingyuan.study.userservice.dto.req.UserLoginReqDTO;
import com.xingyuan.study.userservice.dto.req.UserRegisterReqDTO;
import com.xingyuan.study.userservice.dto.resp.UserLoginRespDTO;
import com.xingyuan.study.userservice.dto.resp.UserRegisterRespDTO;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 9:26
 */
public interface UserLoginService {
    /**
     * user login interface
     *
     * @param requestParam the parameter of user login
     * @return the result of user login
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * check if user login
     *
     * @param accessToken the certificate of user login
     * @return the result of whether user login in
     */
    UserLoginRespDTO checkLogin(String accessToken);

    /**
     * user logout
     *
     * @param accessToken the certificate of user login
     */
    void logout(String accessToken);

    /**
     * check whether username exists
     *
     * @param username username
     * @return result of whether user exists
     */
    Boolean hasUsername(String username);

    /**
     * user register
     *
     * @param requestParam user registration input
     * @return the result of user register
     */
    UserRegisterRespDTO register(UserRegisterReqDTO requestParam);
}
