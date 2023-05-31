package com.example.mkdown_java.MkDownElasticSearch.dao;


import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownNote.model.Note;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NoteRepository extends ElasticsearchRepository<ElasticSearchNote, Integer> {

    @Highlight(
            fields = {
                    @HighlightField(name = "noteTitle"),
                    @HighlightField(name = "noteParticulars")
            },
            parameters = @HighlightParameters(
                    preTags = "<strong><font style='color:red'>",
                    postTags = "</font></strong>",
                    fragmentSize = 500,
                    numberOfFragments = 3
            )
    )
    SearchPage<ElasticSearchNote> findByDescriptiveContent(String descriptiveContent);

}
