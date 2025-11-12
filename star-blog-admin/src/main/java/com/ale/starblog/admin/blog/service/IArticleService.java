package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleBO;
import com.ale.starblog.admin.blog.domain.pojo.article.CreateArticleDTO;
import com.ale.starblog.admin.blog.domain.pojo.article.ModifyArticleDTO;
import com.ale.starblog.framework.core.service.ICrudService;

import java.util.List;

/**
 * 文章服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
public interface IArticleService extends ICrudService<Article, ArticleBO, CreateArticleDTO, ModifyArticleDTO> {

    /**
     * 增加文章浏览量
     * 访问量比较大时可以使用Redis进行缓存
     *
     * @param id 文章ID
     */
    void incrementViewCount(Long id);

    /**
     * 获取热门文章
     *
     * @return 热门文章
     */
    List<ArticleBO> fetchHotArticles();
}