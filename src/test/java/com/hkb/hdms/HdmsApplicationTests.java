package com.hkb.hdms;

import com.alibaba.fastjson.JSON;
import com.hkb.hdms.mapper.RoleMenuMapper;
import com.hkb.hdms.model.pojo.Menu;
import com.hkb.hdms.model.pojo.RoleMenu;
import com.hkb.hdms.model.vo.MenuTreeVo;
import com.hkb.hdms.service.MenuService;
import com.hkb.hdms.service.RoleService;
import com.hkb.hdms.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private SysUserService sysUserService;

    @Test
    void contextLoads() {
        Map<String, Object> user = sysUserService.getUser(15, 1);
        System.out.println(JSON.toJSONString(user));

    }

}
