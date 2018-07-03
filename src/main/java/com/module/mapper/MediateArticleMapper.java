package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.MediateArticle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediateArticleMapper extends BaseMapper<MediateArticle> {

    List<MediateArticle> selectAll();

    MediateArticle selectById(@Param("id") String id);
}
