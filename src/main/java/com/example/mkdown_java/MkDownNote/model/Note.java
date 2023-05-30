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
    @TableId(value = "note_id",type = IdType.AUTO)
    private int noteId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private int userId;

    /**
     * 笔记标题
     */
    @TableField("note_title")
    private String noteTitle;

    /**
     * 笔记内容
     */
    @TableField("note_particulars")
    private String noteParticulars;

    @TableField("es_id")
    private int esId;

    @TableField(exist = false)
    private Img[] noteImgs;
    /**
     * 笔记图片
     */
    @TableField(value = "note_img_ids")
    private String noteImgIds;
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
