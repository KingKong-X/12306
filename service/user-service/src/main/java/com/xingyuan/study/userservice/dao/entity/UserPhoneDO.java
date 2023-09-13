package com.xingyuan.study.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuan.study.database.base.BaseDO;
import lombok.*;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 19:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user_phone")
public class UserPhoneDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * phone
     */
    private String phone;

    /**
     * deletion time
     */
    private Long deletionTime;
}
