package com.xingyuan.study.user.toolkit;

import com.alibaba.fastjson2.JSON;
import com.xingyuan.study.user.core.UserInfoDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.xingyuan.study.base.constant.UserConstant.*;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 21:06
 */
@Slf4j
public class JWTUtil {
    public static final long EXPIRATION = 86400L;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String ISSUER = "xingyuan";

    public static final String SECRET = "xingyuan";
    /**
     * generate user token
     *
     * @param userInfo user information
     * @return token
     */
    public static String getAccessToken(UserInfoDTO userInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID_KEY, userInfo.getUserId());
        map.put(USER_NAME_KEY, userInfo.getUsername());
        map.put(REAL_NAME_KEY, userInfo.getRealName());
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISSUER)
                .setSubject(JSON.toJSONString(map))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
        return TOKEN_PREFIX + token;
    }
}
