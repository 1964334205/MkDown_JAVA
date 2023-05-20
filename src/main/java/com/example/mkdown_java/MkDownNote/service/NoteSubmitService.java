package com.example.mkdown_java.MkDownNote.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.MkDownNote.dao.NoteSubmitDao;
import com.example.mkdown_java.MkDownNote.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 库存交旧待办表 服务实现类
 * </p>
 *
 * @author yj
 * @since 2019-11-21
 */
@Service
public class NoteSubmitService extends ServiceImpl<NoteSubmitDao, Note> {

    @Autowired
    private ImgUploadingUrlService imgUploadingUrlService;
    public boolean Submit(Note note) {
//        note.setNoteParticulars((String) params.get("note_Particulars"));
        boolean flag = this.save(note);
        return flag;
    }

    public Note selectNote(Integer id) {
        Note note = new Note();
        note = this.getById(id);
        String[] imgstring = note.getNoteImgIds().split(",");
        Img[] imgs = new Img[imgstring.length];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = imgUploadingUrlService.getById(imgs[i]);
        }
        note.setImgs(imgs);
        System.out.println("note："+note);
        System.out.println("数据库读取内容："+note.getNoteParticulars());
        return note;
    }
}
