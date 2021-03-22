package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * User表实体类
 *
 * @author huangkebing
 * 2021/03/08
 */
@Data
@TableName("hdms_user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String email;

    private String name;

    private String password;

    private Integer role;

    private Integer live;

    private String mobile;

    private String remark;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
