package com.example.mkdown_java.Img.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mkdown_java.Img.model.Img;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图片上传接口   暂时无用
 */
@Mapper
public interface ImgDao extends BaseMapper<Img> {

    List<Img> Submit();
}