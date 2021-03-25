package com.hkb.hdms.model.vo;

import lombok.Data;

/**
 * 隐患类型分类前端渲染复选框vo
 *
 * @author huangkebing
 * 2021/03/25
 */
@Data
public class TypeVo {

    private Long id;

    private String name;

    private boolean checked;
}
