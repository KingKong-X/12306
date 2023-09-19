package com.xingyuan.study.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xingyuan.study.common.toolkit.BeanUtil;
import com.xingyuan.study.convention.exception.ClientException;
import com.xingyuan.study.userservice.dao.entity.UserDO;
import com.xingyuan.study.userservice.dao.mapper.UserMapper;
import com.xingyuan.study.userservice.dto.resp.UserQueryRespDTO;
import com.xingyuan.study.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Xingyuan Huang
 * @since 2023/9/19 16:04
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public UserQueryRespDTO queryUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> lambdaQueryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = userMapper.selectOne(lambdaQueryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在，请检查用户名是否正确");
        }
        return BeanUtil.convert(userDO, UserQueryRespDTO.class);
    }
}
