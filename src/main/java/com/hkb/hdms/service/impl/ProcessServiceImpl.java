package com.hkb.hdms.service.impl;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.service.ProcessService;
import com.hkb.hdms.utils.UUIDUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/03/26
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    private final RepositoryService repositoryService;

    @Autowired
    public ProcessServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
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
            return new R(-1,e.getMessage());
        }
        return new R(0, "SUCCESS", fileName);
    }

    @Override
    public R deployByString(String stringBPMN) {
        Deployment deployment = repositoryService.createDeployment()
                .addString(UUID.randomUUID() + ".bpmn",stringBPMN)
                .name(UUIDUtil.getUUID())
                .deploy();

        return new R(0, "SUCCESS", deployment.getName());
    }

    @Override
    public Map<String, Object> queryProcesses(int limit, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        page = (page - 1) * limit;
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().listPage(page, limit);

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
    public byte[] getXMLBytes(String deploymentId, String resourceName) {
        InputStream stream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        try {
           return IOUtils.toByteArray(stream);
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
    public R deleteProcess(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return ReturnConstants.SUCCESS;
    }

    @Override
    public InputStream getProcessImage(String processId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
        ProcessDiagramGenerator ge = new DefaultProcessDiagramGenerator();
        return ge.generateDiagram(bpmnModel,"宋体", "宋体", "宋体");
    }
}
