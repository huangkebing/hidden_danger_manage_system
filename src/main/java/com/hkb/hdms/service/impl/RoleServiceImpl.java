package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.service.RoleService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, UserRole> implements RoleService {

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
    public R deleteRole(Long id) {
        if (this.removeById(id)) {
            return ReturnConstants.SUCCESS;
        } else{
            return ReturnConstants.FAILURE;
        }
    }
}
