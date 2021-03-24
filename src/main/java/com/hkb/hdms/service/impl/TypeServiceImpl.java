package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.TypeMapper;
import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.service.TypeService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/24
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Override
    public Map<String, Object> getQuestion(int limit, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        Page<Type> pageParam = new Page<>(page, limit);
        this.page(pageParam, null);

        map.put("count", pageParam.getTotal());
        map.put("data", pageParam.getRecords());
        return map;
    }

    @Override
    public R addType(Type type) {
        if (StringUtils.isNullOrEmpty(type.getName())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        if (this.save(type)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R updateType(Type type) {
        if (StringUtils.isNullOrEmpty(type.getName())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        UpdateWrapper<Type> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", type.getId());
        if (this.update(type, updateWrapper)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R deleteType(Long id) {
        if (this.removeById(id)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }
}
