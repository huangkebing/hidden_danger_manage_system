package com.hkb.hdms.controller;

import com.hkb.hdms.base.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/04/17
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public IndexController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping("/adminIndex.html")
    public Object adminIndexPage() {
        return "adminIndex";
    }

    @RequestMapping("/otherIndex.html")
    public Object otherIndexPage() {
        return "otherIndex";
    }

    @GetMapping("/getResent/{page}/{limit}")
    @ResponseBody
    public Object getResent(@PathVariable long limit, @PathVariable long page){
        Map<String, Object> map = new HashMap<>();
        map.put("data",redisTemplate.opsForZSet().reverseRange(Constants.REDIS_KEY, (page - 1) * limit, page * limit - 1));
        map.put("count", redisTemplate.opsForZSet().zCard(Constants.REDIS_KEY));
        return map;
    }
}
