package com.example.mkdown_java.MkDownNote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServerImpl;
import com.example.mkdown_java.MkDownNote.dao.NoteSubmitDao;
import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.User.util.UserSession;
import com.example.mkdown_java.common.ResultStatus;
import com.example.mkdown_java.common.Time;
import com.example.mkdown_java.common.exception.ResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * note笔记业务操作
 */
@Service
public class NoteSubmitService extends ServiceImpl<NoteSubmitDao, Note> {

    private static final Logger logger
            = LoggerFactory.getLogger(NoteSubmitService.class);
    boolean flag = false;

    /**
     * es数据库操作server
     */
    @Autowired
    private ElasticSearchServerImpl elasticSearchServerImpl;

    /**
     * 保存笔记
     * @param note
     * @return
     */
    public int Submit(Note note) throws ResultException {
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
            if (!flag){
                throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
            }
            logger.debug("增加getNoteId： "+note.getId());
            // 设置es笔记对象信息
            elasticSearchNote.setId(note.getId());
            elasticSearchNote.setTitle(note.getTitle());
            elasticSearchNote.setParticulars(note.getParticulars());
            elasticSearchNote.setUserId(note.getUserId());
            //保存es笔记信息，设置笔记esid
            elasticSearchNote = elasticSearchServerImpl.save(elasticSearchNote);
            // 判断es是否保存成功
            if (elasticSearchNote == null){
                throw new ResultException(ResultStatus.INTERNAL_ELASTIC_CONNECT_FAIL);
            }
            note.setEsId(elasticSearchNote.getId());
            // 保存笔记
            flag = this.updateById(note);
            // 判断mysql是否保存成功
            if (!flag){
                throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
            }
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
            elasticSearchNote = elasticSearchServerImpl.save(elasticSearchNote);
            // 判断es是否保存成功
            if (elasticSearchNote == null){
                throw new ResultException(ResultStatus.INTERNAL_ELASTIC_CONNECT_FAIL);
            }
            note.setEsId(elasticSearchNote.getId());
            //保存笔记
            flag = this.updateById(note);
            // 判断mysql是否保存成功
            if (!flag){
                throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
            }
        }
        return note.getId();
    }

    /**
     * 根据id删除笔记
     * @param id
     * @return
     */
    public boolean deleteNote(Integer id) throws ResultException {
        // 获取笔记信息
        Note note = this.getById(id);
        // 获取用户信息
        User user = new UserSession().get();
        // 判断用户信息是否和笔记中的用户信息一致
        if(user.getId() != note.getUserId()){
            // 用户信息不一致，返回用户无权限访问异常
            throw new ResultException(ResultStatus.USER_NO_PERMISSION);
        }
        // 删除es文档数据
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
        //删除mysql数据
        flag = this.removeById(id);
        //判断mysql是否删除成功
        if (!flag){
            throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
        }
        return flag;
    }

    /**
     * 删除用户所有笔记
     * @param id
     * @return
     */
    public boolean deleteNoteAll(Integer id) throws ResultException {
        //获取用户所有笔记  list
        selectUserNote(id).forEach(lis ->{
            try {
                // 删除用户笔记
                deleteNote(lis.getId());
            } catch (ResultException e) {
                // 抛出用户无权限操作异常
                throw new RuntimeException(e);
            }

        });
        return flag;
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    public Note selectNote(Integer id) throws ResultException {
        Note note = new Note();
        // 查询用户笔记
        note = this.getById(id);
//            if (note.getImgIds() != null && note.getImgIds() != "" && note.getImgIds() != "\"\""){
//                String[] imgstring = null;
//                imgstring = note.getImgIds().replace("\"","").split(",");
//                Img[] imgs = new Img[imgstring.length];
//                for (int i = 0; i < imgs.length; i++) {
//                    imgs[i] = imgUploadingUrlService.getById(imgstring[i]);
//                }
//                note.setIdmgs(imgs);
//            }
        // 判断笔记是否为空
        if(note == null){
            // 返回笔记不存在异常
            throw  new ResultException(ResultStatus.NOTE_FAIL);
        }
        // 获取用户信息
        User user = new UserSession().get();
        // 盘点笔记的用户id和用户信息是否一致
        if(user.getId() != note.getUserId()){
            //返回用户无权限异常
            throw new ResultException(ResultStatus.USER_NO_PERMISSION);
        }
        logger.debug(note.toString());
        return note;
//        logger.debug(note.toString());
//        return note;
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
    public List<Note> selectUserNote(Integer id) throws ResultException {
//        List<Note> noteList = new LinkedList<>();
//        Map map = new HashMap();
//        map.put("user_id",id);
//        noteList = this.listByMap(map);
        //构造条件构造器
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        //构造条件
        wrapper.eq("user_id",id);
        wrapper.orderByDesc("found_time");
        //使用提供的selectList默认方法进行结果查询
        List<Note> noteList = baseMapper.selectList(wrapper);
//        if (noteList == null){
//            throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
//        }
        logger.debug("selectUserNote数据库读取内容："+noteList.toString());
        return noteList;
    }

    /**
     * 使用es搜索笔记
     * @param noteTitleAndNoteParticulars
     * @param userId
     * @return
     */
    public List<Note> selectNoteEs(String noteTitleAndNoteParticulars,Integer userId) throws ResultException {
        List<Note> noteList = new LinkedList<>();
        // 如果查询关键字为空则返回用户全部笔记
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
                    // 判断是否查询到高亮内容
                    if (elasticSearchNoteList.get(i).getHighlightFields() != null){
                        //获取es查询命中到的高亮笔记内容
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
                    // 添加笔记至noteList
                    noteList.add(note);
                }
            }
            logger.debug("selectUserNote数据库读取内容："+noteList.toString());
        }
        return noteList;
    }
}
