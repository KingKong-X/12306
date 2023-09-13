package com.xingyuan.study.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuan.study.database.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 20:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user")
public class UserDO extends BaseDO {
    private Long id;

    private String username;

    private String password;

    private String realName;

    private String region;

    private Integer idType;

    private String idCard;

    private String phone;

    private String telephone;

    private String mail;

    private Integer userType;

    private Integer verifyStatus;

    private String postCode;

    private String address;

    private Long deletionTime;
}
