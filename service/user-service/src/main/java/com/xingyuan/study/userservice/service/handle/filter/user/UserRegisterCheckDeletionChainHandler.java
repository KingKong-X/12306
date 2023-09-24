package com.xingyuan.study.userservice.service.handle.filter.user;

import com.xingyuan.study.convention.exception.ClientException;
import com.xingyuan.study.userservice.dto.req.UserRegisterReqDTO;
import com.xingyuan.study.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Xingyuan Huang
 * @since 2023/9/22 9:28
 */
@Component
@RequiredArgsConstructor
public class UserRegisterCheckDeletionChainHandler implements UserRegisterCreateChainFilter<UserRegisterReqDTO> {
    private final UserService userService;

    @Override
    public void handler(UserRegisterReqDTO requestParam) {
        if (userService.queryUserDeletionNum(requestParam.getIdType(), requestParam.getIdCard()) >= 5) {
            throw new ClientException("证件号多次注销账号已被加入黑名单");
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
