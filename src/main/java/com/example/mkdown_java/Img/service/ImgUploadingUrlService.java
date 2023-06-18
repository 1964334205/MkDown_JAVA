package com.example.mkdown_java.Img.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.dao.ImgDao;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.config.QiniuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**、
 * 图片上传
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
//                img.setId(fileName);
//                //七牛云 oss 对象存储    cdn按流量计费，不是按存储空间计费
//
//                //从配置中读取图片路径文件夹
//                img.setUrl("E:\\dev\\mkdown-vue\\imges\\"+ img.getId()+"."+fileNames[fileNames.length-1]);
//                // todo 把图片大小限制为10M
//                try {
//                    System.out.println(file.getOriginalFilename());
//                    BufferedOutputStream out = new BufferedOutputStream(
//                            new FileOutputStream(new File(
//                                    img.getUrl())));
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

    /**
     * 上传图片
     * @param file
     * @return
     */
    public Img  Submit(MultipartFile file) {
        Img img = new Img();
        if (!file.isEmpty()) {
            // 上传图片至七牛云
            img = qiniuService.saveFile(file,img);
        }
        // 保存图片信息
        this.save(img);
        return img;
    }

}
