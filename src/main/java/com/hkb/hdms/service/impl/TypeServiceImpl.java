package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.mapper.TypeMapper;
import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.service.TypeService;
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
}
