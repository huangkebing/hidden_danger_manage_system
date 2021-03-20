package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.mapper.RoleMenuMapper;
import com.hkb.hdms.model.pojo.RoleMenu;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.service.RoleService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/03/11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, UserRole> implements RoleService {

    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public RoleServiceImpl(RoleMenuMapper roleMenuMapper) {
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public Map<String, Object> getRoles(int limit, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","");

        if (page < 1){
            page = 1;
        }
        Page<UserRole> pageParam = new Page<>(page, limit);
        //分页无条件查询
        this.page(pageParam, null);

        map.put("count",pageParam.getTotal());
        map.put("data",pageParam.getRecords());
        return map;
    }

    @Override
    public R addRole(UserRole role) {
        if(StringUtils.isNullOrEmpty(role.getName())){
            return ReturnConstants.PARAMS_EMPTY;
        }
        if (this.save(role)) {
            return ReturnConstants.SUCCESS;
        } else{
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R updateRole(UserRole role) {
        if(StringUtils.isNullOrEmpty(role.getName())){
            return ReturnConstants.PARAMS_EMPTY;
        }
        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",role.getId());
        if (this.update(role,updateWrapper)) {
            return ReturnConstants.SUCCESS;
        } else{
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    @Transactional
    public R deleteRole(Long id) {
        //删除角色前，删除所有资源与该角色的绑定
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id",id));

        if (this.removeById(id)) {
            return ReturnConstants.SUCCESS;
        } else{
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    @Transactional
    public R roleToMenu(Long roleId, String menuIds) {
        if(menuIds == null){
            return ReturnConstants.PARAMS_EMPTY;
        }
        //获取现在的资源ids
        List<Long> newMenuIds = formatMenuIds(menuIds);

        //获取原本的资源ids
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        List<Long> oldMenuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        List<Long> addMenuList = newMenuIds.stream().filter((menuId) -> !oldMenuIds.contains(menuId)).collect(Collectors.toList());

        List<Long> deleteIds = roleMenus.stream()
                .filter(roleMenu -> !newMenuIds.contains(roleMenu.getMenuId()))
                .map(RoleMenu::getId)
                .collect(Collectors.toList());

        try {
            for (Long menuId : addMenuList) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuMapper.insert(roleMenu);
            }

            if(deleteIds.size() > 0){
                roleMenuMapper.deleteBatchIds(deleteIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new R(-1,e.getMessage());
        }
        return ReturnConstants.SUCCESS;
    }

    private List<Long> formatMenuIds(String menuIds){
        List<Long> res = new ArrayList<>();
        String[] strs = menuIds.split(",");
        for (String menuId : strs) {
            if("".equals(menuId)){
                continue;
            }
            res.add(Long.valueOf(menuId));
        }
        return res;
    }
}
