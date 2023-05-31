package com.example.mkdown_java.MkDownElasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.mkdown_java.MkDownElasticSearch.dao.NoteRepository;
import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownNote.model.Note;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticSearchServer implements NoteRepository {

    @Resource
    private NoteRepository noteRepository;
    @Autowired
    ElasticsearchOperations operations;
    @Autowired
    ElasticsearchClient elasticsearchClient;
    @Autowired
    RestClient restClient;

    private  ElasticsearchOperations elasticsearchOperations;

    public ElasticSearchServer(ElasticsearchOperations elasticsearchOperations) {
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



    public ElasticSearchNote findById(@PathVariable("id")  int id) {

        return elasticsearchOperations.get(Integer.toString(id), ElasticSearchNote.class);
    }


    public List<ElasticSearchNote> findByNoteTitleAndNoteParticulars(String noteTitleAndNoteParticulars,Integer userId) {
        System.out.println(noteTitleAndNoteParticulars);
        Criteria criteria = new Criteria("userId").is(userId).subCriteria(
                new Criteria("noteParticulars").matches(noteTitleAndNoteParticulars).or("noteTitle").matches(noteTitleAndNoteParticulars)
                );
        Query query = new CriteriaQuery(criteria);

        SearchHits<ElasticSearchNote> elasticSearchNoteSearchHits= elasticsearchOperations.search(query, ElasticSearchNote.class);
//         = new ArrayList<ElasticSearchNote>();

        List<ElasticSearchNote> notes = elasticSearchNoteSearchHits.map(hit ->
                hit.getContent()).toList();

//        for(SearchHit<ElasticSearchNote> searchNoteHit : elasticSearchNoteSearchHits.getSearchHits()){
//
//            searchNoteHit.getScore();
//            searchNoteHit.getContent().getNoteId();
//        }
//        ElasticSearchNote elasticSearchNote = elasticsearchOperations.get(noteTitle.toString(), ElasticSearchNote.class);
        return notes;
    }

    /**
     * 搜索博客
     *
     * @param key 搜索内容
     * @return 搜索到的结果列表
     */
    public List<ElasticSearchNote> searchBlog(String key) {
        // 1. 获取数据
        SearchPage<ElasticSearchNote> searchPage = noteRepository.findByDescriptiveContent(
                // 1.1 设置key和分页，这里是从第0页开始的，所以要-1
                key);
        // 2. 高亮数据替换
        List<SearchHit<ElasticSearchNote>> searchHitList = searchPage.getContent();
        ArrayList<ElasticSearchNote> blogDocList = new ArrayList<>(searchHitList.size());
        for (SearchHit<ElasticSearchNote> blogHit : searchHitList) {
            // 2.1 获取博客数据
            ElasticSearchNote blogDoc = blogHit.getContent();
            // 2.2 获取高亮数据
            Map<String, List<String>> fields = blogHit.getHighlightFields();
            if (fields.size() > 0) {
                // 2.3 通过反射，将高亮数据替换到原来的博客数据中
                BeanMap beanMap = BeanMap.create(blogDoc);
                for (String name : fields.keySet()) {
                    beanMap.put(name, fields.get(name).get(0));
                }
            }
            // 2.4 博客数据插入列表
            blogDocList.add(blogDoc);
        }
        return blogDocList;
    }

    @Override
    public SearchPage<ElasticSearchNote> findByDescriptiveContent(String descriptiveContent) {
        return null;
    }
}
