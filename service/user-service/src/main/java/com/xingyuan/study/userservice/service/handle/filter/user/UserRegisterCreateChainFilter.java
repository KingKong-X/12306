package com.xingyuan.study.userservice.service.handle.filter.user;

import com.xingyuan.designpattern.chian.AbstractChainHandler;
import com.xingyuan.study.userservice.dto.req.UserRegisterReqDTO;
import com.xingyuan.study.userservice.enums.UserChainMarkEnum;

/**
 * @author Xingyuan Huang
 * @since 2023/9/22 9:01
 */
public interface UserRegisterCreateChainFilter<T extends UserRegisterReqDTO> extends AbstractChainHandler<UserRegisterReqDTO> {
    @Override
    default String mark() {
        return UserChainMarkEnum.USER_REGISTER_FILTER.name();
    }
}
