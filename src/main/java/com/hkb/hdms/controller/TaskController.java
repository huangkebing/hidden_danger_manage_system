package com.hkb.hdms.controller;

import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/detailTask.html/{problemId}")
    public Object detailTaskPage(@PathVariable Long problemId){
        ModelAndView model = new ModelAndView();
        model.addObject("problemId",problemId);
        model.setViewName("task/detailTask");
        return model;
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

    @GetMapping("getDetailTask/{problemId}")
    @ResponseBody
    public Object getDetailTask(@PathVariable Long problemId){
        return taskService.getDetailTask(problemId);
    }

    @PostMapping("/completeTask")
    @ResponseBody
    public Object completeTask(String taskId){
        return taskService.completeTask(taskId);
    }

    @GetMapping("/historyTask")
    @ResponseBody
    public Object historyTask(int page, int limit){
        return null;
    }
}
