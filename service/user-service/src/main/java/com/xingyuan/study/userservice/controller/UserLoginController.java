package com.xingyuan.study.userservice.controller;

import com.xingyuan.study.convention.result.Result;
import com.xingyuan.study.userservice.dto.req.UserLoginReqDTO;
import com.xingyuan.study.userservice.dto.resp.UserLoginRespDTO;
import com.xingyuan.study.userservice.service.UserLoginService;
import com.xingyuan.study.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xingyuan Huang
 * @since 2023/9/11 20:48
 */
@RestController
@RequiredArgsConstructor
public class UserLoginController {
    private final UserLoginService userLoginService;

    /**
     * user login
     */
    @PostMapping("/api/user-service/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        return Results.success(userLoginService.login(requestParam));
    }

    /**
     * check whether user login by token
     */
    @GetMapping("/api/user-service/check-login")
    public Result<UserLoginRespDTO> checkLogin(@RequestParam("accessToken") String accessToken) {
        UserLoginRespDTO result = userLoginService.checkLogin(accessToken);
        return Results.success(result);
    }

    /**
     * user logout
     */
    @GetMapping("/api/user-service/logout")
    public Result<Void> logout(@RequestParam(required = false) String accessToken) {
        userLoginService.logout(accessToken);
        return Results.success();
    }
}
