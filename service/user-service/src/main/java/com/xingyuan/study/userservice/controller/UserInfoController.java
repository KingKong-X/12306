package com.xingyuan.study.userservice.controller;

import com.xingyuan.study.convention.result.Result;
import com.xingyuan.study.userservice.dto.req.UserRegisterReqDTO;
import com.xingyuan.study.userservice.dto.resp.UserQueryRespDTO;
import com.xingyuan.study.userservice.dto.resp.UserRegisterRespDTO;
import com.xingyuan.study.userservice.service.UserLoginService;
import com.xingyuan.study.userservice.service.UserService;
import com.xingyuan.study.web.Results;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xingyuan Huang
 * @since 2023/9/19 15:54
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserService userService;

    private final UserLoginService userLoginService;

    /**
     * search user information by username
     */
    @GetMapping("/api/user-service/query")
    public Result<UserQueryRespDTO> queryUserByUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userService.queryUserByUsername(username));
    }

    /**
     * check whether username exists
     */
    @GetMapping("/api/user-service/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userLoginService.hasUsername(username));
    }

    /**
     * user register
     */
    @PostMapping("/api/user-service/register")
    public Result<UserRegisterRespDTO> register(@RequestBody @Valid UserRegisterReqDTO requestParam) {
        return Results.success(userLoginService.register(requestParam));
    }
}
