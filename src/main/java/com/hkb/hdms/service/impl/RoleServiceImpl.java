package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author huangkebing
 * 2021/03/11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, UserRole> implements RoleService {

}
