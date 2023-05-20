package com.example.mkdown_java.MkDownNote.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.mkdown_java.Img.model.Img;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 笔记标题
     */
    @TableField("Note_Title")
    private String noteTitle;

    /**
     * 笔记内容
     */
    @TableField("Note_Particulars")
    private String noteParticulars;

    private Img[] imgs;
    /**
     * 笔记图片
     */
    @TableField(value = "Note_Img_Ids",typeHandler = JacksonTypeHandler.class)
    private String noteImgIds;
    /**
     *
     * 创建时间
     */
    @TableField("Found_Time")
    private Time foundTime;

    /**
     * 是否删除(0 不删除，1 删除)
     */
    @TableField("IS_Del")
    private int isDel;

}
