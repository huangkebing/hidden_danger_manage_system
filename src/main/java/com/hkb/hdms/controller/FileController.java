package com.hkb.hdms.controller;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.ProblemInfoMapper;
import com.hkb.hdms.model.pojo.ProblemInfo;
import com.hkb.hdms.model.pojo.User;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * @author huangkebing
 * 2021/04/13
 */
@Controller
@RequestMapping("file")
public class FileController {

    private final HttpSession session;

    private final ProblemInfoMapper problemInfoMapper;

    @Autowired
    public FileController(HttpSession session, ProblemInfoMapper problemInfoMapper) {
        this.session = session;
        this.problemInfoMapper = problemInfoMapper;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object uploadFile(@RequestParam("file") List<MultipartFile> files, @RequestParam(required = false) String flag, @RequestParam(required = false) Long problemId){
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
            if(!"remarks".equals(flag)){
                User user = (User) session.getAttribute(Constants.LOGIN_USER_KEY);

                ProblemInfo info = new ProblemInfo();
                info.setContext(file.getOriginalFilename());
                info.setFilePath("/hdms/upload/" + month + "/" + fileName);
                info.setEmail(user.getEmail());
                info.setUserId(user.getId());
                info.setUsername(user.getName());
                info.setProblemId(problemId);
                if(suffixName.equals(".jpg") || suffixName.equals(".png")
                        || suffixName.equals(".jpeg") || suffixName.equals("gif") || suffixName.equals("bmp")){
                    info.setType(2);
                }
                else {
                    info.setType(3);
                }
                problemInfoMapper.insert(info);
            }
            res.add(dataMap);
        }
        if(!"remarks".equals(flag)){
            return ReturnConstants.SUCCESS;
        }
        R success = new R(0, "Success");

        success.setData(res);
        return success;
    }
}
