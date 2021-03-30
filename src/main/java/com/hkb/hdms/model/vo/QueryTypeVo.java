package com.hkb.hdms.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangkebing
 * 2021/03/30
 */
@Data
public class QueryTypeVo {
    private Long id;

    private String name;

    private String description;

    private String processId;

    private String processName;

    private String processVersion;

    private String processKey;

    private Date create;

    private Date modify;
}
