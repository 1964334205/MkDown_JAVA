package com.example.mkdown_java.MkDownElasticSearch.controller;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServer;
import org.elasticsearch.client.RestClient;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    public String save(ElasticSearchNote elasticSearchNote) {
//        return elasticSearchServer.save(elasticSearchNote);
//    }


//    @GetMapping("/person/{id}")
//    public ElasticSearchNote findById(@PathVariable("id")  Long id) {
//        return elasticSearchServer.findById(id);
//    }

    @GetMapping("/esNoteTitleAndNoteParticulars/{noteTitleAndNoteParticulars}")
    public List<ElasticSearchNote> findByNoteTitle(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars) {
        return elasticSearchServer.findByNoteTitle(noteTitleAndNoteParticulars);
    }

}

