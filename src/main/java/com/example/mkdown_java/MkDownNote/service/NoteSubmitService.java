package com.example.mkdown_java.MkDownNote.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.MkDownNote.dao.NoteSubmitDao;
import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.common.UUIDUtil;
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
    boolean flag = false;
    @Autowired
    private ImgUploadingUrlService imgUploadingUrlService;
    public boolean Submit(Note note) {

//        note.setNoteParticulars((String) params.get("note_Particulars"));
        if(note.getNoteId() == null){
            note.setNoteId(UUIDUtil.getUUID());
            flag = this.save(note);
        }else {
            flag = this.updateById(note);
        }

        return flag;
    }

    public Note selectNote(String noteId) {
        Note note = new Note();
        note = this.getById(noteId);
        String[] imgstring = note.getNoteImgIds().replace("\"","").split(",");
        System.out.println(imgstring.toString());
        Img[] imgs = new Img[imgstring.length];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = imgUploadingUrlService.getById(imgstring[i]);
        }
        note.setNoteImgs(imgs);
        System.out.println("note："+note);
        System.out.println("数据库读取内容："+note.getNoteParticulars());
        return note;
    }

    public List<Note> selectUserNote(String userId) {
        List<Note> noteList = new LinkedList<>();
        Map map = new HashMap();
        map.put("user_id",userId);
        noteList = this.listByMap(map);
        System.out.println("selectUserNote数据库读取内容："+noteList.toString());
        return noteList;
    }
}
