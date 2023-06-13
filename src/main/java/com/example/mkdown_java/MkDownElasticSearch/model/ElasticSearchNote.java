package com.example.mkdown_java.MkDownElasticSearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


@Data
@Document(indexName = "elastic_search_note_test")
public class ElasticSearchNote implements Serializable {

    /**
     * ID
     */
    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    /**
     * 用户ID
     */
    @Field(analyzer="user_id")
    private Integer userId;

    /**
     * 笔记标题
     */
    @Field(type = FieldType.Text,analyzer="ik_smart", searchAnalyzer="ik_smart", copyTo = "descriptiveContent")
    private String title;

    /**
     * 笔记内容
     */
    @Field(type = FieldType.Text,analyzer="ik_smart", searchAnalyzer = "ik_smart", copyTo = "descriptiveContent")
    private String particulars;

}
