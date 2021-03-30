package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * hdms_type表实体类
 *
 * @author huangkebing
 * 2021/03/24
 */
@Data
@TableName("hdms_type")
public class Type {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String processId;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
