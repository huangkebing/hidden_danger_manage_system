package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author huangkebing
 * 2021/05/23
 */
@Data
@TableName("hdms_task_handler")
public class TaskHandler {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("instance_id")
    private String instanceId;

    @TableField("task_id")
    private String taskId;

    private String email;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
