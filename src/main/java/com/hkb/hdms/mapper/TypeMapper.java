package com.hkb.hdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.model.vo.QueryTypeVo;
import com.hkb.hdms.model.vo.TypeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangkebing
 * 2021/03/24
 */
@Repository
public interface TypeMapper extends BaseMapper<Type> {
    List<QueryTypeVo> selectTypes(Page<QueryTypeVo> page);

    List<TypeVo> selectTypesWithUser(Long userId);

    List<TypeVo> selectAll();
}
