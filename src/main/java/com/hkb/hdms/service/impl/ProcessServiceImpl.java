package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.ProcessNodeRoleMapper;
import com.hkb.hdms.mapper.ProcessVariableMapper;
import com.hkb.hdms.model.pojo.ProcessNodeRole;
import com.hkb.hdms.model.pojo.ProcessVariable;
import com.hkb.hdms.service.ProcessService;
import com.hkb.hdms.utils.ProcessUtil;
import com.hkb.hdms.utils.UUIDUtil;
import com.mysql.cj.util.StringUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * @author huangkebing
 * 2021/03/26
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    private final RepositoryService repositoryService;

    private final ProcessNodeRoleMapper processNodeRoleMapper;

    private final ProcessVariableMapper processVariableMapper;

    private final ProcessUtil processUtil;

    @Autowired
    public ProcessServiceImpl(RepositoryService repositoryService, ProcessNodeRoleMapper processNodeRoleMapper, ProcessUtil processUtil, ProcessVariableMapper processVariableMapper) {
        this.repositoryService = repositoryService;
        this.processNodeRoleMapper = processNodeRoleMapper;
        this.processUtil = processUtil;
        this.processVariableMapper = processVariableMapper;
    }

    @Override
    public R upload(MultipartFile processFile) {
        if(processFile.isEmpty()){
            return ReturnConstants.PARAMS_EMPTY;
        }

        String fileName = processFile.getOriginalFilename();
        String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        String filePath = Constants.BPMN_PathMapping;

        filePath = filePath.replace("\\", "/");
        filePath = filePath.replace("file:", "");

        fileName = UUID.randomUUID() + suffixName;
        File file = new File(filePath + fileName);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            processFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnConstants.FAILURE;
        }
        return new R(0, "SUCCESS", fileName);
    }

    @Override
    public R deployByString(String stringBPMN) {
        Deployment deployment = repositoryService.createDeployment()
                .addString(UUID.randomUUID() + ".bpmn",stringBPMN)
                .name(UUIDUtil.getUUID())
                .deploy();

        //流程解析
        processUtil.processNodeWithRole(deployment);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public R deployByFile(MultipartFile processFile) {
        String fileName = processFile.getOriginalFilename();
        Deployment deployment;
        try {
            InputStream fileInputStream = processFile.getInputStream();
            String extension = FilenameUtils.getExtension(fileName);

            if ("zip".equals(extension)) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment()
                        .addZipInputStream(zip)
                        .name(UUIDUtil.getUUID())
                        .deploy();
            } else if("bpmn".equals(extension)){
                deployment = repositoryService.createDeployment()
                        .addInputStream(fileName, fileInputStream)
                        .name(UUIDUtil.getUUID())
                        .deploy();
            } else {
                return ReturnConstants.FILE_TYPE_ERROR;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ReturnConstants.FAILURE;
        }
        //流程解析
        processUtil.processNodeWithRole(deployment);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public Map<String, Object> queryProcesses(String processName, String processKey, int limit, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        page = (page - 1) * limit;

        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if(!StringUtils.isNullOrEmpty(processName)){
            query.processDefinitionNameLike("%" + processName + "%");
        }

        if(!StringUtils.isNullOrEmpty(processKey)){
            query.processDefinitionKeyLike("%" + processKey + "%");
        }

        List<ProcessDefinition> definitions = query.listPage(page, limit);

        definitions.sort((y,x)->x.getVersion()-y.getVersion());

        List<HashMap<String, Object>> listMap= new ArrayList<>();
        for (ProcessDefinition pd : definitions) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("processDefinitionID", pd.getId());
            hashMap.put("name", pd.getName());
            hashMap.put("key", pd.getKey());
            hashMap.put("deploymentID", pd.getDeploymentId());
            hashMap.put("version", pd.getVersion());
            hashMap.put("suspended", pd.isSuspended());
            hashMap.put("resourceName", pd.getResourceName());
            listMap.add(hashMap);
        }

        map.put("count", repositoryService.createProcessDefinitionQuery().count());
        map.put("data", listMap);
        return map;
    }

    @Override
    public List<Map<String, Object>> queryAllProcesses() {
        List<Map<String, Object>> listMap= new ArrayList<>();
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().list();
        definitions.sort((y,x)->x.getVersion()-y.getVersion());

        for (ProcessDefinition pd : definitions) {
            if(pd.isSuspended()){
                continue;
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("processDefinitionID", pd.getId());
            hashMap.put("name", pd.getName());
            hashMap.put("key", pd.getKey());
            hashMap.put("version", pd.getVersion());
            listMap.add(hashMap);
        }
        return listMap;
    }

    @Override
    public byte[] getXMLBytes(String deploymentId, String resourceName) {
        InputStream stream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            stream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public String getXMLFileName(String deploymentId, String resourceName) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).list();
        List<ProcessDefinition> res = list.stream().filter(processDefinition -> resourceName.equals(processDefinition.getResourceName())).collect(Collectors.toList());

        return res.get(0).getName() == null ? "未命名" : res.get(0).getName();
    }

    @Override
    @Transactional
    public R deleteProcess(String deploymentId) {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).list();

        for (ProcessDefinition definition : definitions) {
            //同时删除流程角色和流程变量信息
            processNodeRoleMapper.delete(new QueryWrapper<ProcessNodeRole>().eq("process_id",definition.getId()));
            processVariableMapper.delete(new QueryWrapper<ProcessVariable>().eq("process_id",definition.getId()));
        }
        repositoryService.deleteDeployment(deploymentId, true);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public InputStream getProcessImage(String processId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
        ProcessDiagramGenerator ge = new DefaultProcessDiagramGenerator();
        return ge.generateDiagram(bpmnModel,"宋体", "宋体", "宋体");
    }

    @Override
    public R activeProcess(String processId) {
        repositoryService.activateProcessDefinitionById(processId);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public R suspendProcess(String processId) {
        repositoryService.suspendProcessDefinitionById(processId);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public Map<String, Object> queryProcessNode(String processId) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        List<ProcessNodeRole> nodes = processNodeRoleMapper.selectList(new QueryWrapper<ProcessNodeRole>().eq("process_id", processId));
        map.put("data", nodes);
        return map;
    }

    @Override
    public R updateNodeRole(ProcessNodeRole processNodeRole) {
        UpdateWrapper<ProcessNodeRole> wrapper = new UpdateWrapper<ProcessNodeRole>().eq("node_id", processNodeRole.getNodeId());
        int update = processNodeRoleMapper.update(processNodeRole, wrapper);
        if(update == 1){
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public Map<String, Object> getVariable(String nodeId, String processId, int page, int limit) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        Page<ProcessVariable> pageParam = new Page<>(page, limit);

        if(StringUtils.isNullOrEmpty(nodeId)){
            processVariableMapper.selectPage(pageParam, new QueryWrapper<ProcessVariable>().eq("process_id",processId));
        } else{
            processVariableMapper.selectPage(pageParam, new QueryWrapper<ProcessVariable>().eq("process_id",processId).eq("node_id",nodeId));
        }

        map.put("data",pageParam.getRecords());
        map.put("count", pageParam.getTotal());
        return map;
    }

    @Override
    public R addVariable(ProcessVariable variable) {
        processVariableMapper.insert(variable);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public R updateVariable(ProcessVariable variable) {
        processVariableMapper.update(variable, new UpdateWrapper<ProcessVariable>().eq("id",variable.getId()));
        return ReturnConstants.SUCCESS;
    }

    @Override
    public R deleteVariable(Long variableId) {
        processVariableMapper.deleteById(variableId);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public List<Map<String, Object>> getTaskOption(String processId) {
        List<Map<String, Object>> res = new ArrayList<>();
        Collection<FlowElement> elements = processUtil.getAllFlowElements(processId);
        List<FlowElement> tasks = elements.stream().filter(flowElement -> flowElement.getClass().equals(UserTask.class) || flowElement.getClass().equals(StartEvent.class)).collect(Collectors.toList());
        for (FlowElement task : tasks) {
            Map<String, Object> map = new HashMap<>();
            if(task instanceof StartEvent){
                if(StringUtils.isNullOrEmpty(task.getName())){
                    map.put("name","未命名开始事件");
                }
                else{
                    map.put("name",task.getName());
                }
            }
            else{
                if(StringUtils.isNullOrEmpty(task.getName())){
                    map.put("name","未命名用户任务");
                }
                else{
                    map.put("name",task.getName());
                }
            }
            map.put("id",task.getId());
            res.add(map);
        }
        return res;
    }

}
