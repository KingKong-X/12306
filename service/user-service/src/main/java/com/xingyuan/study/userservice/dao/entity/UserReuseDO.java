package com.xingyuan.study.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuan.study.database.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Xingyuan Huang
 * @since 2023/9/24 14:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_reuse")
public class UserReuseDO extends BaseDO {
    private String username;
}
