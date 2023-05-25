package com.example.mkdown_java.MkDownElasticSearch;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
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
//    public String saveElasticSearchNoteBian(@RequestBody ElasticSearchNote elasticSearchNote) {
//        ElasticSearchNote elasticSearchNote1 = elasticsearchOperations.save(elasticSearchNote);
//        return elasticSearchNote1.getNoteId();
//    }

//    @GetMapping("/person")
//    public String save() {
//        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
//        elasticSearchNote.setNoteId("1");
//        elasticSearchNote.setNoteTitle("笔记内容");
//        elasticSearchNote.setNoteParticulars("阿萨德佛i还是代课教师发哈山东科技方法，水电费灰色空间分段函数，收到开发环境山东科技发哈");
//        System.out.println(elasticSearchNote.toString());
//        ElasticSearchNote elasticSearchNote1 = elasticsearchOperations.save(elasticSearchNote);
//        return elasticSearchNote1.getNoteId();
//    }

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

    @GetMapping("/findByNoteTitle/{noteTitle}")
    public List<Pair<Float, ElasticSearchNote>> findByNoteTitle(@PathVariable("noteTitle")  String noteTitle) {
        System.out.println(noteTitle);
        Criteria criteria = new Criteria("noteTitle").is(noteTitle);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ElasticSearchNote> elasticSearchNoteSearchHits= elasticsearchOperations.search(query, ElasticSearchNote.class);
//         = new ArrayList<ElasticSearchNote>();

        List< Pair<Float, ElasticSearchNote>> notes = elasticSearchNoteSearchHits.map(hit ->
              new Pair<Float, ElasticSearchNote>(hit.getScore(), hit.getContent())).toList();

//        for(SearchHit<ElasticSearchNote> searchNoteHit : elasticSearchNoteSearchHits.getSearchHits()){
//
//            searchNoteHit.getScore();
//            searchNoteHit.getContent().getNoteId();
//        }
//        ElasticSearchNote elasticSearchNote = elasticsearchOperations.get(noteTitle.toString(), ElasticSearchNote.class);
        return notes;
    }

}

