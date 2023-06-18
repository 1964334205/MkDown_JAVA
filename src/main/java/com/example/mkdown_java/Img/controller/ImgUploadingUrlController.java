package com.example.mkdown_java.Img.controller;

import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.config.ResponseResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 */
@RestController
@RequestMapping("/Img")
@ResponseResultBody
public class ImgUploadingUrlController {

    @Autowired
    private ImgUploadingUrlService imgUploadingUrlService;

    /**
     *  上传图片
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/Submit")
    public Img Submit( @RequestParam(value = "files") MultipartFile file){
        return imgUploadingUrlService.Submit(file);
    }

//    @ResponseBody
//    @GetMapping("/Submits")
//    public String Submits(){
//        qiniuService.testUpload();
//        return "true";
//    }
}
