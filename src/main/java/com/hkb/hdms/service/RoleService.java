package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.R;
import com.hkb.hdms.model.pojo.UserRole;
import org.springframework.stereotype.Repository;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/11
 */
@Repository
public interface RoleService extends IService<UserRole> {
    Map<String,Object> getRoles(int limit,int page);

    R addRole(UserRole role);

    R updateRole(UserRole role);

    R deleteRole(Long id);

    R roleToMenu(Long roleId, String menuIds);
}
