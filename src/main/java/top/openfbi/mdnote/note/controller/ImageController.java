package top.openfbi.mdnote.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.note.model.Image;
import top.openfbi.mdnote.note.service.img.ImageService;
import top.openfbi.mdnote.note.service.img.PictureBed;

import java.io.IOException;

/**
 * 图片上传
 */
@RestController
@RequestMapping("/img")
@ResponseResultBody
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private PictureBed pictureBed;

    /**
     *  上传图片
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/upload")
    public Image upload(@RequestParam(value = "files") MultipartFile file) throws IOException, ResultException {
        return imageService.imgSave(file);
    }
}
