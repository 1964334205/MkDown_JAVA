package com.example.mkdown_java.MkDownNote.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServer;
import com.example.mkdown_java.MkDownNote.dao.NoteSubmitDao;
import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.common.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private static final Logger logger
            = LoggerFactory.getLogger(NoteSubmitService.class);
    boolean flag = false;
    @Autowired
    private ImgUploadingUrlService imgUploadingUrlService;

    @Autowired
    private ElasticSearchServer elasticSearchServer;

    public boolean Submit(Note note) {
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
//        note.setNoteParticulars((String) params.get("note_Particulars"));
        if(note.getNoteId() == null){
            note.setNoteId(UUIDUtil.getUUID());
            elasticSearchNote.setNoteId(note.getNoteId());
            elasticSearchNote.setNoteTitle(note.getNoteTitle());
            elasticSearchNote.setNoteParticulars(note.getNoteParticulars());
            note.setEsId(elasticSearchServer.save(elasticSearchNote));
            flag = this.save(note);
        }else {
            elasticSearchNote.setNoteId(note.getNoteId());
            elasticSearchNote.setNoteTitle(note.getNoteTitle());
            elasticSearchNote.setNoteParticulars(note.getNoteParticulars());
            note.setEsId(elasticSearchServer.save(elasticSearchNote));
            flag = this.updateById(note);
        }

        return flag;
    }

    public Note selectNote(String noteId) {
        Note note = new Note();
        System.out.println(note.toString());
        if(noteId == "0"){
            System.out.println("新增笔记");
        }else {
            note = this.getById(noteId);
            String[] imgstring = note.getNoteImgIds().replace("\"","").split(",");
            System.out.println(imgstring.toString());
            Img[] imgs = new Img[imgstring.length];
            for (int i = 0; i < imgs.length; i++) {
                imgs[i] = imgUploadingUrlService.getById(imgstring[i]);
            }
            note.setNoteImgs(imgs);

        }

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
