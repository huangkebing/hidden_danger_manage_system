package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * hdms_problem 表pojo
 *
 * @author huangkebing
 * 2021/04/03
 */
@TableName("hdms_problem")
@Data
public class Problem {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Integer priority;

    @TableField("type_id")
    private Long typeId;

    @TableField("instance_id")
    private String instanceId;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
