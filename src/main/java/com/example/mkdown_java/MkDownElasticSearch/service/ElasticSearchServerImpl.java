package com.example.mkdown_java.MkDownElasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticSearchServerImpl {

    @Autowired
    ElasticsearchOperations operations;
    @Autowired
    ElasticsearchClient elasticsearchClient;
    @Autowired
    RestClient restClient;
//    @Autowired
//    private NoteRepository noteRepository;

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticSearchServerImpl(ElasticsearchOperations elasticsearchOperations) {
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


    public int save(ElasticSearchNote elasticSearchNote) {
        return elasticsearchOperations.save(elasticSearchNote).getNoteId();
    }

    public int delete(int delete) {
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
        elasticSearchNote.setNoteId(delete);
        return Integer.parseInt(elasticsearchOperations.delete(elasticSearchNote));
    }


    public ElasticSearchNote findById(@PathVariable("id") int id) {

        return elasticsearchOperations.get(Integer.toString(id), ElasticSearchNote.class);
    }


    //    public List<ElasticSearchNote> findByNoteTitleAndNoteParticulars(String noteTitleAndNoteParticulars,Integer userId) {
//        System.out.println(noteTitleAndNoteParticulars);
//        Criteria criteria = new Criteria("userId").is(userId).subCriteria(
//                new Criteria("noteParticulars").matches(noteTitleAndNoteParticulars).or("noteTitle").matches(noteTitleAndNoteParticulars)
//                );
//        Query query = new CriteriaQuery(criteria);
//
//        SearchHits<ElasticSearchNote> elasticSearchNoteSearchHits= elasticsearchOperations.search(query, ElasticSearchNote.class);
//
//        List<ElasticSearchNote> notes = elasticSearchNoteSearchHits.map(hit ->
//                hit.getContent()).toList();
////        for(SearchHit<ElasticSearchNote> searchNoteHit : elasticSearchNoteSearchHits.getSearchHits()){
////
////            searchNoteHit.getScore();
////            searchNoteHit.getContent().getNoteId();
////        }
////        ElasticSearchNote elasticSearchNote = elasticsearchOperations.get(noteTitle.toString(), ElasticSearchNote.class);
//        return notes;
//    }
    public List<SearchHit<ElasticSearchNote>> findByNoteTitleAndNoteParticulars(String searchKey, Integer userId) {
        ArrayList<HighlightField> highlightFields = new ArrayList<HighlightField>();
        highlightFields.add(new HighlightField("noteParticulars"));
        highlightFields.add(new HighlightField("noteTitle"));
        ArrayList<String> searchFields = new ArrayList<>() {
            {
                add("noteParticulars");
                add("noteTitle");
            }
        };

        HighlightParameters highlightParameters = HighlightParameters.builder().withBoundaryMaxScan(5).build();
        Highlight highlight = new Highlight(highlightParameters, highlightFields);
        Query byUserId = MatchQuery.of(m -> m.field("userId").query(userId))._toQuery();
        Query byKey = MultiMatchQuery.of(m -> m.fields(searchFields).query(searchKey))._toQuery();


        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b.must(byUserId, byKey)
                ))
                .withHighlightQuery(new HighlightQuery(highlight, ElasticSearchNote.class))
                .build();

        SearchHits<ElasticSearchNote> elasticSearchNoteSearchHits = elasticsearchOperations.search(nativeQuery, ElasticSearchNote.class);
        List<SearchHit<ElasticSearchNote>> notesAndEsNote = elasticSearchNoteSearchHits.getSearchHits();
        List<ElasticSearchNote> notes = elasticSearchNoteSearchHits.map(hit -> hit.getContent()).toList();
        return notesAndEsNote;
    }

//    /**
//     * 搜索博客
//     *
//     * @return 搜索到的结果列表
//     */
//    public List<ElasticSearchNote> findByUserIdAndNoteTitleOrNoteParticulars(Integer userId, String noteTitle, String noteParticulars) {
//        // 1. 获取数据
//        List<ElasticSearchNote> searchPage = noteRepository.findByUserIdAndNoteTitleOrNoteParticulars(userId, noteTitle, noteParticulars);
//        return searchPage;
//    }

//    @Override
//    public SearchPage<ElasticSearchNote> findByDescriptiveContent(String descriptiveContent) {
//        return null;
//    }
}
