package com.xingyuan.study.userservice.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 9:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginReqDTO {
    private String usernameOrMailOrPhone;

    private String password;
}
