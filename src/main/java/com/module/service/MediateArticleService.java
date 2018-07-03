package com.module.service;

import com.core.base.service.BaseModelService;
import com.module.entity.MediateArticle;
import com.module.entity.NotarizeArticle;
import com.module.mapper.MediateArticleMapper;
import com.module.mapper.NotarizeArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公证预约文章
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class MediateArticleService extends BaseModelService<MediateArticleMapper,MediateArticle> {

    @Autowired
    private MediateArticleMapper mediateArticleMapper;

    public List<MediateArticle> queryAll(){
        return this.mediateArticleMapper.selectAll();
    }

    public MediateArticle queryById(String id){
        return this.mediateArticleMapper.selectById(id);
    }

}
