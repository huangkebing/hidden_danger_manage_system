package com.hkb.hdms.service;

import com.hkb.hdms.base.R;
import com.hkb.hdms.model.pojo.ProcessNodeRole;
import com.hkb.hdms.model.pojo.ProcessVariable;
import org.activiti.engine.repository.Deployment;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/26
 */
public interface ProcessService {

    R upload(MultipartFile processFile);

    R deployByString(String stringBPMN);

    R deployByFile(MultipartFile processFile);

    Map<String, Object> queryProcesses(String processName, String processKey, int limit, int page);

    List<Map<String, Object>> queryAllProcesses();

    byte[] getXMLBytes(String deploymentId, String resourceName);

    String getXMLFileName(String deploymentId, String resourceName);

    R deleteProcess(String deploymentId);

    InputStream getProcessImage(String processId);

    R activeProcess(String processId);

    R suspendProcess(String processId);

    Map<String, Object> queryProcessNode(String processId);

    R updateNodeRole(ProcessNodeRole processNodeRole);

    Map<String, Object> getVariable(String nodeId, String processId, int page, int limit);

    R addVariable(ProcessVariable variable);

    R updateVariable(ProcessVariable variable);

    R deleteVariable(Long variableId);

    List<Map<String, Object>> getTaskOption(String processId);
}
