package top.openfbi.mdnote.note.service.img;

import com.qiniu.common.QiniuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.note.model.Image;

/**、
 * 图片上传
 */
@Service
public class ImageService{

    @Autowired
    private PictureBed pictureBed;
    /**
     * 上保存文件信息
     * @return
     */

    public Image imgSave(MultipartFile file) throws QiniuException, ResultException {
        // 保存图片信息
        return pictureBed.upload(file);
    }


}
