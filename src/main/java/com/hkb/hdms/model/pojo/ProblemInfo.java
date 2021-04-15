package com.hkb.hdms.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author huangkebing
 * 2021/04/12
 */
@TableName("hdms_problem_info")
@Data
public class ProblemInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("problem_id")
    private Long problemId;

    @TableField("`context`")
    private String context;

    private Integer type;

    @TableField("user_id")
    private Long userId;

    private String username;

    private String email;

    @TableField("file_path")
    private String filePath;

    @TableField(value = "`create`", fill = FieldFill.INSERT)
    private Date create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modify;
}
