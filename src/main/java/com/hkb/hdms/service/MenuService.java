package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.model.pojo.Menu;

import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/16
 */
public interface MenuService extends IService<Menu> {
    Map<String, Object> initMenu();

    Map<String, Object> getMenu();

    BaseReturnDto addMenu(Menu menu);

    BaseReturnDto updateMenu(Menu menu);

    BaseReturnDto deleteMenu(Long id);
}
