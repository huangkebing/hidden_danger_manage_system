package com.hkb.hdms.controller;

import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 流程实例相关接口
 *
 * @author huangkebing
 * 2021/04/02
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/createTask.html")
    public Object createTaskPage(){
        return "task/createTask";
    }

    @RequestMapping("/detailTask.html")
    public Object detailTaskPage(){
        return "task/detailTask";
    }

    @PostMapping("/createTask")
    @ResponseBody
    public Object createTask(Problem problem){
        return taskService.createTask(problem);
    }

    @GetMapping("/getMyTask")
    @ResponseBody
    public Object getMyTask(int page, int limit){
        return taskService.getMyTask(page, limit);
    }
}
