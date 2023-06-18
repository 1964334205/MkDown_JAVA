package com.example.mkdown_java.MkDownNote.controller;

import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import com.example.mkdown_java.User.util.UserSession;
import com.example.mkdown_java.common.exception.ResultException;
import com.example.mkdown_java.config.ResponseResultBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * note请求
 */
@RestController
@RequestMapping("/Note")
@ResponseResultBody
public class NoteSubmitController {

    @Autowired
    private NoteSubmitService noteSubmitService;

    private static final Logger logger
            = LoggerFactory.getLogger(NoteSubmitController.class);

    /**
     * Submit方法专门的返回值
     * 原因：springboot会把string返回值给识别成html结构，导致无法序列化为Result类型。抛出类型转换异常。
     * 所以使用类的方式返回数据
     * 数据最好使用对象形式返回
     */
    @Data
    @AllArgsConstructor
    class SubmitResponse {
        private int noteId;
    }

    /**
     * 提交保存笔记
     * @param note
     * @return
     */
    @ResponseBody
    @PostMapping("/Submit")
    public SubmitResponse Submit(@RequestBody  Note note) throws ResultException {
        logger.debug("提交内容："+note.toString());
        // 设置笔记userid
        note.setUserId(new UserSession().get().getId());
        // 保存笔记
        return new SubmitResponse(noteSubmitService.Submit(note));
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/selectNote")
    public Note selectNote(Integer id) throws ResultException {
        logger.debug("进入查询 笔记ID：" + id);
        return noteSubmitService.selectNote(id);
    }

    /**
     * 根据id删除笔记
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/deleteNote")
    public void deleteNote(Integer id) throws ResultException {
        noteSubmitService.deleteNote(id);
    }

    /**
     * 查询用户全部笔记
     * @return
     */
    @ResponseBody
    @GetMapping("/selectUserNote")
    public List<Note> selectUserNote() throws ResultException {
        logger.debug("查询用户全部笔记");
        return noteSubmitService.selectUserNote(new UserSession().get().getId());
    }

    /**
     * 使用es搜索笔记
     * @param noteTitleAndNoteParticulars
     * @return
     */
    @ResponseBody
    @GetMapping("/selectNoteEs")
    public List<Note> selectNoteEs(String noteTitleAndNoteParticulars) throws ResultException {
        logger.debug("查询es用户笔记  id:"+noteTitleAndNoteParticulars);
        return noteSubmitService.selectNoteEs(noteTitleAndNoteParticulars,new UserSession().get().getId());
    }

    @GetMapping ("/GitSubmit")
    public String GitSubmit(String neirong){
        logger.debug("Git提交内容："+neirong);
        return neirong;
    }
}
