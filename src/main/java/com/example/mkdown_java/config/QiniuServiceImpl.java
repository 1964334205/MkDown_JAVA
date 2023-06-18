package com.example.mkdown_java.config;

import com.alibaba.fastjson.JSON;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.common.UUIDUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传类
 */
@Service
public class QiniuServiceImpl {
    @Value(value = "${Qiniu.AccessKey}")
    private String accessKey;
    @Value(value = "${Qiniu.SecretKey}")
    private String secretKey;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //测试文件上传
    public void  testUpload() {
        //构造一个带指定 Region 对象的配置类，指定存储区域，和存储空间选择的区域一致
        //
        Configuration cfg = new Configuration(Region.huabei());
        //其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传
        String bucket = "mkdownimg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID() + ".jpg";
        FileInputStream fileInputStream = null;
        try {

            String filePath = "C:\\Users\\wxq\\Desktop\\图片\\2.jpg";
            fileInputStream = new FileInputStream(new File(filePath));
            //得到本地文件的字节数组
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            //认证
            System.out.println("AccessKey:"+accessKey);
            System.out.println("SecretKey:"+secretKey);
            Auth auth = Auth.create(accessKey, secretKey);
            //认证通过后得到token（令牌）
            String upToken = auth.uploadToken(bucket);
            try {
                //上传文件,参数：字节数组，key，token令牌
                //key: 建议我们自已生成一个不重复的名称
                Response response = uploadManager.put(bytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(),
                        DefaultPutRet.class);
                logger.info("上传成功{}", JSON.toJSONString(putRet));
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    logger.error("上传失败{}",ex2.getMessage());
                }
            }
        } catch (IOException ex) {
            logger.error("上传文件失败", ex);
        }

    }

    /**
     * 上传图片至七牛云
     */
    public Img saveFile(MultipartFile uploadFile,Img img) {
        // 生成图片id
        img.setId(UUIDUtil.getUUID()) ;
        // 设置上传大区地址(北美)
        Configuration cfg = new Configuration(Region.beimei());
        // 创建上传类
        UploadManager uploadManager = new UploadManager(cfg);
        // 设置上传空间名
        String bucket = "whaiwai1";
        // 获取文件名
        String fileName = uploadFile.getOriginalFilename();
        // 设置新文件名+后缀
        String key = img.getId() + fileName.substring(fileName.lastIndexOf("."));
        // 设置上传秘钥
        Auth auth = Auth.create(accessKey, secretKey);
        // 生成上传token
        String upToken = auth.uploadToken(bucket);
        try {
            // 开始上传图片
            Response response = uploadManager.put(uploadFile.getBytes(), key, upToken);
            // 上传完回复对象
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            // 设置文件URL和文件在七牛云的文件名
            img.setUrl("http://rw34jwhy2.bkt.gdipper.com/" + putRet.key);
            // 返回文件信息
            return img;
        } catch (QiniuException e) {
            logger.error("上传文件失败", e);
            logger.error(JSON.toJSONString(e.response));
        } catch (IOException e) {
            logger.error("上传文件失败", e);
        }
        return null;
    }

//    public List<String> saveFile(MultipartFile[] uploadFiles) {
//        List<String> list = new ArrayList<>();
//        for (MultipartFile uploadFile : uploadFiles) {
//
//            list.add(saveFile(uploadFile));
//        }
//        return list;
//    }
}
