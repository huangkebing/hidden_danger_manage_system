package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.MenuMapper;
import com.hkb.hdms.mapper.RoleMenuMapper;
import com.hkb.hdms.model.pojo.Menu;
import com.hkb.hdms.model.pojo.RoleMenu;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.model.vo.MenuTreeVo;
import com.hkb.hdms.model.vo.MenuVo;
import com.hkb.hdms.service.MenuService;
import com.hkb.hdms.utils.MenuTreeUtil;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/03/16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;

    private final HttpSession session;

    private final MenuTreeUtil treeUtil;
    
    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, HttpSession session, MenuTreeUtil treeUtil, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.session = session;
        this.treeUtil = treeUtil;
        this.roleMenuMapper = roleMenuMapper;
    }

    /**
     * 根据登录的用户角色动态生成资源
     */
    @Override
    public Map<String, Object> initMenu() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> home = new HashMap<>();
        Map<String, Object> logo = new HashMap<>();

        //获取当前登录的用户
        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);

        List<Menu> menus = menuMapper.selectByRole(loginUser.getRole());
        List<MenuVo> menuVos = new ArrayList<>();

        for (Menu menu : menus) {
            MenuVo menuVo = new MenuVo();
            menuVo.setId(menu.getId());
            menuVo.setPid(menu.getPid());
            menuVo.setHref(menu.getHref());
            menuVo.setTitle(menu.getTitle());
            menuVo.setIcon(menu.getIcon());
            menuVo.setTarget(menu.getTarget());
            menuVos.add(menuVo);
        }

        map.put("menuInfo", treeUtil.toTree(menuVos, 0L));

        home.put("title", "首页");
        //判断系统管理员
        if (loginUser.getRole() == Constants.ADMIN_ID) {
            home.put("href", Constants.ADMIN_INDEX);
        } else {
            home.put("href", Constants.OTHER_INDEX);
        }

        logo.put("title", "HDMS");
        logo.put("image", Constants.LOGO_PATH);
        logo.put("href", "");

        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMenu() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        Map<String, Object> menuData = getMenuData();
        map.put("count", menuData.get("count"));
        ArrayList<MenuVo> menuVos = (ArrayList<MenuVo>) menuData.get("data");
        map.put("data", dataFormat(menuVos));
        return map;
    }

    @Override
    public R addMenu(Menu menu) {
        if (StringUtils.isNullOrEmpty(menu.getTitle())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        if (this.save(menu)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R updateMenu(Menu menu) {
        if (StringUtils.isNullOrEmpty(menu.getTitle())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        UpdateWrapper<Menu> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",menu.getId());
        if (this.update(menu,wrapper)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R deleteMenu(Long id) {
        if (this.removeById(id)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public List<MenuTreeVo> getMenuWithRole(Long roleId) {
        //查询出所有的资源
        List<Menu> menus = this.list(new QueryWrapper<Menu>().orderByAsc("sort"));
        
        //查询出roleId下绑定的资源
        List<RoleMenu> ownMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id", roleId));

        //过滤出roleId下的资源id
        List<Long> ownMenuIds = ownMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        List<MenuTreeVo> menuTreeVos = new ArrayList<>();

        for (Menu menu : menus) {
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setId(menu.getId());
            menuTreeVo.setPid(menu.getPid());
            menuTreeVo.setTitle(menu.getTitle());
            menuTreeVos.add(menuTreeVo);
        }

        return treeUtil.toTree(menuTreeVos, 0L, ownMenuIds);
    }

    /**
     * 获取所有资源原数据
     */
    private Map<String, Object> getMenuData() {
        Map<String, Object> map = new HashMap<>();

        List<Menu> menus = this.list(new QueryWrapper<Menu>().orderByAsc("sort"));

        map.put("count", menus.size());
        List<MenuVo> menuVos = new ArrayList<>();

        for (Menu menu : menus) {
            MenuVo menuVo = new MenuVo();
            menuVo.setId(menu.getId());
            menuVo.setPid(menu.getPid());
            menuVo.setHref(menu.getHref());
            menuVo.setTitle(menu.getTitle());
            menuVo.setIcon(menu.getIcon());
            menuVo.setTarget(menu.getTarget());
            menuVo.setSort(menu.getSort());
            menuVos.add(menuVo);
        }

        map.put("data", treeUtil.toTree(menuVos, 0L));
        return map;
    }

    /**
     * 数据格式转化，把原数据转化为页面渲染需要的格式
     */
    private ArrayList<Map<String, Object>> dataFormat(List<MenuVo> menuVos) {
        ArrayList<Map<String, Object>> menuList = new ArrayList<>();
        for (MenuVo menuVo : menuVos) {
            HashMap<String, Object> menu = new HashMap<>();
            menu.put("id", menuVo.getId());
            menu.put("title", menuVo.getTitle());
            menu.put("sort", menuVo.getSort());
            menu.put("href", menuVo.getHref());
            menu.put("icon", menuVo.getIcon());
            if (menuVo.getPid() == 0) {
                menu.put("pid", -1);
            } else {
                menu.put("pid", menuVo.getPid());
            }
            menu.put("target", menuVo.getTarget());
            menuList.add(menu);

            if (!ObjectUtils.isEmpty(menuVo.getChild())) {
                menuList.addAll(dataFormat(menuVo.getChild()));
            }
        }
        return menuList;
    }

}
