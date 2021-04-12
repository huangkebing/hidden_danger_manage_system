package com.hkb.hdms.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author huangkebing
 * 2021/04/12
 */
@Data
public class ProblemDto {
    private Long id;

    private String name;

    private String description;

    private Integer priority;

    private Long typeId;

    private String typeName;

    private String instanceId;

    private Long userId;

    private String email;

    private String username;

    private Date create;

    private Date modify;
}
