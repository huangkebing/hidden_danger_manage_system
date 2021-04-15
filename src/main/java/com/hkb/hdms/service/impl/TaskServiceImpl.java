package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.ProblemInfoMapper;
import com.hkb.hdms.mapper.ProblemMapper;
import com.hkb.hdms.mapper.ProcessVariableMapper;
import com.hkb.hdms.mapper.TaskMapper;
import com.hkb.hdms.model.dto.InstanceDto;
import com.hkb.hdms.model.dto.ProblemDto;
import com.hkb.hdms.model.pojo.*;
import com.hkb.hdms.service.TaskService;
import com.hkb.hdms.service.TypeService;
import com.hkb.hdms.utils.TaskHandlerUtil;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/04/05
 */
@Service
public class TaskServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements TaskService {

    private final TypeService typeService;

    private final RuntimeService runtimeService;

    private final org.activiti.engine.TaskService taskService;

    private final TaskHandlerUtil taskHandlerUtil;

    private final ProcessVariableMapper processVariableMapper;

    private final UserGroupManager userGroupManager;

    private final ProblemMapper problemMapper;

    private final ProblemInfoMapper problemInfoMapper;

    private final TaskMapper taskMapper;

    private final HttpSession session;

    @Autowired
    public TaskServiceImpl(TypeService typeService,
                           RuntimeService runtimeService,
                           TaskHandlerUtil taskHandlerUtil,
                           org.activiti.engine.TaskService taskService,
                           HttpSession session,
                           ProcessVariableMapper processVariableMapper,
                           UserGroupManager userGroupManager,
                           TaskMapper taskMapper,
                           ProblemMapper problemMapper,
                           ProblemInfoMapper problemInfoMapper) {
        this.typeService = typeService;
        this.runtimeService = runtimeService;
        this.taskHandlerUtil = taskHandlerUtil;
        this.taskService = taskService;
        this.session = session;
        this.processVariableMapper = processVariableMapper;
        this.userGroupManager = userGroupManager;
        this.taskMapper = taskMapper;
        this.problemMapper = problemMapper;
        this.problemInfoMapper = problemInfoMapper;
    }

    @Override
    @Transactional
    public R createTask(Problem problem, Map<String, Object> processVariables) {
        Type taskType = typeService.getById(problem.getTypeId());
        User user = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        List<ProcessVariable> variables = processVariableMapper.selectList(new QueryWrapper<ProcessVariable>()
                .eq("begin_variable", 1)
                .eq("process_id", problem.getTypeId()));

        //给定流程变量
        Map<String, Object> variablesMap = new HashMap<>();
        for (ProcessVariable variable : variables) {
            variablesMap.put(variable.getName(), processVariables.get("variables[" + variable.getName() + "]"));
        }

        ProcessInstance instance;
        try {
            //根据流程定义id发起流程实例
            instance = runtimeService.startProcessInstanceById(taskType.getProcessId(), variablesMap);
            //绑定任务的操作人
            taskHandlerUtil.setTaskHandler(instance, problem.getTypeId());
            problem.setInstanceId(instance.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnConstants.PROCESS_ERROR;
        }
        problem.setUserId(user.getId());
        this.save(problem);

        return ReturnConstants.SUCCESS;
    }

    @Override
    public Map<String, Object> getMyTask(int page, int limit) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        int offset = (page - 1) * limit;

        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        List<String> groups = userGroupManager.getUserGroups(loginUser.getEmail());

        List<Problem> problems = taskMapper.getTodoInstances(loginUser.getEmail(), groups, limit, offset);

        map.put("data", problems);
        map.put("count", taskMapper.getTodoCount(loginUser.getEmail(), groups));

        return map;
    }

    @Override
    public R getDetailTask(Long problemId) {
        Map<String, Object> data = new HashMap<>();
        ProblemDto problem = problemMapper.selectDetailById(problemId);
        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);

        List<Task> tasks = taskService.createTaskQuery().processInstanceId(problem.getInstanceId()).taskCandidateUser(loginUser.getEmail()).list();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            List<ProcessVariable> variables = processVariableMapper.selectList(new QueryWrapper<ProcessVariable>()
                    .eq("process_id", task.getProcessDefinitionId())
                    .eq("node_id", task.getTaskDefinitionKey()));
            map.put("variables",variables);
            list.add(map);
        }

        data.put("task", list);
        data.put("problem", problem);
        return new R(0, "SUCCESS", data);
    }

    @Override
    @Transactional
    public R completeTask(String taskId, Map<String,Object> processVariables) {
        //根据taskId查询出task
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        List<ProcessVariable> variables = processVariableMapper.selectList(new QueryWrapper<ProcessVariable>()
                .eq("node_id", task.getTaskDefinitionKey())
                .eq("process_id", task.getProcessDefinitionId()));

        //给定流程变量
        Map<String, Object> variablesMap = new HashMap<>();
        for (ProcessVariable variable : variables) {
            variablesMap.put(variable.getName(), processVariables.get("variables[" + variable.getName() + "]"));
        }
        taskHandlerUtil.deleteCandidateUser(taskId, task.getProcessInstanceId());
        //完成task
        taskService.complete(taskId, variablesMap);
        //尝试去设置后续出现的任务节点的处理人
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //处理的是最后一个节点，complete之后instance会为null
        if(ObjectUtils.isNotEmpty(instance)){
            Problem problem = this.getOne(new QueryWrapper<Problem>().eq("instance_id", task.getProcessInstanceId()));
            taskHandlerUtil.setTaskHandler(instance,problem.getTypeId());
        }
        return ReturnConstants.SUCCESS;
    }

    @Override
    public List<ProcessVariable> getBeginVariable(Long typeId) {
        Type taskType = typeService.getById(typeId);

        return processVariableMapper.selectList(new QueryWrapper<ProcessVariable>()
                .eq("process_id", taskType.getProcessId())
                .eq("begin_variable", 1));
    }

    @Override
    public Map<String, Object> getHistoryTask(String begin, String end, int page, int limit) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        int offset = (page - 1) * limit;

        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        List<String> groups = userGroupManager.getUserGroups(loginUser.getEmail());

        List<Problem> problems = taskMapper.getHistoryInstances(loginUser.getEmail(), groups, limit, offset, begin, end);

        map.put("data", problems);
        map.put("count", taskMapper.getHistoryCount(loginUser.getEmail(), groups, begin, end));
        return map;
    }

    @Override
    public Map<String, Object> getSolveingTask(int page, int limit) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        int offset = (page - 1) * limit;

        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        List<String> groups = userGroupManager.getUserGroups(loginUser.getEmail());

        List<Problem> problems = taskMapper.getSolveingInstances(loginUser.getEmail(), groups, limit, offset);

        map.put("data", problems);
        map.put("count", taskMapper.getSolveingCount(loginUser.getEmail(), groups));
        return map;
    }

    @Override
    public R updateProblem(Problem problem) {
        if (this.update(problem, new UpdateWrapper<Problem>().eq("id",problem.getId()))) {
            return ReturnConstants.SUCCESS;
        }
        else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public Map<String, Object> getRemarks(Long problemId, int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        Page<ProblemInfo> pageParam = new Page<>(page, limit);
        problemInfoMapper.selectPage(pageParam, new QueryWrapper<ProblemInfo>()
                .eq("type", 1)
                .eq("problem_id",problemId)
                .orderByDesc("modify"));
        map.put("data",pageParam.getRecords());
        map.put("count",pageParam.getTotal());
        return map;
    }

    @Override
    public Map<String, Object> getFiles(Long problemId, int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        Page<ProblemInfo> pageParam = new Page<>(page, limit);
        problemInfoMapper.selectPage(pageParam, new QueryWrapper<ProblemInfo>()
                .eq("type", 2).or().eq("type",3)
                .eq("problem_id",problemId)
                .orderByDesc("modify"));
        map.put("data",pageParam.getRecords());
        map.put("count",pageParam.getTotal());
        return map;
    }

    @Override
    public R addRemarks(Long problemId, String remark) {
        User user = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        ProblemInfo problemInfo = new ProblemInfo();
        problemInfo.setProblemId(problemId);
        problemInfo.setContext(remark);
        problemInfo.setType(1);
        problemInfo.setEmail(user.getEmail());
        problemInfo.setUserId(user.getId());
        problemInfo.setUsername(user.getName());
        problemInfoMapper.insert(problemInfo);

        return ReturnConstants.SUCCESS;
    }

    @Override
    public R updateRemarks(ProblemInfo info) {
        User user = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        info.setEmail(user.getEmail());
        info.setUserId(user.getId());
        info.setUsername(user.getName());

        problemInfoMapper.update(info, new UpdateWrapper<ProblemInfo>().eq("id",info.getId()));
        return ReturnConstants.SUCCESS;
    }

    @Override
    public R deleteRemarks(Long infoId) {
        problemInfoMapper.deleteById(infoId);
        return ReturnConstants.SUCCESS;
    }
}
