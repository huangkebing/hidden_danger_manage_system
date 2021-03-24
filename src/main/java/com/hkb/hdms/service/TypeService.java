package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.model.pojo.Type;

import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/24
 */
public interface TypeService extends IService<Type> {
    Map<String, Object> getQuestion(int limit, int page);
}
