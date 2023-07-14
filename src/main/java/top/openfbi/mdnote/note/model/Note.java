package top.openfbi.mdnote.note.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("note")
public class Note implements Serializable {


    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 笔记标题
     */
    @TableField("title")
    private String title;

    /**
     * 笔记内容
     */
    @TableField("content")
    private String content;

//
//    @TableField("es_id")
//    private Integer esId;

//    @TableField(exist = false)
//    private Image[] idmgs;
    /**
     * 笔记图片
     */
//    @TableField(value = "img_ids")
//    private String imgIds;


    /**
     *
     * 创建时间
     */

    @TableField("create_time")
    private String createTime;

    /**
     * 是否删除(0 不删除，1 删除)
     */
    @TableField("status")
    private int status;

}
