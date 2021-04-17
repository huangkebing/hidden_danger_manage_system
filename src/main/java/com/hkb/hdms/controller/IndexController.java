package com.hkb.hdms.controller;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.mapper.ProblemMapper;
import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.dto.IndexDto;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/04/17
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    private final RedisTemplate<String,Object> redisTemplate;

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final ProblemMapper problemMapper;

    private final RepositoryService repositoryService;

    @Autowired
    public IndexController(RedisTemplate<String, Object> redisTemplate, UserMapper userMapper, RoleMapper roleMapper, ProblemMapper problemMapper, RepositoryService repositoryService) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.problemMapper = problemMapper;
        this.repositoryService = repositoryService;
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

    @GetMapping("/adminIndexNumbers")
    @ResponseBody
    public Object adminIndexNumbers(){
        Map<String, Object> map = new HashMap<>();
        map.put("userNumber", userMapper.selectCount(null));
        map.put("roleNumber", roleMapper.selectCount(null));
        map.put("problemNumber", problemMapper.selectCount(null));
        map.put("processNumber", repositoryService.createProcessDefinitionQuery().count());
        return map;
    }

    @GetMapping("/countByType")
    @ResponseBody
    public Object countByType(){
        Map<String, Object> map = new HashMap<>();
        List<IndexDto> indexDtos = problemMapper.countByType();
        List<String> names = indexDtos.stream().map(IndexDto::getName).collect(Collectors.toList());
        map.put("legend", names);
        map.put("series", indexDtos);
        return map;
    }

    @GetMapping("/countByTypeAndWeekDay")
    @ResponseBody
    public Object countByTypeAndWeekDay(){
        Map<String, Object> map = new HashMap<>();
        List<IndexDto> indexDtos = problemMapper.countByTypeAndWeekDay();
        List<String> names = indexDtos.stream().map(IndexDto::getName).collect(Collectors.toList());
        map.put("legend", names);
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, List<IndexDto>> groups = indexDtos.stream().collect(Collectors.groupingBy(IndexDto::getName));
        for (String group : groups.keySet()) {
            Map<String, Object> nameMap = new HashMap<>();
            List<IndexDto> dtoList = groups.get(group);
            nameMap.put("name", group);
            nameMap.put("type", "line");
            int[] counts = new int[7];
            for (IndexDto indexDto : dtoList) {
                counts[indexDto.getWeekday()] = indexDto.getValue();
            }
            nameMap.put("data", counts);
            data.add(nameMap);
        }
        map.put("series", data);
        return map;
    }
}
