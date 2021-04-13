package com.hkb.hdms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huangkebing
 * 2021/04/13
 */
@Controller
@RequestMapping("file")
public class FileController {

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object uploadFile(MultipartFile file){
        return null;
    }
}
