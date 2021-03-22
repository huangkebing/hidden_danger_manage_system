package com.hkb.hdms;

import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private SysUserService sysUserService;

    @Test
    void contextLoads() {
        User user = new User();
        user.setEmail("huangkebing2@163.com");
        user.setRole(5);
        sysUserService.addUser(user);
    }

}
