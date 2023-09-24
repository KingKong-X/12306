package com.xingyuan.study.userservice.dto.resp;

import lombok.Data;

/**
 * @author Xingyuan Huang
 * @since 2023/9/20 20:47
 */
@Data
public class UserRegisterRespDTO {
    private String name;

    private String realName;

    private String phone;
}
