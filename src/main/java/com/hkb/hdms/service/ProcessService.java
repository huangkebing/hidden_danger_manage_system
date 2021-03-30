package com.hkb.hdms.service;

import com.hkb.hdms.base.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/26
 */
public interface ProcessService {

    R upload(MultipartFile processFile);

    R deployByString(String stringBPMN);

    Map<String, Object> queryProcesses(String processName, String processKey, int limit, int page);

    byte[] getXMLBytes(String deploymentId, String resourceName);

    String getXMLFileName(String deploymentId, String resourceName);

    R deleteProcess(String deploymentId);

    InputStream getProcessImage(String processId);

    R activeProcess(String processId);

    R suspendProcess(String processId);
}