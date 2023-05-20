package com.example.mkdown_java.MkDownNote.controller;

import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public Note selectNote(Integer id){
        System.out.println("进入查询");
        Note note = noteSubmitService.selectNote(id);
        return note;
    }

    @GetMapping ("/GitSubmit")
    public String GitSubmit(String neirong){
        System.out.println("Git提交内容："+neirong);
        return neirong;
    }
}
