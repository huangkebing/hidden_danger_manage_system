package com.hkb.hdms;

import com.alibaba.fastjson.JSON;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.model.pojo.ProblemObserver;
import com.hkb.hdms.utils.NoticeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class HdmsApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private NoticeUtil noticeUtil;
    @Test
    void contextLoads() {
        List<Object> range = redisTemplate.opsForList().range(Constants.REDIS_KEY, 0, 0);
        System.out.println(JSON.toJSONString(range.get(0)));
    }
}
