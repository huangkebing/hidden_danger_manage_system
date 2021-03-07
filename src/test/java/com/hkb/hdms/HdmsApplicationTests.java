package com.hkb.hdms;

import com.hkb.hdms.utils.ValidateCodeMailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private ValidateCodeMailSender sender;
    @Test
    void contextLoads() {
        sender.sendMail("986574", "1216033824@qq.com");
    }

}
