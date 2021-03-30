package com.hkb.hdms.controller;

import com.hkb.hdms.service.ProcessService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 流程定义相关接口
 *
 * @author huangkebing
 * 2021/03/26
 */
@Controller
@RequestMapping("/process")
public class ProcessController {

    private final ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @RequestMapping("/process.html")
    public Object processPage(){
        return "process/process";
    }

    /**
     * 上传bpmn文件，用于编辑器导入文件使用
     * @param  multipartFile 文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public Object upload(@RequestParam("processFile") MultipartFile multipartFile){
        return processService.upload(multipartFile);
    }

    /**
     * 通过String部署流程
     * @param stringBPMN 流程bpmn
     */
    @PostMapping("/addDeploymentByString")
    @ResponseBody
    public Object addDeploymentByString(String stringBPMN) {
        return processService.deployByString(stringBPMN);
    }

    @GetMapping("/getProcesses")
    @ResponseBody
    public Object getProcesses(@RequestParam(required = false) String processName,
                               @RequestParam(required = false) String processKey, int limit, int page){
        return processService.queryProcesses(processName, processKey, limit, page);
    }

    @RequestMapping("/exportXML/{deploymentId}/{resourceName}")
    public void exportXML(@PathVariable("deploymentId") String deploymentId,
                          @PathVariable String resourceName,
                          HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            byte[] xmlBytes = processService.getXMLBytes(deploymentId, resourceName);
            ByteArrayInputStream in = new ByteArrayInputStream(xmlBytes);
            IOUtils.copy(in, response.getOutputStream());
            String filename = processService.getXMLFileName(deploymentId, resourceName) + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            response.flushBuffer();
        } catch (IOException e) {
            try {
                PrintWriter out = response.getWriter();
                out.write("未找到对应数据，该流程模型没有设置流程数据！");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @RequestMapping("/getProcessImage/{processId}")
    public void getProcessImage(HttpServletResponse response, @PathVariable String processId){
        InputStream processImage = processService.getProcessImage(processId);
        try {
            byte[] imageBytes = IOUtils.toByteArray(processImage);
            response.setContentType("text/xml"); // 设置返回的文件类型
            OutputStream os = response.getOutputStream();
            os.write(imageBytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/deleteProcess")
    @ResponseBody
    public Object deleteProcess(String deploymentId){
        return processService.deleteProcess(deploymentId);
    }

    @PostMapping("/activeProcess")
    @ResponseBody
    public Object activeProcess(String processId){
        return processService.activeProcess(processId);
    }

    @PostMapping("/suspendProcess")
    @ResponseBody
    public Object suspendProcess(String processId){
        return processService.suspendProcess(processId);
    }
}
