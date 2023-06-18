package com.example.mkdown_java.MkDownElasticSearch.controller;


import com.example.mkdown_java.MkDownElasticSearch.model.ElasticSearchNote;
import com.example.mkdown_java.MkDownElasticSearch.service.ElasticSearchServerImpl;
import com.example.mkdown_java.User.util.UserSession;
import com.example.mkdown_java.common.exception.ResultException;
import com.example.mkdown_java.config.ResponseResultBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * es数据库请求接口
 */
@RestController
@RequestMapping("/ElasticSearchNote")
@ResponseResultBody
public class ElasticSearchController {

    @Autowired
    private ElasticSearchServerImpl elasticSearchServerImpl;

    private static final Logger logger
            = LoggerFactory.getLogger(ElasticSearchController.class);

    /**
     * 保存笔记到es
     * @param elasticSearchNote
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public ElasticSearchNote save(@RequestBody ElasticSearchNote elasticSearchNote) {
        return elasticSearchServerImpl.save(elasticSearchNote);
    }

    /**
     * 根据id查询笔记
     * @param id
     * @return ElasticSearchNote
     */
    @GetMapping("/person/{id}")
    public ElasticSearchNote findById(@PathVariable("id")  int id) {
        return elasticSearchServerImpl.findById(id);
    }

    /**
     * 根据条件搜索笔记
     * @param noteTitleAndNoteParticulars
     * @return
     */
    @GetMapping("/esNoteTitleAndNoteParticulars/{noteTitleAndNoteParticulars}")
    public List<SearchHit<ElasticSearchNote>> findByNoteTitleAndNoteParticulars(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars) {
        return elasticSearchServerImpl.findByNoteTitleAndNoteParticulars(noteTitleAndNoteParticulars,new UserSession().get().getId());
    }

    /**
     * delete方法专门的返回值
     * 原因：springboot会把string返回值给识别成html结构，导致无法序列化为Result类型。抛出类型转换异常。
     * 所以使用类的方式返回数据
     * 数据最好使用对象形式返回
     */
    @Data
    @AllArgsConstructor
    class EsDeleteResponse{
        private int esId;
    }

    /**
     * 删除笔记
     * @param id
     * @return
     * @throws ResultException
     */
    @GetMapping("/delete/{id}")
    public EsDeleteResponse delete(@PathVariable("id")  int id) throws ResultException {
       return new EsDeleteResponse(elasticSearchServerImpl.delete(id));
    }

//    @GetMapping("/searchNote/{noteTitleAndNoteParticulars}")
//    public List<ElasticSearchNote> searchNote(@PathVariable("noteTitleAndNoteParticulars")  String noteTitleAndNoteParticulars) {
//        System.out.println("获取参数："+ noteTitleAndNoteParticulars);
//        return elasticSearchServerImpl.findByUserIdAndNoteTitleOrNoteParticulars(userIdLs,noteTitleAndNoteParticulars,noteTitleAndNoteParticulars);
//    }
}

