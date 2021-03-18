package com.hkb.hdms;

import com.hkb.hdms.model.pojo.Menu;
import com.hkb.hdms.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {
        Menu menu = new Menu();
        menu.setHref("system/menu.html");
        menu.setIcon("fa fa-window-maximize");
        menu.setPid(7L);
        menu.setTitle("菜单管理");
        menu.setSort(110L);
        menu.setTarget("_self");
        menuService.addMenu(menu);

    }

}
