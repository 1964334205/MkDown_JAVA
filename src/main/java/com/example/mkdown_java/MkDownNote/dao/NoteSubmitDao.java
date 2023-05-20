package com.example.mkdown_java.MkDownNote.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mkdown_java.MkDownNote.model.Note;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**

 */
@Mapper
public interface NoteSubmitDao extends BaseMapper<Note> {

    List<Note> Submit();
}