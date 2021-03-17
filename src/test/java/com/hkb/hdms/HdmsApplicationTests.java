package com.hkb.hdms;

import com.alibaba.fastjson.JSON;
import com.hkb.hdms.mapper.MenuMapper;
import com.hkb.hdms.mapper.RoleMenuMapper;
import com.hkb.hdms.model.pojo.Menu;
import com.hkb.hdms.model.pojo.RoleMenu;
import com.hkb.hdms.service.MenuService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.Role;
import java.util.Map;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {
        Map<String, Object> menu = menuService.getMenu();
        System.out.println(JSON.toJSONString(menu));
    }

}
