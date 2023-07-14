package top.openfbi.mdnote.note.service.img.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.openfbi.mdnote.common.ResultStatus;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.note.model.Image;
import top.openfbi.mdnote.note.service.img.ImgFilePictureBedInterface;
import top.openfbi.mdnote.utils.Hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传类
 */
@Component
@Service
public class QiNiuPictureBedServiceImpl implements ImgFilePictureBedInterface {
    // 上传秘钥

    private String ACCESS_KEY;

    private String SECRET_KEY;

    public String getACCESS_KEY() {
        return ACCESS_KEY;
    }
    @Value(value = "${Qiniu.AccessKey}")
    public void setACCESS_KEY(String ACCESS_KEY) {
        this.ACCESS_KEY = ACCESS_KEY;
    }
    public String getSECRET_KEY() {
        return SECRET_KEY;
    }
    @Value(value = "${Qiniu.SecretKey}")
    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }
    public String getBUCKET() {
        return BUCKET;
    }
    @Value(value = "${Qiniu.Bucket}")
    public void setBUCKET(String BUCKET) {
        this.BUCKET = BUCKET;
    }
    // 设置上传空间名
    private String BUCKET;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    //测试文件上传
    public void  testUpload() {
        //构造一个带指定 Region 对象的配置类，指定存储区域，和存储空间选择的区域一致
        //
        Configuration cfg = new Configuration(Region.beimei());
        //其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID() + ".jpg";
        FileInputStream fileInputStream;
        try {

            String filePath = "C:\\Users\\wxq\\Desktop\\图片\\2.jpg";
            fileInputStream = new FileInputStream(filePath);
            //得到本地文件的字节数组
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            //认证
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            //认证通过后得到token（令牌）
            String upToken = auth.uploadToken(BUCKET);
            try {
                //上传文件,参数：字节数组，key，token令牌
                //key: 建议我们自已生成一个不重复的名称
                Response response = uploadManager.put(bytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(),
                        DefaultPutRet.class);
                logger.info("上传成功: {}", JSON.toJSONString(putRet));
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    logger.error("上传失败: {}",ex2.getMessage());
                }
            }
        } catch (IOException ex) {
            logger.error("上传文件失败: {}", ex);
        }

    }

    /**
     * 上传图片至七牛云
     */
    public Image save(MultipartFile uploadFile) throws ResultException {
        Image image = new Image();
        byte [] fileBytes = null;
        // 生成图片id
        // 使用md5计算文件哈希值
        //设置文件id
        try {
            fileBytes = uploadFile.getBytes();
            image.setMd5(Hash.md5(fileBytes));

        } catch (IOException e) {
            logger.warn("文件读取错误，上传失败");
            throw new ResultException(ResultStatus.FILE_READ_FALL);
        }

        // 设置上传大区地址(北美)
        Configuration cfg = new Configuration(Region.beimei());
        // 创建上传类
        UploadManager uploadManager = new UploadManager(cfg);
        // 获取文件名
        image.setName(uploadFile.getOriginalFilename());
        // 保存文件后缀类型
        image.setExtension(image.getName().substring(image.getName().lastIndexOf(".")));
        // 设置上传秘钥
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 生成上传token
        String upToken = auth.uploadToken(BUCKET);

        try {
            // 开始上传图片
            Response response = uploadManager.put(fileBytes, image.getMd5()+ image.getExtension(), upToken);
        }catch (QiniuException qiniuException){
            logger.error("七牛云图片上传失败");
            throw new ResultException(ResultStatus.QI_NIU_FILE_UPLOAD_FALL);
        }



        // 上传完回复对象
        DefaultPutRet putRet = JSON.parseObject(fileBytes, DefaultPutRet.class);
        // 设置文件URL和文件在七牛云的文件名
        image.setFileUrl("http://rw34jwhy2.bkt.gdipper.com/" + putRet.key);
        // 返回文件信息
        return image;
    }
}
