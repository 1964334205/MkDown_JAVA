package com.example.mkdown_java.MkDownElasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger
            = LoggerFactory.getLogger(ElasticSearchServerImpl.class);
//    @Autowired
//    private NoteRepository noteRepository;

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticSearchServerImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    //    @PostMapping("/person")
//    public String saveElasticSearchNoteBian(@RequestBody ElasticSearchNote elasticSearchNote) {
//        ElasticSearchNote elasticSearchNote1 = elasticsearchOperations.save(elasticSearchNote);
//        return elasticSearchNote1.getId();
//    }

//    @GetMapping("/person")
//    public String save() {
//        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
//        elasticSearchNote.setId("1");
//        elasticSearchNote.setTitle("笔记内容");
//        elasticSearchNote.setParticulars("阿萨德佛i还是代课教师发哈山东科技方法，水电费灰色空间分段函数，收到开发环境山东科技发哈");
//        System.out.println(elasticSearchNote.toString());
//        ElasticSearchNote elasticSearchNote1 = elasticsearchOperations.save(elasticSearchNote);
//        return elasticSearchNote1.getId();
//    }

    /**
     * 保存笔记到es
     * @param elasticSearchNote
     * @return
     */
    public int save(ElasticSearchNote elasticSearchNote) {
       return elasticsearchOperations.save(elasticSearchNote).getId();
    }

    /**
     * 删除笔记
     * @param delete
     * @return
     */
    public int delete(int delete) {
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
        elasticSearchNote.setId(delete);
        return Integer.parseInt(elasticsearchOperations.delete(elasticSearchNote));
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    public ElasticSearchNote findById(@PathVariable("id") int id) {

        return elasticsearchOperations.get(Integer.toString(id), ElasticSearchNote.class);
    }


    //    public List<ElasticSearchNote> findByNoteTitleAndNoteParticulars(String noteTitleAndNoteParticulars,Integer id) {
//        System.out.println(noteTitleAndNoteParticulars);
//        Criteria criteria = new Criteria("id").is(id).subCriteria(
//                new Criteria("particulars").matches(noteTitleAndNoteParticulars).or("title").matches(noteTitleAndNoteParticulars)
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
////            searchNoteHit.getContent().getId();
////        }
////        ElasticSearchNote elasticSearchNote = elasticsearchOperations.get(title.toString(), ElasticSearchNote.class);
//        return notes;
//    }

    /**
     * 根据笔记标题和内容搜索指定用户笔记
     * @param searchKey
     * @param userId
     * @return
     */
    public List<SearchHit<ElasticSearchNote>> findByNoteTitleAndNoteParticulars(String searchKey, Integer userId) {
        // 需要高亮的字段
        ArrayList<HighlightField> highlightFields = new ArrayList<HighlightField>();
        highlightFields.add(new HighlightField("particulars"));
        highlightFields.add(new HighlightField("title"));

        // 创建需要搜索的字段
        ArrayList<String> searchFields = new ArrayList<>() {
            {
                add("particulars");
                add("title");
            }
        };

        //设置上下文搜索宽度
        HighlightParameters highlightParameters = HighlightParameters.builder().withBoundaryMaxScan(5).build();
        // 设置高亮对象
        Highlight highlight = new Highlight(highlightParameters, highlightFields);
        // 设置搜索条件语句对象
        Query byUserId = MatchQuery.of(m -> m.field("userId").query(userId))._toQuery();
        // 设置搜索条件语句对象
        Query byKey = MultiMatchQuery.of(m -> m.fields(searchFields).query(searchKey))._toQuery();

        // 设置查询对象 条件为byUserId and byKey
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b.must(byUserId, byKey)
                ))
                .withHighlightQuery(new HighlightQuery(highlight, ElasticSearchNote.class))
                .build();
        // 执行搜索语句，并设置返回类型为：ElasticSearchNote
        SearchHits<ElasticSearchNote> elasticSearchNoteSearchHits = elasticsearchOperations.search(nativeQuery, ElasticSearchNote.class);
        // 获取查询结果
        List<SearchHit<ElasticSearchNote>> notesAndEsNote = elasticSearchNoteSearchHits.getSearchHits();
//        List<ElasticSearchNote> notes = elasticSearchNoteSearchHits.map(hit -> hit.getContent()).toList();
        return notesAndEsNote;
    }

//    /**
//     * 搜索博客
//     *
//     * @return 搜索到的结果列表
//     */
//    public List<ElasticSearchNote> findByUserIdAndNoteTitleOrNoteParticulars(Integer id, String title, String particulars) {
//        // 1. 获取数据
//        List<ElasticSearchNote> searchPage = noteRepository.findByUserIdAndNoteTitleOrNoteParticulars(id, title, particulars);
//        return searchPage;
//    }

//    @Override
//    public SearchPage<ElasticSearchNote> findByDescriptiveContent(String descriptiveContent) {
//        return null;
//    }
}
