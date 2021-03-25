package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.R;
import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.model.vo.TypeVo;

import java.util.List;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/24
 */
public interface TypeService extends IService<Type> {
    Map<String, Object> getQuestion(int limit, int page);

    R addType(Type type);

    R updateType(Type type);

    R deleteType(Long id);

    List<TypeVo> getQuestionWithUserId(Long userId);
}
