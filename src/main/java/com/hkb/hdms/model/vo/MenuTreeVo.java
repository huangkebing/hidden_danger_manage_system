package com.hkb.hdms.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 渲染资源成树形结构使用类
 *
 * @author huangkebing
 * 2021/03/19
 */
@Data
public class MenuTreeVo {
    private Long id;

    private Long pid;

    private String title;

    //是否默认展开
    private boolean spread;

    //是否默认选中
    private boolean checked;

    private List<MenuTreeVo> children;
}
