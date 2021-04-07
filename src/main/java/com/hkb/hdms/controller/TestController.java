package com.hkb.hdms.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 初步集成activiti测试使用
 *
 * @author huangkebing
 * 2021/03/15
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/deploy")
    @ResponseBody
    public Object deployement(){
        Deployment deploy = repositoryService.createDeployment()
                .name("流程部署测试2")
                .key("myTest")
                .addClasspathResource("processes/test.bpmn")
                .deploy();

        return deploy;
    }

    @RequestMapping("/process")
    @ResponseBody
    public Object process(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_1");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",processInstance.getId());
        map.put("name",processInstance.getName());
        return map;
    }

    @RequestMapping("/task")
    @ResponseBody
    public Object task(){
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId("45025253-9750-11eb-b56e-9822ef207876")
                .taskCandidateUser("yuanchengwei3@163.com")
                .list();

        ArrayList<Map<String, Object>> res = new ArrayList<>();
        for (Task task : tasks) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("流程实例id：" , task.getProcessInstanceId());
            map.put("任务id：" , task.getId());
            map.put("任务负责人：" , task.getAssignee());
            map.put( "任务名称：" , task.getName());
            res.add(map);
        }
        return res;
    }

    @RequestMapping("/complete")
    @ResponseBody
    public Object completeTask(){
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("Process_1")
                .taskAssignee("黄钶炳")
                .list();

        taskService.complete(list.get(0).getId());
        return task();
    }
}
