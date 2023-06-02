package com.example.mkdown_java.MkDownElasticSearch.controller;


import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author geng
 * 2020/12/20
 */
@RestController
@RequestMapping("/ElasticSearchNote")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchServerImpl elasticSearchServerImpl;

    private int userIdLs = 1909167524;

    @ResponseBody
    @PostMapping("/save")
    public int save(@RequestBody ElasticSearchNote elasticSearchNote) {

        return elasticSearchServerImpl.save(elasticSearchNote);
    }


    @GetMapping("/person/{id}")
    public ElasticSearchNote findById(@PathVariable("id")  int id) {
        return elasticSearchServerImpl.findById(id);
    }

    @GetMapping("/esNoteTitleAndNoteParticulars/{noteTitleAndNoteParticulars}")
    public List<SearchHit<ElasticSearchNote>> findByNoteTitleAndNoteParticulars(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars) {
        return elasticSearchServerImpl.findByNoteTitleAndNoteParticulars(noteTitleAndNoteParticulars,userIdLs);
    }

    @GetMapping("/delete/{id}")
    public int delete(@PathVariable("id")  int id) {
        return elasticSearchServerImpl.delete(id);
    }

//    @GetMapping("/searchNote/{noteTitleAndNoteParticulars}")
//    public List<ElasticSearchNote> searchNote(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars) {
//        System.out.println("获取参数："+ noteTitleAndNoteParticulars);
//        return elasticSearchServerImpl.findByUserIdAndNoteTitleOrNoteParticulars(userIdLs,noteTitleAndNoteParticulars,noteTitleAndNoteParticulars);
//    }
}

