package com.example.mkdown_java.MkDownElasticSearch.controller;


import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServer;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ElasticSearchServer elasticSearchServer;

    @ResponseBody
    @PostMapping("/save")
    public int save(@RequestBody ElasticSearchNote elasticSearchNote) {

        return elasticSearchServer.save(elasticSearchNote);
    }


    @GetMapping("/person/{id}")
    public ElasticSearchNote findById(@PathVariable("id")  int id) {
        return elasticSearchServer.findById(id);
    }

    @GetMapping("/esNoteTitleAndNoteParticulars/{noteTitleAndNoteParticulars}/{userId}")
    public List<ElasticSearchNote> findByNoteTitleAndNoteParticulars(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars,@PathVariable("userId")  int userId) {
        return elasticSearchServer.findByNoteTitleAndNoteParticulars(noteTitleAndNoteParticulars,userId);
    }

    @GetMapping("/delete/{id}")
    public int delete(@PathVariable("id")  int id) {
        return elasticSearchServer.delete(id);
    }
}

