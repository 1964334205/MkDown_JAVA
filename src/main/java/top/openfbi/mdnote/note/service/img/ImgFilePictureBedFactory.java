package top.openfbi.mdnote.note.service.img;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.openfbi.mdnote.note.service.img.impl.LocalPictureBedServiceImpl;
import top.openfbi.mdnote.note.service.img.impl.QiNiuPictureBedServiceImpl;

import java.util.HashMap;
import java.util.Map;

@Component
public class ImgFilePictureBedFactory {
    private static int PictureBed;
    private static ImgFilePictureBedFactory imgFilePictureBedFactory;
    public static int getPictureBed() {
        return PictureBed;
    }

    @Value(value = "${FileUploading.PictureBed}")
    public void setPictureBed(int pictureBed) {
        ImgFilePictureBedFactory.PictureBed = pictureBed;
    }


    @Autowired
    private QiNiuPictureBedServiceImpl qiniuService;
    @Autowired
    private LocalPictureBedServiceImpl localServer;

    private static final Map<Integer, ImgFilePictureBedInterface> createImgFilePictureBed = new HashMap<>();

    @PostConstruct
    public void init() {
        imgFilePictureBedFactory = this;
        imgFilePictureBedFactory.createImgFilePictureBed.put(0,this.qiniuService);
        imgFilePictureBedFactory.createImgFilePictureBed.put(1,this.localServer);
    }

    @PostConstruct
    public static ImgFilePictureBedInterface createImgFilePictureBed(){
        return imgFilePictureBedFactory.createImgFilePictureBed.get(ImgFilePictureBedFactory.PictureBed);
    }
}
