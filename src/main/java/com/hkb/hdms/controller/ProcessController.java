package com.hkb.hdms.controller;

import com.hkb.hdms.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upload")
    @ResponseBody
    public Object upload(@RequestParam("processFile") MultipartFile multipartFile){
        return processService.upload(multipartFile);
    }
}
