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
        System.out.println(System.getProperty("user.dir"));
    }

}
