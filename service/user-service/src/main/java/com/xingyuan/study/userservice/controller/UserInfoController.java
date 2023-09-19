package com.xingyuan.study.userservice.controller;

import com.xingyuan.study.convention.result.Result;
import com.xingyuan.study.userservice.dto.resp.UserQueryRespDTO;
import com.xingyuan.study.userservice.service.UserService;
import com.xingyuan.study.web.Results;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xingyuan Huang
 * @since 2023/9/19 15:54
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserService userService;

    /**
     * search user information by username
     */
    @GetMapping("/api/user-service/query")
    public Result<UserQueryRespDTO> queryUserByUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userService.queryUserByUsername(username));
    }
}
