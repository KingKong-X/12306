package com.xingyuan.study.userservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xingyuan.study.cache.DistributedCache;
import com.xingyuan.study.convention.exception.ClientException;
import com.xingyuan.study.convention.exception.ServiceException;
import com.xingyuan.study.user.core.UserInfoDTO;
import com.xingyuan.study.user.toolkit.JWTUtil;
import com.xingyuan.study.userservice.dao.entity.UserDO;
import com.xingyuan.study.userservice.dao.entity.UserMailDO;
import com.xingyuan.study.userservice.dao.entity.UserPhoneDO;
import com.xingyuan.study.userservice.dao.mapper.UserMailMapper;
import com.xingyuan.study.userservice.dao.mapper.UserMapper;
import com.xingyuan.study.userservice.dao.mapper.UserPhoneMapper;
import com.xingyuan.study.userservice.dto.req.UserLoginReqDTO;
import com.xingyuan.study.userservice.dto.resp.UserLoginRespDTO;
import com.xingyuan.study.userservice.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 15:05
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {
    private UserMailMapper userMailMapper;

    private UserPhoneMapper userPhoneMapper;

    private UserMapper userMapper;

    private DistributedCache distributedCache;

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        String usernameOrMailOrPhone = requestParam.getUsernameOrMailOrPhone();
        boolean mailFlag = usernameOrMailOrPhone.contains("@");
        String username;
        if (mailFlag) {
            LambdaQueryWrapper<UserMailDO> queryWrapper = new LambdaQueryWrapper<>(UserMailDO.class)
                    .eq(UserMailDO::getMail, usernameOrMailOrPhone);
            username = Optional.ofNullable(userMailMapper.selectOne(queryWrapper))
                    .map(UserMailDO::getUsername)
                    .orElseThrow(() -> new ClientException("用户名/手机号/邮箱不存在"));
        } else {
            LambdaQueryWrapper<UserPhoneDO> queryWrapper = new LambdaQueryWrapper<>(UserPhoneDO.class)
                    .eq(UserPhoneDO::getPhone, usernameOrMailOrPhone);
            username = Optional.ofNullable(userPhoneMapper.selectOne(queryWrapper))
                    .map(UserPhoneDO::getPhone)
                    .orElse(null);
        }
        username = Optional.ofNullable(username).orElse(requestParam.getUsernameOrMailOrPhone());
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getPassword, requestParam.getPassword())
                .select(UserDO::getId, UserDO::getUsername, UserDO::getRealName);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO != null) {
            UserInfoDTO userInfo = UserInfoDTO.builder()
                    .userId(String.valueOf(userDO.getId()))
                    .username(userDO.getUsername())
                    .realName(userDO.getRealName())
                    .build();
            String token = JWTUtil.getAccessToken(userInfo);
            UserLoginRespDTO userLoginRespDTO = new UserLoginRespDTO(userInfo.getUserId(), userInfo.getUsername(), userInfo.getRealName(), token);
            distributedCache.put(token, JSON.toJSONString(userLoginRespDTO), 30, TimeUnit.MINUTES);
            return userLoginRespDTO;
        }
        throw new ServiceException("账号不存在或密码错误");
    }

    @Override
    public UserLoginRespDTO checkLogin(String accessToken) {
        return distributedCache.get(accessToken, UserLoginRespDTO.class);
    }

    @Override
    public void logout(String accessToken) {
        if (StrUtil.isNotBlank(accessToken)) {
            distributedCache.delete(accessToken);
        }
    }
}
