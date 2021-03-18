package com.hkb.hdms;

import com.hkb.hdms.mapper.RoleMenuMapper;
import com.hkb.hdms.model.pojo.Menu;
import com.hkb.hdms.model.pojo.RoleMenu;
import com.hkb.hdms.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Test
    void contextLoads() {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(5L);
        roleMenu.setMenuId(22L);
        roleMenuMapper.insert(roleMenu);

    }

}
