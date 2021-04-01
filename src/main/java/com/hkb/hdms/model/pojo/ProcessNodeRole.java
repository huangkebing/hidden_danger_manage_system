package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 流程节点与角色绑定表pojo
 *
 * @author huangkebing
 * 2021/04/01
 */
@TableName("hdms_process_node_role")
@Data
public class ProcessNodeRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("node_id")
    private String nodeId;

    @TableField("process_id")
    private String processId;

    private String name;

    @TableField("role_id")
    private Long roleId;

    @TableField("role_name")
    private String roleName;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
