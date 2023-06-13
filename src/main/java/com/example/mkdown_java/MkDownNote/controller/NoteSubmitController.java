package com.example.mkdown_java.MkDownNote.controller;

import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import com.example.mkdown_java.User.util.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Note")
public class NoteSubmitController {

    @Autowired
    private NoteSubmitService noteSubmitService;

    private static final Logger logger
            = LoggerFactory.getLogger(NoteSubmitController.class);

    /**
     * 提交保存笔记
     * @param note
     * @return
     */
    @ResponseBody
    @PostMapping("/Submit")
    public int Submit(@RequestBody  Note note){
        logger.debug("提交内容："+note.toString());
        // 设置笔记userid
        note.setUserId(new UserSession().get().getId());
        // 保存笔记
        int id = noteSubmitService.Submit(note);
        return id;
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/selectNote")
    public Note selectNote(Integer id){
        logger.debug("进入查询 笔记ID：" + id);
        Note note = noteSubmitService.selectNote(id);
        return note;
    }

    /**
     * 根据id删除笔记
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/deleteNote")
    public boolean deleteNote(Integer id) {
        return noteSubmitService.deleteNote(id);
    }

    /**
     * 查询用户全部笔记
     * @return
     */
    @ResponseBody
    @GetMapping("/selectUserNote")
    public List<Note> selectUserNote(){
        logger.debug("查询用户全部笔记");
        List<Note> noteList = noteSubmitService.selectUserNote(new UserSession().get().getId());
        return noteList;
    }

    /**
     * 使用es搜索笔记
     * @param noteTitleAndNoteParticulars
     * @return
     */
    @ResponseBody
    @GetMapping("/selectNoteEs")
    public List<Note> selectNoteEs(String noteTitleAndNoteParticulars){
        logger.debug("查询es用户笔记  id:"+noteTitleAndNoteParticulars);
        List<Note> noteList = noteSubmitService.selectNoteEs(noteTitleAndNoteParticulars,new UserSession().get().getId());
        return noteList;
    }

    @GetMapping ("/GitSubmit")
    public String GitSubmit(String neirong){
        logger.debug("Git提交内容："+neirong);
        return neirong;
    }
}
