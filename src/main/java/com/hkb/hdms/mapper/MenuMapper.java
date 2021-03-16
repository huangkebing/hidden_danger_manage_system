package com.hkb.hdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkb.hdms.model.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统资源mapper
 *
 * @author huangkebing
 * 2021/03/16
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色id查可访问的资源
     *
     * @param roleId 角色id
     */
    List<Menu> selectByRole(int roleId);
}
