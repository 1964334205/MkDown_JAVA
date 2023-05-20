package com.example.mkdown_java.Img.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Time;

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
@TableName("mkDownImg")
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "Img_Id")
    private String imgId;

    /**
     * 用户ID
     */
    @TableField("Img_Url")
    private String imgUrl;

    /**
     * 创建时间
     */
    @TableField("Uploading_Time")
    private Time uploading_Time;

    /**
     * 是否删除(0 不删除，1 删除)
     */
    @TableField("IS_Del")
    private int isDel;

}
