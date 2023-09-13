package com.xingyuan.study.database.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author Xingyuan Huang
 * @since 2023/9/12 15:12
 */
@Data
public class BaseDO {
    /**
     * create time
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * update time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * logic delete flag
     */
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;
}
