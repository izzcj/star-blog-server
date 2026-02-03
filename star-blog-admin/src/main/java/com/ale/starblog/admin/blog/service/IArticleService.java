package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleBO;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleQuery;
import com.ale.starblog.framework.core.service.ICrudService;

import java.util.Collection;
import java.util.Map;

/**
 * 文章服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
public interface IArticleService extends ICrudService<Article, ArticleBO, ArticleQuery> {

    /**
     * 增加文章浏览量
     * 访问量比较大时可以使用Redis进行缓存
     *
     * @param id 文章ID
     */
    void incrementViewCount(Long id);

    /**
     * 获取文章标题映射
     *
     * @param ids 文章ID集合
     * @return 文章标题映射
     */
    Map<Long, String> fetchTitleMapping(Collection<Long> ids);

    /**
     * 获取文章分类数量映射
     *
     * @param categories 文章分类集合
     * @return 文章分类数量映射
     */
    Map<String, Long> fetchCategoryCountMapping(Collection<String> categories);

    /**
     * 发布文章
     *
     * @param id 文章ID
     */
    void publish(Long id);

    /**
     * 切换置顶状态
     *
     * @param id 文章ID
     */
    void toggleTop(Long id);

    /**
     * 切换推荐状态
     *
     * @param id 文章ID
     */
    void toggleRecommend(Long id);
}