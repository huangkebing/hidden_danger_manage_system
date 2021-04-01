package com.hkb.hdms.service;

import com.hkb.hdms.base.R;
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

    void processNodeWithRole(Deployment deployment);

    Map<String, Object> queryProcessNode(String processId);
}
