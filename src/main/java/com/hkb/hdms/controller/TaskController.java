package com.hkb.hdms.controller;

import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

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

    @RequestMapping("/historyTask.html")
    public Object historyTaskPage(){
        return "task/historyTask";
    }

    @RequestMapping("/solveingTask.html")
    public Object solveingTaskPage(){
        return "task/solveingTask";
    }

    @PostMapping("/createTask")
    @ResponseBody
    public Object createTask(Problem problem, @RequestParam Map<String, Object> processVariables){
        return taskService.createTask(problem, processVariables);
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
    public Object completeTask(String taskId, @RequestParam Map<String, Object> processVariables){
        return taskService.completeTask(taskId,processVariables);
    }

    @GetMapping("/historyTask")
    @ResponseBody
    public Object getHistoryTask(@RequestParam(required = false) String begin,
                              @RequestParam(required = false) String end,
                              int page, int limit){
        return taskService.getHistoryTask(begin, end, page, limit);
    }

    @GetMapping("/SolveingTask")
    @ResponseBody
    public Object getSolveingTask(int page, int limit){
        return taskService.getSolveingTask(page, limit);
    }

    @GetMapping("/getBeginVariable/{typeId}")
    @ResponseBody
    public Object getBeginVariable(@PathVariable Long typeId){
        return taskService.getBeginVariable(typeId);
    }
}
