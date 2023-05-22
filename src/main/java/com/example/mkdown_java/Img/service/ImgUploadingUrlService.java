package com.example.mkdown_java.Img.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.dao.ImgDao;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.config.QiniuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 库存交旧待办表 服务实现类
 * </p>
 *
 * @author yj
 * @since 2019-11-21
 */
@Service
public class ImgUploadingUrlService extends ServiceImpl<ImgDao, Img>{

    @Autowired
    private QiniuServiceImpl qiniuService;
    //    @autowired
//    string picPath;
//    public static  Img  Submit(MultipartFile files) {
//            Img img = new Img();
//            MultipartFile file = files;
//
//            if (!file.isEmpty()) {
//                // url加上文件后缀名
//                String fileName= UUIDUtil.getUUID();
//                System.out.print("generated fileName: " +file.getOriginalFilename());
//                String[] fileNames = file.getOriginalFilename().split("\\.");
//                img.setImgId(fileName);
//                //七牛云 oss 对象存储    cdn按流量计费，不是按存储空间计费
//
//                //从配置中读取图片路径文件夹
//                img.setImgUrl("E:\\dev\\mkdown-vue\\imges\\"+ img.getImgId()+"."+fileNames[fileNames.length-1]);
//                // todo 把图片大小限制为10M
//                try {
//                    System.out.println(file.getOriginalFilename());
//                    BufferedOutputStream out = new BufferedOutputStream(
//                            new FileOutputStream(new File(
//                                    img.getImgUrl())));
//                    //logback;
//                    System.out.println(file.getName());
//                    out.write(file.getBytes());
////                    out.flush();
//                    out.close();
//                } catch (FileNotFoundException e) {
//                    // todo 打印到日志里
//                    e.printStackTrace();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        return img;
//    }

    public Img  Submit(MultipartFile file) {
        Img img = new Img();
        if (!file.isEmpty()) {
            img = qiniuService.saveFile(file,img);
        }
        this.save(img);
        return img;
    }

}
