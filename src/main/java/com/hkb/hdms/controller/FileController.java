package com.hkb.hdms.controller;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * @author huangkebing
 * 2021/04/13
 */
@Controller
@RequestMapping("file")
public class FileController {

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object uploadFile(List<MultipartFile> files, @RequestParam(required = false) String flag){
        if(!"remarks".equals(flag)){

        }

        LocalDate now = LocalDate.now();
        String month = now.getYear() + "年" + now.getMonthOfYear() + "月";
        String path = Constants.FILE_UPLOADMapping + month + "/";

        List<Map<String, Object>> res = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;

            File createFile = new File(path + fileName);

            if (!createFile.getParentFile().exists()) {
                createFile.getParentFile().mkdirs();
            }

            try {
                file.transferTo(createFile);
            } catch (Exception e) {
                e.printStackTrace();
                return ReturnConstants.FAILURE;
            }
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("url", "/hdms/upload/" + month + "/" + fileName);
            res.add(dataMap);
        }
        R success = new R(0, "Success");

        success.setData(res);
        return success;
    }
}
