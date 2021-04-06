package com.hkb.hdms.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkb.hdms.mapper.ProcessNodeRoleMapper;
import com.hkb.hdms.model.pojo.ProcessNodeRole;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/04/06
 */
@Component
public class TaskHandlerUtil {

    private final TaskService taskService;

    private final ProcessNodeRoleMapper processNodeRoleMapper;

    @Autowired
    public TaskHandlerUtil(TaskService taskService, ProcessNodeRoleMapper processNodeRoleMapper) {
        this.taskService = taskService;
        this.processNodeRoleMapper = processNodeRoleMapper;
    }

    public void setTaskHandler(String instanceId){
        List<Task> list = taskService.createTaskQuery().processInstanceId(instanceId).list();
        System.out.println(123);
    }
}
