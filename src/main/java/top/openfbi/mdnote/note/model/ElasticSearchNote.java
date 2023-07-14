package top.openfbi.mdnote.note.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * es实体类
 */
@Data
@Document(indexName = "elastic_search_note")
public class ElasticSearchNote {

    /**
     * ID
     */
    @Id
    @Field(type = FieldType.Integer)
    private Long id;

    /**
     * 用户ID
     */
    @Field(analyzer="user_id")
    private Long userId;

    /**
     * 笔记标题
     */
    @Field(type = FieldType.Text,analyzer="ik_smart", searchAnalyzer="ik_smart", copyTo = "descriptiveContent")
    private String title;

    /**
     * 笔记内容
     */
    @Field(type = FieldType.Text,analyzer="ik_smart", searchAnalyzer = "ik_smart", copyTo = "descriptiveContent")
    private String content;

}
