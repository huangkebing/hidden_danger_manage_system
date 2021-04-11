package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.ProblemMapper;
import com.hkb.hdms.mapper.ProcessVariableMapper;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.ProcessVariable;
import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.TaskService;
import com.hkb.hdms.service.TypeService;
import com.hkb.hdms.utils.TaskHandlerUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;
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

    private final HistoryService historyService;

    private final TaskHandlerUtil taskHandlerUtil;

    private final ProcessVariableMapper processVariableMapper;

    private final HttpSession session;

    @Autowired
    public TaskServiceImpl(TypeService typeService,
                           RuntimeService runtimeService,
                           TaskHandlerUtil taskHandlerUtil,
                           org.activiti.engine.TaskService taskService,
                           HttpSession session,
                           HistoryService historyService,
                           ProcessVariableMapper processVariableMapper) {
        this.typeService = typeService;
        this.runtimeService = runtimeService;
        this.taskHandlerUtil = taskHandlerUtil;
        this.taskService = taskService;
        this.session = session;
        this.historyService = historyService;
        this.processVariableMapper = processVariableMapper;
    }

    @Override
    public R createTask(Problem problem, Map<String, Object> processVariables) {
        Type taskType = typeService.getById(problem.getTypeId());

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

        page = (page - 1) * limit;

        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(loginUser.getEmail()).listPage(page, limit);

        List<Problem> problems = new ArrayList<>();
        for (Task task : tasks) {
            Problem problem = this.getOne(new QueryWrapper<Problem>().eq("instance_id", task.getProcessInstanceId()));
            problems.add(problem);
        }

        map.put("data", problems);
        map.put("count", problems.size());

        return map;
    }

    @Override
    public R getDetailTask(Long problemId) {
        Map<String, Object> data = new HashMap<>();
        Problem problem = this.getById(problemId);
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
        return new R(0, "SUCCESS", data);
    }

    @Override
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
    public void test(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processFinished().taskCandidateUser("yuanchengwei3@163.com").list();


        List<HistoricProcessInstance> list2 = historyService.createNativeHistoricProcessInstanceQuery()
                .sql("select distinct RES.PROC_INST_ID_, HPI.END_TIME_\n" +
                        "from ACT_HI_TASKINST RES inner join ACT_HI_IDENTITYLINK HI\n" +
                        "    on HI.TASK_ID_ = RES.ID_ inner join ACT_HI_PROCINST HPI\n" +
                        "        ON RES.PROC_INST_ID_ = HPI.ID_\n" +
                        "WHERE HPI.END_TIME_ is not null\n" +
                        "  and RES.ASSIGNEE_ is null\n" +
                        "  and HI.TYPE_ = 'candidate'\n" +
                        "  and ( HI.USER_ID_ = 'yuanchengwei3@163.com' or HI.GROUP_ID_ IN ( '交通隐患' , '火灾隐患' ) )")
                .list();

        List<HistoricTaskInstance> list1 = historyService.createNativeHistoricTaskInstanceQuery()
                .sql("select distinct RES.PROC_INST_ID_\n" +
                        "from ACT_HI_TASKINST RES inner join ACT_HI_IDENTITYLINK HI\n" +
                        "    on HI.TASK_ID_ = RES.ID_ inner join ACT_HI_PROCINST HPI\n" +
                        "        ON RES.PROC_INST_ID_ = HPI.ID_\n" +
                        "WHERE HPI.END_TIME_ is not null\n" +
                        "  and RES.ASSIGNEE_ is null\n" +
                        "  and HI.TYPE_ = 'candidate'\n" +
                        "  and ( HI.USER_ID_ = 'yuanchengwei3@163.com' or HI.GROUP_ID_ IN ( '交通隐患' , '火灾隐患' ) )").list();


        System.out.println(list2.size());
    }
}
