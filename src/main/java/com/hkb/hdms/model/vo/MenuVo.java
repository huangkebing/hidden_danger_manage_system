package com.hkb.hdms.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 系统菜单资源vo
 *
 * @author huangkebing
 * 2021/03/16
 */
@Data
public class MenuVo {
    private Long id;

    private Long pid;

    private String title;

    private String icon;

    private String href;

    private String target;

    private List<MenuVo> child;
}
