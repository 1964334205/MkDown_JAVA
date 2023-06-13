package com.example.mkdown_java.MkDownElasticSearch.controller;


import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServerImpl;
import com.example.mkdown_java.User.util.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger
            = LoggerFactory.getLogger(ElasticSearchController.class);

    private int userIdLs = 1909167524;

    /**
     * 保存笔记到es
     * @param elasticSearchNote
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public int save(@RequestBody ElasticSearchNote elasticSearchNote) {
        return elasticSearchServerImpl.save(elasticSearchNote);
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    @GetMapping("/person/{id}")
    public ElasticSearchNote findById(@PathVariable("id")  int id) {
        return elasticSearchServerImpl.findById(id);
    }

    /**
     * 根据条件搜索笔记
     * @param noteTitleAndNoteParticulars
     * @return
     */
    @GetMapping("/esNoteTitleAndNoteParticulars/{noteTitleAndNoteParticulars}")
    public List<SearchHit<ElasticSearchNote>> findByNoteTitleAndNoteParticulars(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars) {
        return elasticSearchServerImpl.findByNoteTitleAndNoteParticulars(noteTitleAndNoteParticulars,new UserSession().get().getId());
    }

    /**
     * 删除笔记
     * @param id
     * @return
     */
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

