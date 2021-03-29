package com.hkb.hdms.service;

import com.hkb.hdms.base.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huangkebing
 * 2021/03/26
 */
public interface ProcessService {

    R upload(MultipartFile processFile);
}
