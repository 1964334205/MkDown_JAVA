package com.example.mkdown_java.MkDownElasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.common.ResultStatus;
import com.example.mkdown_java.common.exception.ResultException;
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

/**
 * es业务层
 */
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

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticSearchServerImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * 保存笔记到es
     * @param elasticSearchNote
     * @return
     */
    public ElasticSearchNote save(ElasticSearchNote elasticSearchNote) {
       return elasticsearchOperations.save(elasticSearchNote);
    }

    /**
     * 删除笔记
     * @param delete
     * @return
     */
    public int delete(int delete) throws ResultException {
        // 创建es对象
        ElasticSearchNote elasticSearchNote = new ElasticSearchNote();
        // 设置es对象id
        elasticSearchNote.setId(delete);
        // 根据id删除指定es文档
        String esNoteId = elasticsearchOperations.delete(elasticSearchNote);
        //判断是否删除成功
        if (esNoteId == null){
            // 删除失败，抛出es数据库异常
            throw new ResultException(ResultStatus.INTERNAL_ELASTIC_CONNECT_FAIL);
        }
        // 删除成功返回esID
        return Integer.parseInt(esNoteId);
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return
     */
    public ElasticSearchNote findById(@PathVariable("id") int id) {
        // 根据ID查询es中的文档，返回值为ElasticSearchNote
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
        // 需要高亮的字段particulars，title
        ArrayList<HighlightField> highlightFields = new ArrayList<HighlightField>();
        highlightFields.add(new HighlightField("particulars"));
        highlightFields.add(new HighlightField("title"));

        // 创建需要搜索的字段，particulars，title
        ArrayList<String> searchFields = new ArrayList<>() {
            {
                add("particulars");
                add("title");
            }
        };

        //设置上下文搜索宽度为5
        HighlightParameters highlightParameters = HighlightParameters.builder().withBoundaryMaxScan(5).build();
        // 设置高亮对象为上面设置的highlightFields
        Highlight highlight = new Highlight(highlightParameters, highlightFields);
        // 设置搜索根据userId条件语句对象
        Query byUserId = MatchQuery.of(m -> m.field("userId").query(userId))._toQuery();
        // 使用searchFields设置搜索条件语句对象，查询目标为searchKey
        Query byKey = MultiMatchQuery.of(m -> m.fields(searchFields).query(searchKey))._toQuery();

        // 设置查询对象 条件为byUserId and byKey。查询目标为ElasticSearchNote，高亮目标为highlight
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b.must(byUserId, byKey)
                ))
                .withHighlightQuery(new HighlightQuery(highlight, ElasticSearchNote.class))
                .build();
        // 执行搜索语句，并设置返回类型为：ElasticSearchNote
        SearchHits<ElasticSearchNote> elasticSearchNoteSearchHits = elasticsearchOperations.search(nativeQuery, ElasticSearchNote.class);
        // 获取查询高亮结果
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
