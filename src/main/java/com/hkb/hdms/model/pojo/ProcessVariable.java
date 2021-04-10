package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * hdms_process_variable表实体类
 *
 * @author huangkebing
 * 2021/04/10
 */
@TableName("hdms_process_variable")
@Data
public class ProcessVariable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("node_id")
    private String nodeId;

    @TableField("node_name")
    private String nodeName;

    @TableField("process_id")
    private String processId;

    private String name;

    private Integer type;

    private String title;

    private String tip0;

    private String tip1;

    @TableField("begin_variable")
    private Integer beginVariable;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
