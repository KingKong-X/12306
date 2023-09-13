package com.xingyuan.study.user.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 20:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {
    private String userId;

    private String username;

    private String realName;

    private String token;
}
