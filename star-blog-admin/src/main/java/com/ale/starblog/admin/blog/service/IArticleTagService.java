package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.ArticleTag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 文章标签服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 根据文章id获取标签
     *
     * @param article 文章id
     * @return 标签列表
     */
    List<TagBO> getTagsByArticleId(Long article);

    /**
     * 更新文章标签
     *
     * @param articleId 文章id
     * @param tagIds 标签id列表
     */
    void updateArticleTags(Long articleId, List<Long> tagIds);

}