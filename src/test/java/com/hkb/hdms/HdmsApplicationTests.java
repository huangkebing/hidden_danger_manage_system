package com.hkb.hdms;

import com.alibaba.fastjson.JSON;
import com.hkb.hdms.mapper.RoleMenuMapper;
import com.hkb.hdms.model.pojo.Menu;
import com.hkb.hdms.model.pojo.RoleMenu;
import com.hkb.hdms.model.vo.MenuTreeVo;
import com.hkb.hdms.service.MenuService;
import com.hkb.hdms.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {
        List<MenuTreeVo> menuWithRole = menuService.getMenuWithRole(5L);
        System.out.println(JSON.toJSONString(menuWithRole));

    }

}
