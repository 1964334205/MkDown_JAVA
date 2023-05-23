package com.example.mkdown_java.MkDownElasticSearch;



import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.mkdown_java.MkDownNote.model.Note;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geng
 * 2020/12/20
 */
@RestController
@RequestMapping("/ElasticSearchNote")
public class NoteController {

    @Autowired
    ElasticsearchOperations operations;
    @Autowired
    ElasticsearchClient elasticsearchClient;
    @Autowired
    RestClient restClient;

    private  ElasticsearchOperations elasticsearchOperations;

    public NoteController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

//    @PostMapping("/person")
//    public String save(@RequestBody ElasticSearchNote elasticSearchNote) {
//        ElasticSearchNote elasticSearchNote1 = elasticsearchOperations.save(elasticSearchNote);
//        return elasticSearchNote1.getNoteId();
//    }

    @GetMapping("/person")
    public String save() {
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
        elasticSearchNote.setNoteId("1");
        elasticSearchNote.setNoteTitle("笔记内容");
        elasticSearchNote.setNoteParticulars("阿萨德佛i还是代课教师发哈山东科技方法，水电费灰色空间分段函数，收到开发环境山东科技发哈");
        System.out.println(elasticSearchNote.toString());
        ElasticSearchNote elasticSearchNote1 = elasticsearchOperations.save(elasticSearchNote);
        return elasticSearchNote1.getNoteId();
    }

    @GetMapping("/person/{id}")
    public ElasticSearchNote findById(@PathVariable("id")  Long id) {
        ElasticSearchNote elasticSearchNote = elasticsearchOperations.get(id.toString(), ElasticSearchNote.class);
        return elasticSearchNote;
    }

}

