package com.xingyuan.study.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuan.study.database.base.BaseDO;
import lombok.*;

/**
 * @author Xingyuan Huang
 * @since 2023/9/22 19:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_deletion")
public class UserDeletionDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * type of certificate
     */
    private Integer idType;

    /**
     * id number
     */
    private String idCard;
}
