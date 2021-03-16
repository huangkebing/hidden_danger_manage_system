package com.hkb.hdms.utils;

import com.hkb.hdms.model.vo.MenuVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 把资源list转化为树形结构工具类
 *
 * @author huangkebing
 * 2021/03/16
 */
@Component
public class MenuTreeUtil {
    public List<MenuVo> toTree(List<MenuVo> treeList, Long pid) {
        List<MenuVo> retList = new ArrayList<>();
        for (MenuVo parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    private MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
        for (MenuVo child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
