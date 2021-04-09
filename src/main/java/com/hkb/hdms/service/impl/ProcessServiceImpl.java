package com.hkb.hdms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.ProcessNodeRoleMapper;
import com.hkb.hdms.model.pojo.ProcessNodeRole;
import com.hkb.hdms.service.ProcessService;
import com.hkb.hdms.utils.ProcessUtil;
import com.hkb.hdms.utils.UUIDUtil;
import com.mysql.cj.util.StringUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
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
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
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

    private final ProcessUtil processUtil;

    @Autowired
    public ProcessServiceImpl(RepositoryService repositoryService, ProcessNodeRoleMapper processNodeRoleMapper, ProcessUtil processUtil) {
        this.repositoryService = repositoryService;
        this.processNodeRoleMapper = processNodeRoleMapper;
        this.processUtil = processUtil;
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
        processUtil.processAnalysis(deployment);
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
        processUtil.processAnalysis(deployment);
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
            BpmnModel bpmnModel = repositoryService.getBpmnModel(definition.getId());
            if(bpmnModel != null) {
                Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
                List<String> userTasks = flowElements.stream()
                        .filter(flowElement -> flowElement.getClass().equals(UserTask.class))
                        .map(FlowElement::getId)
                        .collect(Collectors.toList());
                processNodeRoleMapper.delete(new QueryWrapper<ProcessNodeRole>().in("node_id",userTasks));
            }
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
    public void processNodeWithRole(Deployment deployment) {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

        for (ProcessDefinition definition : definitions) {

            BpmnModel bpmnModel = repositoryService.getBpmnModel(definition.getId());
            if(bpmnModel != null) {
                Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
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
    public void test() {
        String id = "6b3ae979-9923-11eb-8e64-9822ef207876";

        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().deploymentId(id).list();

        for (ProcessDefinition definition : definitions) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(definition.getId());
            if(bpmnModel != null) {
                Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
                for (FlowElement flowElement : flowElements) {
                    if(flowElement instanceof SequenceFlow){
                        SequenceFlow flowElement1 = (SequenceFlow) flowElement;
                        SpelExpressionParser parser = new SpelExpressionParser();

                        SpelExpression expression = parser.parseRaw(flowElement1.getConditionExpression());

                        System.out.println(flowElement1.getConditionExpression());
                    }
                    System.out.println(flowElement.getId());
                    System.out.println(flowElement.getName());
                    System.out.println(flowElement.getClass());
                    System.out.println(JSON.toJSONString(flowElement.getExtensionElements()));
                }
            }
        }



    }


}
