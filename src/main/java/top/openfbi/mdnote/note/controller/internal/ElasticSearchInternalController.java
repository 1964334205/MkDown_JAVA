package top.openfbi.mdnote.note.controller.internal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.*;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.note.model.ElasticSearchNote;
import top.openfbi.mdnote.note.service.ElasticSearchServerImpl;
import top.openfbi.mdnote.user.util.UserSession;

import java.util.List;


/**
 * es数据库请求接口
 */
@RestController
@RequestMapping("/internal/ElasticSearchNote")
@ResponseResultBody
public class ElasticSearchInternalController {

    @Autowired
    private ElasticSearchServerImpl elasticSearchServerImpl;

    private static final Logger logger
            = LoggerFactory.getLogger(ElasticSearchInternalController.class);

    /**
     * 保存笔记到es
     * @param elasticSearchNote
     * @return
     */
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
     * @param q
     * @return
     */
    @GetMapping("/search/{q}")
    public List<SearchHit<ElasticSearchNote>> search(@PathVariable("q")  String q) {
        return elasticSearchServerImpl.search(q,UserSession.get().getId());
    }



    /**
     * 删除笔记
     * @param id
     * @return
     * @throws ResultException
     */
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id")  int id) throws ResultException {
        elasticSearchServerImpl.delete(id);
    }

}

