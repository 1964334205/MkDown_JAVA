package com.example.mkdown_java.MkDownNote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.model.Img;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServer;
import com.example.mkdown_java.MkDownNote.dao.NoteSubmitDao;
import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.common.Time;
import com.example.mkdown_java.common.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    public int Submit(Note note) {
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
//        note.setNoteParticulars((String) params.get("note_Particulars"));
        if(String.valueOf(note.getNoteId()).equals("0")){
//            note.setNoteId(UUIDUtil.getUUID());
            note.setFoundTime(Time.returnTime());
            flag = this.save(note);

            System.out.println("增加getNoteId： "+note.getNoteId());
            elasticSearchNote.setNoteId(note.getNoteId());
            elasticSearchNote.setNoteTitle(note.getNoteTitle());
            elasticSearchNote.setNoteParticulars(note.getNoteParticulars());
            elasticSearchNote.setUserId(note.getUserId());
            note.setEsId(elasticSearchServer.save(elasticSearchNote));
            flag = this.updateById(note);
        }else {
            elasticSearchNote.setNoteId(note.getNoteId());
            note.setFoundTime(Time.returnTime());
            System.out.println("更新getNoteId： "+note.getNoteId());
            elasticSearchNote.setNoteTitle(note.getNoteTitle());
            elasticSearchNote.setNoteParticulars(note.getNoteParticulars());
            elasticSearchNote.setUserId(note.getUserId());
            note.setEsId(elasticSearchServer.save(elasticSearchNote));

        }
        if (flag == true){
            return note.getNoteId();
        }else {
            return 0;
        }
    }

    public boolean deleteNote(int noteId) {
        Note note = this.getById(noteId);
        elasticSearchServer.delete(note.getEsId());
        String noteImgIds = note.getNoteImgIds();
        if (noteImgIds != null && noteImgIds != "" && noteImgIds != "\"\""){
            String[] imgstring = null;
            imgstring = noteImgIds.replace("\"","").split(",");
            System.out.println(imgstring);
            Img[] imgs = new Img[imgstring.length];
            for (int i = 0; i < imgs.length; i++) {
                flag = imgUploadingUrlService.removeById(imgstring[i]);
                if (flag = false){
                    return false;
                }
            }
        }
        flag = this.removeById(noteId);
        return flag;
    }


    public Note selectNote(int noteId) {
        Note note = new Note();
        if(String.valueOf(note.getNoteId()).equals("0")){
            System.out.println("新增笔记");
        }else {
            note = this.getById(noteId);
            System.out.println(note.toString());
            if (note.getNoteImgIds() != null && note.getNoteImgIds() != "" && note.getNoteImgIds() != "\"\""){
                String[] imgstring = null;
                imgstring = note.getNoteImgIds().replace("\"","").split(",");
                Img[] imgs = new Img[imgstring.length];
                for (int i = 0; i < imgs.length; i++) {
                    imgs[i] = imgUploadingUrlService.getById(imgstring[i]);
                }
                note.setNoteImgs(imgs);
            }
        }
        System.out.println(note.toString());
        return note;
    }

    public List<Note> selectUserNote(int userId) {
//        List<Note> noteList = new LinkedList<>();
//        Map map = new HashMap();
//        map.put("user_id",userId);
//        noteList = this.listByMap(map);
        //构造条件构造器
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        //构造条件
        wrapper.eq("user_id",userId);
        wrapper.orderByAsc("found_time");
        //使用提供的selectList默认方法进行结果查询
        List<Note> noteList = baseMapper.selectList(wrapper);
        System.out.println("selectUserNote数据库读取内容："+noteList.toString());
        return noteList;
    }

    public List<Note> selectNoteEs(String noteTitleAndNoteParticulars,int userId) {
        List<Note> noteList = new LinkedList<>();
        if (noteTitleAndNoteParticulars == null || noteTitleAndNoteParticulars == ""){
            noteList = this.selectUserNote(userId);
        }else {
            List<ElasticSearchNote> elasticSearchNoteList = elasticSearchServer.findByNoteTitleAndNoteParticulars(noteTitleAndNoteParticulars,userId);
            for (int i = 0; i < elasticSearchNoteList.size(); i++) {
                noteList.add(this.getById(elasticSearchNoteList.get(i).getNoteId()));
            }
            System.out.println("selectUserNote数据库读取内容："+noteList.toString());
        }
        return noteList;
    }
}
