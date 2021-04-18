package com.hkb.hdms.controller;

import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.ProblemInfo;
import com.hkb.hdms.service.TaskService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    @RequestMapping("/readTask.html/{problemId}")
    public Object readTaskPage(@PathVariable Long problemId){
        ModelAndView model = new ModelAndView();
        model.addObject("problemId",problemId);
        model.setViewName("task/readTask");
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
    public Object getHistoryTask(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String priority,
                              @RequestParam(required = false) String begin,
                              @RequestParam(required = false) String end,
                              int page, int limit){
        return taskService.getHistoryTask(name, priority, begin, end, page, limit);
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

    @PostMapping("/updateProblem")
    @ResponseBody
    public Object updateProblem(Problem problem){
        return taskService.updateProblem(problem);
    }

    @GetMapping("/getRemarks/{problemId}/{page}/{limit}")
    @ResponseBody
    public Object getRemarks(@PathVariable int limit, @PathVariable int page, @PathVariable Long problemId){
        return taskService.getRemarks(problemId, page, limit);
    }

    @GetMapping("/getFiles/{problemId}/{page}/{limit}")
    @ResponseBody
    public Object getFiles(@PathVariable int limit, @PathVariable int page, @PathVariable Long problemId){
        return taskService.getFiles(problemId, page, limit);
    }

    @PostMapping("/addRemarks")
    @ResponseBody
    public Object addRemarks(Long problemId, String remark){
        return taskService.addRemarks(problemId, remark);
    }

    @PostMapping("/updateRemarks")
    @ResponseBody
    public Object updateRemarks(ProblemInfo info){
        return taskService.updateRemarks(info);
    }

    @PostMapping("/deleteRemarks")
    @ResponseBody
    public Object deleteRemarks(Long infoId){
        return taskService.deleteRemarks(infoId);
    }

    @PostMapping("/transferTask")
    @ResponseBody
    public Object transferTask(Long problemId, String email){
        return taskService.transferTask(problemId, email);
    }

    @GetMapping("speedOfProgress/{problemId}")
    public void speedOfProgress(HttpServletResponse response, @PathVariable Long problemId) {
        InputStream image = taskService.speedOfProgress(problemId);
        try {
            byte[] imageBytes = IOUtils.toByteArray(image);
            image.close();
            response.setContentType("text/xml"); // 设置返回的文件类型
            OutputStream os = response.getOutputStream();
            os.write(imageBytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("closeTask")
    @ResponseBody
    public Object closeTask(Long problemId){
        return taskService.closeTask(problemId);
    }

    @PostMapping("deleteTask")
    @ResponseBody
    public Object deleteTask(Long problemId){
        return taskService.deleteTask(problemId);
    }
}
