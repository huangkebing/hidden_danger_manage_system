package com.hkb.hdms.service.impl;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.service.ProcessService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * @author huangkebing
 * 2021/03/26
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Override
    public R upload(MultipartFile processFile) {
        if(processFile.isEmpty()){
            return ReturnConstants.PARAMS_EMPTY;
        }

        String fileName = processFile.getOriginalFilename();
        String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        String filePath = Constants.BPMN_PathMapping;

        filePath = filePath.replace("\\", "/");
        filePath = filePath.replace("file:", "");

        fileName = UUID.randomUUID() + suffixName;
        File file = new File(filePath + fileName);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            processFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(-1,e.getMessage());
        }
        return new R(0, "SUCCESS", fileName);
    }
}
