package com.hkb.hdms;

import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.ProcessService;
import com.hkb.hdms.service.SysUserService;
import com.hkb.hdms.service.TaskService;
import com.sun.el.ExpressionFactoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.el.ValueExpression;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private ProcessService processService;

    @Test
    void contextLoads() {
        processService.test();
    }

    @Test
    void test(){
        String a = "${value == 1 && name != null}";

        String b = "[a-zA-Z_][a-zA-Z0-9_]* *==|!=";
        Pattern compile = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");

        Matcher matcher = compile.matcher(a);
        while(matcher.find()){
            System.out.println(matcher.group());
        }


    }
}
