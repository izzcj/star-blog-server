package com.ale.starblog.admin.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleBO;
import com.ale.starblog.admin.blog.domain.pojo.article.CreateArticleDTO;
import com.ale.starblog.admin.blog.domain.pojo.article.ModifyArticleDTO;
import com.ale.starblog.admin.blog.enums.ArticleStatus;
import com.ale.starblog.admin.blog.mapper.ArticleMapper;
import com.ale.starblog.admin.blog.service.IArticleService;
import com.ale.starblog.admin.blog.service.IArticleTagService;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends AbstractCrudServiceImpl<ArticleMapper, Article, ArticleBO> implements IArticleService {

    /**
     * 文章标签关联服务
     */
    private final IArticleTagService articleTagService;

    @Override
    public void beforeCreate(Article entity, HookContext context) {
        if (entity.getStatus() == null) {
            entity.setStatus(ArticleStatus.DRAFT);
        }
    }

    @Override
    public void afterCreate(Article entity, HookContext context) {
        CreateArticleDTO createDTO = CastUtils.cast(context.get(HookConstants.CREATE_DTO_KEY));
        // 保存文章标签关联关系
        if (createDTO.getTagIds() != null && !createDTO.getTagIds().isEmpty()) {
            this.articleTagService.updateArticleTags(entity.getId(), createDTO.getTagIds());
        }
    }

    @Override
    public void afterModify(Article entity, HookContext context) {
        ModifyArticleDTO modifyArticleDTO = CastUtils.cast(context.get(HookConstants.MODIFY_DTO_KEY));
        // 更新文章标签关联关系
        this.articleTagService.updateArticleTags(entity.getId(), modifyArticleDTO.getTagIds());
    }

    @Override
    public void incrementViewCount(Long id) {
        Article article = this.lambdaQuery()
            .select(Article::getId, Article::getViewCount)
            .eq(Article::getId, id)
            .one();
        if (article != null) {
            this.lambdaUpdate()
                .set(Article::getViewCount, article.getViewCount() + 1)
                .eq(Article::getId, id)
                .update();
        }
    }

    @Override
    public List<ArticleBO> fetchHotArticles() {
        // 按照浏览量排序取前10
        return this.lambdaQuery()
            .eq(Article::getStatus, ArticleStatus.PUBLISHED)
            .orderByDesc(Article::getViewCount)
            .last("limit 10")
            .list()
            .stream()
            .map(article -> BeanUtil.copyProperties(article, ArticleBO.class))
            .toList();
    }
}