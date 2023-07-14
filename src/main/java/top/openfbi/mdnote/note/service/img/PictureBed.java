package top.openfbi.mdnote.note.service.img;

import com.qiniu.common.QiniuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.note.model.Image;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class PictureBed {

    @Value(value = "${Loacl.Path}")
    private String PATH;
    @Value(value = "${Loacl.FileUrl}")
    private String FileUrl;

    private static final Logger logger
            = LoggerFactory.getLogger(PictureBed.class);
    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public Image upload(MultipartFile file) throws ResultException, QiniuException {

        ImgFilePictureBedInterface pictureBed = ImgFilePictureBedFactory.createImgFilePictureBed();
        return pictureBed.save(file);
    }
}
