package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private int id;
    private String email;
    private String name;
    private String password;
    private int role;
    @TableField("`group`")
    private String group;
    @TableField("`create`")
    private Date create;
    private Date modify;
}
