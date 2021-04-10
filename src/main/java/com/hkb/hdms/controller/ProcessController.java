package com.hkb.hdms.controller;

import com.hkb.hdms.model.pojo.ProcessNodeRole;
import com.hkb.hdms.model.pojo.ProcessVariable;
import com.hkb.hdms.service.ProcessService;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public Object processPage() {
        return "process/process";
    }

    @RequestMapping("/node.html/{processId}/{processName}")
    public Object nodePage(@PathVariable String processId, @PathVariable String processName){
        ModelAndView model = new ModelAndView();
        model.addObject("processId",processId);
        if(StringUtils.isNullOrEmpty(processName) || "undefined".equals(processName)){
            processName = "未命名";
        }
        processName += " 流程用户任务节点表";
        model.addObject("processName", processName);
        model.setViewName("process/node");
        return model;
    }

    @RequestMapping("/variable.html/{processId}/{processName}")
    public Object variablePage(@PathVariable String processId, @PathVariable String processName){
        ModelAndView model = new ModelAndView();
        model.addObject("processId",processId);
        if(StringUtils.isNullOrEmpty(processName) || "undefined".equals(processName)){
            processName = "未命名";
        }
        processName += " 流程变量表";
        model.addObject("processName", processName);
        model.setViewName("process/variable");
        return model;
    }

    /**
     * 上传bpmn文件，用于编辑器导入文件使用
     *
     * @param multipartFile 文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public Object upload(@RequestParam("processFile") MultipartFile multipartFile) {
        return processService.upload(multipartFile);
    }

    /**
     * 通过String部署流程
     *
     * @param stringBPMN 流程bpmn
     */
    @PostMapping("/addDeploymentByString")
    @ResponseBody
    public Object addDeploymentByString(String stringBPMN) {
        return processService.deployByString(stringBPMN);
    }


    @PostMapping("/addDeploymentByFile")
    @ResponseBody
    public Object addDeploymentByFile(@RequestParam("processFile") MultipartFile processFile) {
        return processService.deployByFile(processFile);
    }

    @GetMapping("/getProcesses")
    @ResponseBody
    public Object getProcesses(@RequestParam(required = false) String processName,
                               @RequestParam(required = false) String processKey, int limit, int page) {
        return processService.queryProcesses(processName, processKey, limit, page);
    }

    @GetMapping("/getAllProcesses")
    @ResponseBody
    public Object getAllProcesses() {
        return processService.queryAllProcesses();
    }

    @RequestMapping("/exportXML/{deploymentId}/{resourceName}")
    public void exportXML(@PathVariable("deploymentId") String deploymentId,
                          @PathVariable String resourceName,
                          HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            byte[] xmlBytes = processService.getXMLBytes(deploymentId, resourceName);
            ByteArrayInputStream in = new ByteArrayInputStream(xmlBytes);
            IOUtils.copy(in, response.getOutputStream());
            String filename = processService.getXMLFileName(deploymentId, resourceName) + ".bpmn";
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

    @RequestMapping("/getDefineXML")
    public void getDefineXML(String deploymentId, String resourceName, HttpServletResponse response) {
        try {
            byte[] xmlBytes = processService.getXMLBytes(deploymentId, resourceName);
            response.setContentType("text/xml");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(xmlBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getProcessImage/{processId}")
    public void getProcessImage(HttpServletResponse response, @PathVariable String processId) {
        InputStream processImage = processService.getProcessImage(processId);
        try {
            byte[] imageBytes = IOUtils.toByteArray(processImage);
            processImage.close();
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
    public Object deleteProcess(String deploymentId) {
        return processService.deleteProcess(deploymentId);
    }

    @PostMapping("/activeProcess")
    @ResponseBody
    public Object activeProcess(String processId) {
        return processService.activeProcess(processId);
    }

    @PostMapping("/suspendProcess")
    @ResponseBody
    public Object suspendProcess(String processId) {
        return processService.suspendProcess(processId);
    }

    @GetMapping("/getProcessNode/{processId}")
    @ResponseBody
    public Object getProcessNode(@PathVariable String processId) {
        return processService.queryProcessNode(processId);
    }

    @PostMapping("/updateNodeRole")
    @ResponseBody
    public Object updateNodeRole(ProcessNodeRole processNodeRole){
        return processService.updateNodeRole(processNodeRole);
    }

    @GetMapping("/getVariable")
    @ResponseBody
    public Object getVariable(@RequestParam(required = false) String nodeId,
                              String processId, int page, int limit){
        return processService.getVariable(nodeId, processId, page, limit);
    }

    @PostMapping("/addVariable")
    @ResponseBody
    public Object addVariable(ProcessVariable variable){
        return processService.addVariable(variable);
    }

    @PostMapping("/updateVariable")
    @ResponseBody
    public Object updateVariable(ProcessVariable variable){
        return processService.updateVariable(variable);
    }

    @PostMapping("/deleteVariable")
    @ResponseBody
    public Object deleteVariable(Long variableId){
        return processService.deleteVariable(variableId);
    }

    @GetMapping("/getTaskOption/{processId}")
    @ResponseBody
    public Object getTaskOption(@PathVariable String processId){
        return processService.getTaskOption(processId);
    }
}
