package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * hdms_system_menu实体类
 *
 * @author huangkebing
 * 2021/03/16
 */
@Data
@TableName("hdms_system_menu")
public class Menu {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long pid;

    private String title;

    private String icon;

    private String href;

    private String target;

    private Long sort;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
