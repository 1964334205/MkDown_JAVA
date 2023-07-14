package top.openfbi.mdnote.note.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.openfbi.mdnote.common.Result;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.note.model.Note;
import top.openfbi.mdnote.note.model.fe.EsNoteResult;
import top.openfbi.mdnote.note.model.fe.SimplificationNote;
import top.openfbi.mdnote.note.service.NoteService;
import top.openfbi.mdnote.user.util.UserSession;

import java.util.LinkedList;
import java.util.List;

/**
 * note请求
 */
@RestController
@RequestMapping("/note")
@ResponseResultBody
public class NoteController {

    @Autowired
    private NoteService noteService;

    private static final Logger logger
            = LoggerFactory.getLogger(NoteController.class);

    /**
     * Submit方法专门的返回值
     * 原因：springboot会把string返回值给识别成html结构，导致无法序列化为Result类型。抛出类型转换异常。
     * 所以使用类的方式返回数据
     * 数据最好使用对象形式返回
     */
    @Data
    @AllArgsConstructor
    static
    class SubmitResponse {
        private Long noteId;
    }

    /**
     * 提交保存笔记
     * @param note
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public SubmitResponse save(@RequestBody  Note note) throws ResultException {
        // 保存笔记
        note.setUserId(UserSession.get().getId());
        return new SubmitResponse(noteService.save(note));
    }


    /**
     * deleteNote方法专门的返回值
     * 原因：springboot会把string返回值给识别成html结构，导致无法序列化为Result类型。抛出类型转换异常。
     * 所以使用类的方式返回数据
     * 数据最好使用对象形式返回
     */
    @Data
    @AllArgsConstructor
    static
    class DeleteNoteResponse {
    }


    /**
     * 根据id删除笔记
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/delete")
    public Result delete(Long id) throws ResultException {
        noteService.delete(id, UserSession.get().getId());
        return Result.success();
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/info")
    public SimplificationNote info(Long id) throws ResultException {
        Note note = noteService.info(id,UserSession.get().getId());
        SimplificationNote simplificationNote = new SimplificationNote();
        simplificationNote.setId(note.getId());
        simplificationNote.setTitle(note.getTitle());
        simplificationNote.setContent(note.getContent());
        return simplificationNote;
    }

    /**
     * 查询用户全部笔记
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public EsNoteResult list() throws ResultException {
        //创建返回类
        List<Note> list = noteService.getNoteList(UserSession.get().getId());
        EsNoteResult esNoteResult = new EsNoteResult();
        List<SimplificationNote> frontEndNoteList = new LinkedList<>();
        list.stream().forEach(note ->{
            SimplificationNote simplificationNote = new SimplificationNote();
            simplificationNote.setId(note.getId());
            simplificationNote.setTitle(note.getTitle());
            simplificationNote.setContent(note.getContent());
            frontEndNoteList.add(simplificationNote);
        });
        esNoteResult.setNoteList(frontEndNoteList);
        return esNoteResult;
    }

    /**
     * 使用es搜索笔记
     * @param q
     * @return
     */
    @ResponseBody
    @GetMapping("/search")
    public EsNoteResult search(String q) throws ResultException {
        return noteService.search(q,UserSession.get().getId());
    }

}
