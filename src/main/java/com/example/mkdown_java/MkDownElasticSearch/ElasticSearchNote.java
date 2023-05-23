package com.example.mkdown_java.MkDownElasticSearch;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
@Document(indexName = "elasticSearchNote")
public class ElasticSearchNote implements Serializable {

    /**
     * ID
     */
    @Id
    @Field(type = FieldType.Text)
    private String noteId;

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
