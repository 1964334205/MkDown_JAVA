package com.example.mkdown_java.MkDownNote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.Img.service.ImgUploadingUrlService;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServerImpl;
import com.example.mkdown_java.MkDownNote.dao.NoteSubmitDao;
import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.common.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

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
    private ElasticSearchServerImpl elasticSearchServerImpl;

    /**
     * 保存笔记
     * @param note
     * @return
     */
    public int Submit(Note note) {
        // 创建es保存对象
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
//        note.setParticulars((String) params.get("note_Particulars"));
        // 判断笔记id是否为空  执行新增或修改笔记
        if(note.getId() == null){
//            note.setId(UUIDUtil.getUUID());
            // 笔记创建时间
            note.setFoundTime(Time.returnTime());
            // 保存笔记
            flag = this.save(note);
            logger.debug("增加getNoteId： "+note.getId());
            // 设置es笔记对象信息
            elasticSearchNote.setId(note.getId());
            elasticSearchNote.setTitle(note.getTitle());
            elasticSearchNote.setParticulars(note.getParticulars());
            elasticSearchNote.setUserId(note.getUserId());
            //保存es笔记信息，设置笔记esid
            note.setEsId(elasticSearchServerImpl.save(elasticSearchNote));
            // 保存笔记
            flag = this.updateById(note);
        }else {
            // 设置es对象id
            elasticSearchNote.setId(note.getId());
            // 设置修改时间
            note.setFoundTime(Time.returnTime());
            logger.debug("更新getNoteId： "+note.getId());
            // 设置es对象信息
            elasticSearchNote.setTitle(note.getTitle());
            elasticSearchNote.setParticulars(note.getParticulars());
            elasticSearchNote.setUserId(note.getUserId());
            // 保存es对象，设置笔记的esid
            note.setEsId(elasticSearchServerImpl.save(elasticSearchNote));
            //保存笔记
            flag = this.updateById(note);
        }
        if (flag == true){
            // 返回笔记id
            return note.getId();
        }else {
            return 0;
        }
    }

    /**
     * 根据id删除笔记
     * @param id
     * @return
     */
    public boolean deleteNote(Integer id) {
        Note note = this.getById(id);
        elasticSearchServerImpl.delete(note.getEsId());
//        String imgIds = note.getImgIds();
//        if (imgIds != null && imgIds != "" && imgIds != "\"\""){
//            String[] imgstring = null;
//            imgstring = imgIds.replace("\"","").split(",");
//              logger.debug("删除笔记：" + imgstring);
//            Img[] imgs = new Img[imgstring.length];
//            for (int i = 0; i < imgs.length; i++) {
//                flag = imgUploadingUrlService.removeById(imgstring[i]);
//                if (flag = false){
//                    return false;
//                }
//            }
//        }
        flag = this.removeById(id);
        return flag;
    }

    /**
     * 删除用户所有笔记
     * @param id
     * @return
     */
    public boolean deleteNoteAll(Integer id) {
        selectUserNote(id).forEach(lis ->{
            deleteNote(lis.getId());
        });
        return flag;
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    public Note selectNote(Integer id) {
        Note note = new Note();
        if(id == null){
            logger.debug("新增笔记");
        }else {
            note = this.getById(id);
            logger.debug(note.toString());
//            if (note.getImgIds() != null && note.getImgIds() != "" && note.getImgIds() != "\"\""){
//                String[] imgstring = null;
//                imgstring = note.getImgIds().replace("\"","").split(",");
//                Img[] imgs = new Img[imgstring.length];
//                for (int i = 0; i < imgs.length; i++) {
//                    imgs[i] = imgUploadingUrlService.getById(imgstring[i]);
//                }
//                note.setIdmgs(imgs);
//            }
        }
        logger.debug(note.toString());
        return note;
    }

//    public Note selectNoteHighlightField(Integer id) {
//        Note note = new Note();
//        if(id == null){
//            logger.debug("新增笔记");
//        }else {
//            note = this.getById(id);
//            logger.debug(note.toString());
//            if (note.getImgIds() != null && note.getImgIds() != "" && note.getImgIds() != "\"\""){
//                String[] imgstring = null;
//                imgstring = note.getImgIds().replace("\"","").split(",");
//                Img[] imgs = new Img[imgstring.length];
//                for (int i = 0; i < imgs.length; i++) {
//                    imgs[i] = imgUploadingUrlService.getById(imgstring[i]);
//                }
//                note.setIdmgs(imgs);
//            }
//        }
//        logger.debug(note.toString());
//        return note;
//    }

    /**
     * 查询用户全部笔记
     * @param id
     * @return
     */
    public List<Note> selectUserNote(Integer id) {
//        List<Note> noteList = new LinkedList<>();
//        Map map = new HashMap();
//        map.put("user_id",id);
//        noteList = this.listByMap(map);
        //构造条件构造器
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        //构造条件
        wrapper.eq("user_id",id);
        wrapper.orderByAsc("found_time");
        //使用提供的selectList默认方法进行结果查询
        List<Note> noteList = baseMapper.selectList(wrapper);
        logger.debug("selectUserNote数据库读取内容："+noteList.toString());
        return noteList;
    }

    /**
     * 使用es搜索笔记
     * @param noteTitleAndNoteParticulars
     * @param userId
     * @return
     */
    public List<Note> selectNoteEs(String noteTitleAndNoteParticulars,Integer userId) {
        List<Note> noteList = new LinkedList<>();
        // 如果关键字为空则返回用户全部笔记
        if (noteTitleAndNoteParticulars == null || noteTitleAndNoteParticulars == ""){
            noteList = this.selectUserNote(userId);
        }else {
            logger.debug("selectNoteEs高亮查询");
            // 使用es搜索笔记
            List<SearchHit<ElasticSearchNote>> elasticSearchNoteList = elasticSearchServerImpl.findByNoteTitleAndNoteParticulars(noteTitleAndNoteParticulars,userId);
            // 循环处理笔记信息
            for (int i = 0; i < elasticSearchNoteList.size(); i++) {
                logger.debug(String.valueOf(elasticSearchNoteList.get(i).getContent().getId()));
                // 根据es笔记id查询笔记信息
                Note note = this.getById(elasticSearchNoteList.get(i).getContent().getId());
                //如果笔记信息不存在则略过
                if(note == null){

                }else {
                    if (elasticSearchNoteList.get(i).getHighlightFields() != null){
                        //获取es查询笔记命中到的字段
                        Map<String, List<String>> getHighlightFieldsMap = elasticSearchNoteList.get(i).getHighlightFields();
                        // 设置笔记标题高亮
                        if (getHighlightFieldsMap.get("title") != null){
                            String noteTitleHighlights = "";
                            for (String setNoteTitle:getHighlightFieldsMap.get("title")) {
                                noteTitleHighlights = noteTitleHighlights+setNoteTitle;
                            }
                            note.setTitleHighlight(noteTitleHighlights);
                        }
                        // 设置笔记内容高亮
                        if (getHighlightFieldsMap.get("particulars") != null){
                            String noteParticularsHighlights = "";
                            for (String getHighlightFields:getHighlightFieldsMap.get("particulars")) {
                                noteParticularsHighlights = noteParticularsHighlights+getHighlightFields;
                            }
                            note.setParticularsHighlight(noteParticularsHighlights);
                        }
                    }
                    // 添加笔记至list
                    noteList.add(note);
                }
            }
            logger.debug("selectUserNote数据库读取内容："+noteList.toString());
        }
        return noteList;
    }
}
