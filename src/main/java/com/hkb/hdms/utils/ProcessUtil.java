package com.hkb.hdms.utils;

import com.hkb.hdms.mapper.ProcessNodeRoleMapper;
import com.hkb.hdms.model.pojo.ProcessNodeRole;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/04/09
 */
@Component
public class ProcessUtil {

    private final RepositoryService repositoryService;

    private final ProcessNodeRoleMapper processNodeRoleMapper;

    @Autowired
    public ProcessUtil(RepositoryService repositoryService, ProcessNodeRoleMapper processNodeRoleMapper) {
        this.repositoryService = repositoryService;
        this.processNodeRoleMapper = processNodeRoleMapper;
    }

    public Collection<FlowElement> getAllFlowElements(String processDefineId){
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefineId);
        return bpmnModel.getMainProcess().getFlowElements();
    }

    public void processNodeWithRole(Deployment deployment){
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        for (ProcessDefinition definition : definitions) {
            Collection<FlowElement> flowElements = getAllFlowElements(definition.getId());
            List<FlowElement> userTasks = flowElements.stream().filter(flowElement -> flowElement.getClass().equals(UserTask.class)).collect(Collectors.toList());
            for (FlowElement userTask : userTasks) {
                ProcessNodeRole processNodeRole = new ProcessNodeRole();
                processNodeRole.setRoleId(0L);
                processNodeRole.setNodeId(userTask.getId());
                processNodeRole.setName(userTask.getName());
                processNodeRole.setProcessId(definition.getId());
                processNodeRoleMapper.insert(processNodeRole);
            }
        }
    }
}
