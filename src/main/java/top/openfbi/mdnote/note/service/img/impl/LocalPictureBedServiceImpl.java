package top.openfbi.mdnote.note.service.img.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.openfbi.mdnote.common.ResultStatus;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.note.model.Image;
import top.openfbi.mdnote.note.service.img.ImgFilePictureBedInterface;
import top.openfbi.mdnote.utils.Hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

@Component
@Service
public class LocalPictureBedServiceImpl implements ImgFilePictureBedInterface {

    private String FileUrl;

    public String getFileUrl() {
        return FileUrl;
    }

    @Value(value = "${Loacl.FileUrl}")
    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getPATH() {
        return PATH;
    }

    @Value(value = "${Loacl.Path}")
    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    private String PATH;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Image uploadPrepare(MultipartFile uploadFile,int suffix) throws ResultException {
        Image image = new Image();
        // 获取文件名
        String fileName = uploadFile.getOriginalFilename();

        // 保存文件后缀类型
        image.setExtension(fileName.substring(fileName.lastIndexOf(".")));
        //设置文件id
        try {
            byte [] fileByte = uploadFile.getBytes();
            String fileMd5 = Hash.md5(fileByte);
            image.setMd5(fileMd5 +"-" + suffix);

            image.setFileUrl(FileUrl + fileMd5 + "-"+suffix + image.getExtension());
            if (suffix == 0){
                image.setMd5(fileMd5);
                image.setFileUrl(FileUrl + fileMd5 + image.getExtension());
            }

        } catch (IOException e) {
            logger.info("文件读取错误，上传失败");
            throw new ResultException(ResultStatus.FILE_READ_FALL);
        }


        // 设置新文件名+后缀
        String imgNewName = "";


        imgNewName = image.getMd5() + image.getExtension();
        // 设置文件访问URL   //获取上传文件名称
        uploadFile(uploadFile, imgNewName);//文件上传成功
        return image;
    }


    public void uploadFile(MultipartFile uploadFile, String imgNewName) throws ResultException {
        //判断该路径是否存在

        File path = new File(PATH);
        if (!path.exists()) {
            //如果这个文件夹不存在的话,就创建这个文件
            path.mkdirs();
        }
        //完成文件上传
        File file = new File(PATH, imgNewName);
        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            logger.warn("文件保存错误，上传失败");
            throw new ResultException(ResultStatus.FILE_SAVE_FALL);
        }
    }


    @Override
    public Image save(MultipartFile file) throws ResultException {

        //判断文件是否为空
        if(file.isEmpty()){
            logger.error("文件内容为空，上传失败");
            throw new ResultException(ResultStatus.FILE_CONTENT_NULL);
        }
        // 获取文件名
        String fielName = file.getOriginalFilename();
        //获取文件后缀
        String extension = fielName.substring(fielName.lastIndexOf("."));
        if (fielName == null || fielName.isEmpty()) {
            logger.info("文件名为空，上传失败");
            throw new ResultException(ResultStatus.FILE_NAME_FALL);
        }
        int suffix = 0;
        try {
            // 用文件内容计算md5
            byte [] fileByte = file.getBytes();
            String fileMd5 = Hash.md5(fileByte);
            // 如果文件存在，则在md5后添加后缀（-1/2/3），循环读取，直到无法找到增加后缀文件
            for (;suffix < 10 ; suffix++) {
                File filePath = null;
                String fileUrl = "";
                // 用md5+文件后缀名 去文件系统上进行读取
                if(suffix == 0){
                    filePath = new File(PATH+"/"+ fileMd5 + extension);
                    fileUrl = FileUrl+ fileMd5 + extension;
                }else {
                    filePath = new File(PATH+"/"+fileMd5+ "-"+suffix+ extension);
                    fileUrl = FileUrl+ fileMd5 + "-"+suffix + extension;
                }
                FileInputStream fileInputStream = new FileInputStream(filePath);
                if (Arrays.equals(file.getBytes(),fileInputStream.readAllBytes())){
                    Image image = new Image();
                    image.setFileUrl(fileUrl);
                    return image;
                }
            }
            // 默认10次
            throw new ResultException(ResultStatus.FILE_HASH_COLLISION_TOO_MUCH);
        } catch (FileNotFoundException e) {
            // 如果文件不存在，则写入文件，返回md5+文件后缀名
            return uploadPrepare(file,suffix);
        } catch (IOException e) {
            logger.error("文件读取错误，上传失败");
            throw new ResultException(ResultStatus.FILE_READ_FALL);
        }
    }
}
