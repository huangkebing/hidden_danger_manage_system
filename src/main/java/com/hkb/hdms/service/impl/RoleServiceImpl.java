package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.service.RoleService;
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
}
