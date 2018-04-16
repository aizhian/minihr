package com.chinasoftinc.minihr.web.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Aizhanglin on 2018/4/16.
 */
@RestController
public class ProjectController {
    @PutMapping("/project/exportExcel")
    public void exportExcel(@RequestParam("file") MultipartFile file){

    }


}
