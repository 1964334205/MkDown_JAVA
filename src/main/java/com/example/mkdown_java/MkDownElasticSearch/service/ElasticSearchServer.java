package com.example.mkdown_java.MkDownElasticSearch.service;

import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;

import java.util.List;

public interface  ElasticSearchServer {
    List<ElasticSearchNote> findByUserIdAndNoteTitleOrNoteParticulars(Integer id,String title,String particulars);
}
