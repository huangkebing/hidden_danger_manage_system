package com.hkb.hdms.controller;

import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 隐患相关接口
 *
 * @author huangkebing
 * 2021/03/24
 */
@Controller
@RequestMapping("/question")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @RequestMapping("/question.html")
    public Object questionPage(){
        return "question/question";
    }

    /**
     * 获取隐患类型接口，分页查询
     */
    @GetMapping("/getQuestion")
    @ResponseBody
    public Object getQuestion(int limit, int page) {
        return typeService.getQuestion(limit, page);
    }

    @PostMapping("/addQuestion")
    @ResponseBody
    public Object addQuestion(Type type){
        return typeService.addType(type);
    }

    @PostMapping("/updateQuestion")
    @ResponseBody
    public Object updateQuestion(Type type){
        return typeService.updateType(type);
    }

    @PostMapping("/deleteQuestion")
    @ResponseBody
    public Object deleteQuestion(Long id){
        return typeService.deleteType(id);
    }
}
