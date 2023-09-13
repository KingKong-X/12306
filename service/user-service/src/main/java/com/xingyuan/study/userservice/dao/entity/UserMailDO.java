package com.xingyuan.study.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuan.study.database.base.BaseDO;
import lombok.*;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 15:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user_mail")
public class UserMailDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * mail
     */
    private String mail;

    /**
     * deletion time
     */
    private Long deletionTime;
}
