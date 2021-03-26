package com.hkb.hdms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 流程定义相关接口
 *
 * @author huangkebing
 * 2021/03/26
 */
@Controller
@RequestMapping("/processDefine")
public class ProcessDefinitionController {

    @RequestMapping("/process.html")
    public Object processPage(){
        return "process/process";
    }
}
