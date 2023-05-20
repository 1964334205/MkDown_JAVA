package com.example.mkdown_java.Img.controller;

import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import com.example.mkdown_java.config.QiniuServiceImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/Img")
public class ImgUploadingUrlController {

    @Autowired
    private ImgUploadingUrlService imgUploadingUrlService;
    @ResponseBody
    @PostMapping("/Submit")
    public Img Submit( @RequestParam(value = "files") MultipartFile file){
        Img img = imgUploadingUrlService.Submit(file);
        return img;
    }

//    @ResponseBody
//    @GetMapping("/Submits")
//    public String Submits(){
//        qiniuService.testUpload();
//        return "true";
//    }
}
