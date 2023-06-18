package com.example.mkdown_java.Img.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Time;

/**
 * 图片实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mkDownImg")
public class Img implements Serializable {

    /**
     * 文件ID
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 文件URL
     */
    @TableField("url")
    private String url;

    /**
     * 创建时间
     */
    @TableField("upload_time")
    private Time uploadTime;

    /**
     * 是否删除(0 不删除，1 删除)
     */
    @TableField("status")
    private int status;
    //state
    //status

}
