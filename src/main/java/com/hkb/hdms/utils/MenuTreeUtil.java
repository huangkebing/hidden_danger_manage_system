package com.hkb.hdms.utils;

import com.hkb.hdms.model.vo.MenuTreeVo;
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

    public List<MenuTreeVo> toTree(List<MenuTreeVo> treeList, Long pid, List<Long> ownMenus) {
        List<MenuTreeVo> retList = new ArrayList<>();
        for (MenuTreeVo parent : treeList) {
            //如果该资源和角色已绑定，check置为true
            if (ownMenus.contains(parent.getId())) {
                parent.setChecked(true);
            }

            if (pid.equals(parent.getPid())) {
                //最外一层默认展开
                parent.setSpread(true);
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    private MenuTreeVo findChildren(MenuTreeVo parent, List<MenuTreeVo> treeList) {
        for (MenuTreeVo child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
