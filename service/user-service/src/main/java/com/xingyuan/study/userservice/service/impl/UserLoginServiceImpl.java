package com.xingyuan.study.userservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xingyuan.designpattern.chian.AbstractChainContext;
import com.xingyuan.study.cache.DistributedCache;
import com.xingyuan.study.common.toolkit.BeanUtil;
import com.xingyuan.study.convention.exception.ClientException;
import com.xingyuan.study.convention.exception.ServiceException;
import com.xingyuan.study.user.core.UserInfoDTO;
import com.xingyuan.study.user.toolkit.JWTUtil;
import com.xingyuan.study.userservice.dao.entity.UserDO;
import com.xingyuan.study.userservice.dao.entity.UserMailDO;
import com.xingyuan.study.userservice.dao.entity.UserPhoneDO;
import com.xingyuan.study.userservice.dao.entity.UserReuseDO;
import com.xingyuan.study.userservice.dao.mapper.UserMailMapper;
import com.xingyuan.study.userservice.dao.mapper.UserMapper;
import com.xingyuan.study.userservice.dao.mapper.UserPhoneMapper;
import com.xingyuan.study.userservice.dao.mapper.UserReuseMapper;
import com.xingyuan.study.userservice.dto.req.UserLoginReqDTO;
import com.xingyuan.study.userservice.dto.req.UserRegisterReqDTO;
import com.xingyuan.study.userservice.dto.resp.UserLoginRespDTO;
import com.xingyuan.study.userservice.dto.resp.UserRegisterRespDTO;
import com.xingyuan.study.userservice.enums.UserChainMarkEnum;
import com.xingyuan.study.userservice.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.xingyuan.study.userservice.constant.RedisKeyConstant.LOCK_USER_REGISTER;
import static com.xingyuan.study.userservice.constant.RedisKeyConstant.USER_REGISTER_REUSE_SHARDING;
import static com.xingyuan.study.userservice.enums.UserRegisterErrorCodeEnum.*;
import static com.xingyuan.study.userservice.toolkit.UserReuseUtil.hasShardingIdx;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 15:05
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {
    private final UserMailMapper userMailMapper;

    private final UserPhoneMapper userPhoneMapper;

    private final UserMapper userMapper;

    private final UserReuseMapper userReuseMapper;

    private final DistributedCache distributedCache;

    private final RBloomFilter<String> userRegisterPenetrationBloomFilter;

    private final StringRedisTemplate stringRedisTemplate;

    private final AbstractChainContext<UserRegisterReqDTO> abstractChainContext;

    private final RedissonClient redissonClient;

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

    @Override
    public Boolean hasUsername(String username) {
        boolean hasUsername = userRegisterPenetrationBloomFilter.contains(username);
        if (hasUsername) {
            return Boolean.FALSE.equals(stringRedisTemplate.opsForSet().isMember(USER_REGISTER_REUSE_SHARDING + hasShardingIdx(username), username));
        }
        return true;
    }

    @Transactional
    @Override
    public UserRegisterRespDTO register(UserRegisterReqDTO requestParam) {
        abstractChainContext.handler(UserChainMarkEnum.USER_REGISTER_FILTER.name(), requestParam);
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER + requestParam.getUsername());
        boolean tryLock = lock.tryLock();
        try {
            if (!tryLock) {
                throw new ServiceException(HAS_USERNAME_NOTNULL);
            }
            // t_user
            try {
                int inserted = userMapper.insert(BeanUtil.convert(requestParam, UserDO.class));
                if (inserted < 1) {
                    throw new ServiceException(USER_REGISTER_FAIL);
                }
            } catch (DuplicateKeyException duplicateKeyException) {
                log.error("用户名 [{}] 重复注册", requestParam.getUsername());
                throw new ServiceException(HAS_USERNAME_NOTNULL);
            }
            UserPhoneDO userPhoneDO = UserPhoneDO.builder()
                    .phone(requestParam.getPhone())
                    .username(requestParam.getUsername())
                    .build();
            // t_user_phone
            try {
                userPhoneMapper.insert(userPhoneDO);
            } catch (DuplicateKeyException duplicateKeyException) {
                log.error("用户 [{}] 注册手机号 [{}] 重复", requestParam.getUsername(), requestParam.getPhone());
                throw new ServiceException(PHONE_REGISTERED);
            }
            // t_user_mail
            if (StrUtil.isNotBlank(requestParam.getMail())) {
                UserMailDO userMailDO = UserMailDO.builder()
                        .mail(requestParam.getMail())
                        .username(requestParam.getUsername())
                        .build();
                try {
                    userMailMapper.insert(userMailDO);
                } catch (DuplicateKeyException duplicateKeyException) {
                    log.error("用户 [{}] 注册邮箱 [{}] 重复", requestParam.getUsername(), requestParam.getMail());
                    throw new ServiceException(MAIL_REGISTERED);
                }
            }
            String username = requestParam.getUsername();
            userReuseMapper.delete(Wrappers.update(new UserReuseDO(username)));
            stringRedisTemplate.opsForSet().remove(USER_REGISTER_REUSE_SHARDING + hasShardingIdx(username), username);
            userRegisterPenetrationBloomFilter.add(username);
        } finally {
            lock.unlock();
        }
        return BeanUtil.convert(requestParam ,UserRegisterRespDTO.class);
    }
}
