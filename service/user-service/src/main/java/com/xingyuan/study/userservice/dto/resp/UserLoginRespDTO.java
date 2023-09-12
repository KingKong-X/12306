package com.xingyuan.study.userservice.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 9:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRespDTO {
    private String userId;

    private String username;

    private String realName;

    private String accessToken;
}
