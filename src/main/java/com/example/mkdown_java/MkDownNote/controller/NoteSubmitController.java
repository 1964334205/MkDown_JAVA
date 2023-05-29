package com.example.mkdown_java.MkDownNote.controller;

import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Note")
public class NoteSubmitController {

    private int userIdLs = 1909167524;

    @Autowired
    private NoteSubmitService noteSubmitService;
    @ResponseBody
    @PostMapping("/Submit")
    public Boolean Submit(@RequestBody  Note note){
        System.out.println("提交内容："+note.toString());
        Boolean flag = noteSubmitService.Submit(note);
        return flag;
    }

    @ResponseBody
    @GetMapping("/selectNote")
    public Note selectNote(int noteId){
        System.out.println("进入查询 笔记ID：" + noteId);
        Note note = noteSubmitService.selectNote(noteId);
        return note;
    }

    @ResponseBody
    @GetMapping("/deleteNote")
    public boolean deleteNote(int noteId) {
        return noteSubmitService.deleteNote(noteId);
    }

    @ResponseBody
    @GetMapping("/selectUserNote")
    public List<Note> selectUserNote(int userId){
        System.out.println("查询用户笔记  id:"+userId);
        List<Note> noteList = noteSubmitService.selectUserNote(userIdLs);
        return noteList;
    }

    @ResponseBody
    @GetMapping("/selectNoteEs")
    public List<Note> selectNoteEs(String noteTitleAndNoteParticulars,int userId){
        System.out.println("查询es用户笔记  id:"+noteTitleAndNoteParticulars);
        List<Note> noteList = noteSubmitService.selectNoteEs(noteTitleAndNoteParticulars,userIdLs);
        return noteList;
    }

    @GetMapping ("/GitSubmit")
    public String GitSubmit(String neirong){
        System.out.println("Git提交内容："+neirong);
        return neirong;
    }
}
