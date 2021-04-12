package com.hkb.hdms.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.mapper.ProcessNodeRoleMapper;
import com.hkb.hdms.mapper.RoleMapper;
import com.hkb.hdms.mapper.TaskMapper;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.dto.InstanceDto;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.ProcessNodeRole;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.model.pojo.UserRole;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangkebing
 * 2021/04/06
 */
@Component
public class TaskHandlerUtil {

    private final TaskService taskService;

    private final ProcessNodeRoleMapper processNodeRoleMapper;

    private final HttpSession session;

    private final RoleMapper roleMapper;

    private final UserMapper userMapper;

    private final TaskMapper taskMapper;

    @Autowired
    public TaskHandlerUtil(TaskService taskService, ProcessNodeRoleMapper processNodeRoleMapper, HttpSession session, RoleMapper roleMapper, UserMapper userMapper, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.processNodeRoleMapper = processNodeRoleMapper;
        this.session = session;
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
    }

    public void setTaskHandler(ProcessInstance instance, Long typeId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).list();

        for (Task task : tasks) {
            ProcessNodeRole processNodeRole = processNodeRoleMapper.selectOne(new QueryWrapper<ProcessNodeRole>()
                    .eq("process_id", instance.getProcessDefinitionId())
                    .eq("node_id", task.getTaskDefinitionKey()));

            Long roleId = processNodeRole.getRoleId();

            //该任务是发起人的情况
            if(roleId == 0L){
                User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
                taskService.addCandidateUser(task.getId(), loginUser.getEmail());
            }
            else{
                UserRole userRole = roleMapper.selectById(roleId);
                List<User> users;
                //该角色是否参与问题分配，不参与直接全部查出来
                if(userRole.getQuestion() == 0){
                    users = userMapper.selectList(new QueryWrapper<User>()
                            .eq("role", roleId)
                            .eq("live",1));
                }
                else{
                    users = userMapper.selectUsersByTypeAndRole(roleId, typeId);
                }
                for (User user : users) {
                    taskService.addCandidateUser(task.getId(), user.getEmail());
                }
            }
        }
    }

    public void deleteCandidateUser(String taskId, String instanceId){
        User user = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        taskMapper.deleteRuUsers(taskId, instanceId);
        taskMapper.deleteHiUsers(taskId, instanceId);

        taskService.addCandidateUser(taskId, user.getEmail());
    }

    public List<Problem> todoTaskSort(List<Problem> resources, List<String> resourceIds) {
        if(resources.size() == 0 || resourceIds.size() == 0){
            return resources;
        }
        List<Problem> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(resources)){
            //初始化result,为了排序
            for(int i= 0; i < resources.size() ;i++){
                result.add(new Problem());
            }
            for(Problem resource:resources){
                String id = resource.getInstanceId();
                result.set(resourceIds.indexOf(id), resource);
            }
        }
        return result;
    }

    public List<Problem> historyTaskSort(List<Problem> resources, List<String> resourceIds, List<InstanceDto> instances) {
        if(resources.size() == 0 || resourceIds.size() == 0 || instances.size() == 0){
            return resources;
        }
        List<Problem> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(resources)){
            //初始化result,为了排序
            for(int i= 0; i < resources.size() ;i++){
                result.add(new Problem());
            }
            for(Problem resource:resources){
                String id = resource.getInstanceId();
                int index = resourceIds.indexOf(id);
                InstanceDto processInstance = instances.get(index);
                resource.setCreate(processInstance.getSolveTime());
                result.set(index, resource);
            }
        }
        return result;
    }
}
