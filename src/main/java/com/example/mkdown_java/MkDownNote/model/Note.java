package com.example.mkdown_java.MkDownNote.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mkdown_java.Img.model.Img;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("Note")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 笔记标题
     */
    @TableField("title")
    private String title;

    @TableField(exist = false)
    private String titleHighlight;
    /**
     * 笔记内容
     */
    @TableField("particulars")
    private String particulars;

    @TableField(exist = false)
    private String particularsHighlight;

    @TableField("es_id")
    private Integer esId;

    @TableField(exist = false)
    private Img[] idmgs;
    /**
     * 笔记图片
     */
    @TableField(value = "img_ids")
    private String imgIds;
    /**
     *
     * 创建时间
     */
    @TableField("found_time")
    private String foundTime;

    /**
     * 是否删除(0 不删除，1 删除)
     */
    @TableField("status")
    private int status;

}
