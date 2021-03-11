package com.hkb.hdms;

import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.service.RoleService;
import com.hkb.hdms.utils.ValidateCodeMailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private RoleMapper mapper;

    @Test
    void contextLoads() {
        UserRole role = new UserRole();
        role.setName("ADMIN");
        role.setDescription("系统管理员");
        mapper.insert(role);
    }

}
