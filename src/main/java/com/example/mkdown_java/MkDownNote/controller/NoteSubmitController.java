package com.example.mkdown_java.MkDownNote.controller;

import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Note")
public class NoteSubmitController {

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
    public Note selectNote(String noteId){
        System.out.println("进入查询");
        Note note = noteSubmitService.selectNote(noteId);
        return note;
    }

    @ResponseBody
    @GetMapping("/selectUserNote")
    public List<Note> selectUserNote(String userId){
        System.out.println("查询用户笔记  id+:"+userId);
        List<Note> noteList = noteSubmitService.selectUserNote(userId);
        return noteList;
    }

    @GetMapping ("/GitSubmit")
    public String GitSubmit(String neirong){
        System.out.println("Git提交内容："+neirong);
        return neirong;
    }
}
