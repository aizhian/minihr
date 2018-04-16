package com.chinasoftinc.minihr.web.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Aizhanglin on 2018/4/16.
 */
public interface EmployeeService {
    void readAndSaveFromExcel(MultipartFile multipartFile);
}
