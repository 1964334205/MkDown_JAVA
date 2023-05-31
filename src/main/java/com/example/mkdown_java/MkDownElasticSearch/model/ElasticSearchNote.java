package com.example.mkdown_java.MkDownElasticSearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * <p>
 * 借用申请审批表
 * </p>
 *
 * @author fx
 * @since 2019-11-22
 */
@Data
@Document(indexName = "elastic_search_note")
public class ElasticSearchNote implements Serializable {

    /**
     * ID
     */
    @Id
    @Field(type = FieldType.Integer)
    private Integer noteId;

    /**
     * 用户ID
     */
    @Field(analyzer="user_id")
    private Integer userId;

    /**
     * 笔记标题
     */
    @Field(analyzer="ik_max_word")
    private String noteTitle;

    /**
     * 笔记内容
     */
    @Field(analyzer="ik_max_word")
    private String noteParticulars;

}
