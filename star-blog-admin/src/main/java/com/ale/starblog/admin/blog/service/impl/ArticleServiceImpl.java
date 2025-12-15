package com.ale.starblog.admin.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityBO;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleBO;
import com.ale.starblog.admin.blog.enums.ArticleStatus;
import com.ale.starblog.admin.blog.listener.ActivityPublishedEvent;
import com.ale.starblog.admin.blog.mapper.ArticleMapper;
import com.ale.starblog.admin.blog.service.IArticleService;
import com.ale.starblog.admin.blog.service.IArticleTagService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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
        ArticleBO articleBO = CastUtils.cast(context.get(HookConstants.ENTITY_BO_KEY));
        // 保存文章标签关联关系
        if (articleBO.getTagIds() != null && !articleBO.getTagIds().isEmpty()) {
            this.articleTagService.updateArticleTags(entity.getId(), articleBO.getTagIds());
        }
    }

    @Override
    public void afterModify(Article entity, HookContext context) {
        ArticleBO articleBO = CastUtils.cast(context.get(HookConstants.ENTITY_BO_KEY));
        // 更新文章标签关联关系
        this.articleTagService.updateArticleTags(entity.getId(), articleBO.getTagIds());
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
    public Map<Long, String> fetchTitleMapping(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        return this.lambdaQuery()
            .select(Article::getId, Article::getTitle)
            .in(Article::getId, ids)
            .list()
            .stream()
            .collect(Collectors.toMap(Article::getId, Article::getTitle));
    }

    @Override
    public Map<String, Long> fetchCategoryCountMapping(Collection<String> categories) {
        if (CollectionUtil.isEmpty(categories)) {
            return Collections.emptyMap();
        }
        return this.lambdaQuery()
            .select(Article::getCategory, Article::getId)
            .in(Article::getCategory, categories)
            .list()
            .stream()
            .collect(Collectors.groupingBy(Article::getCategory, Collectors.counting()));
    }

    @Override
    public void publish(Long id) {
        Article article = this.lambdaQuery()
            .select(Article::getId, Article::getStatus)
            .eq(Article::getId, id)
            .oneOpt()
            .orElseThrow(() -> new ServiceException("发布失败！文章[{}]不存在！", id));
        if (article.getStatus() == ArticleStatus.PUBLISHED) {
            throw new ServiceException("发布文章失败！文章[{}]已发布！", id);
        }
        this.lambdaUpdate()
            .set(Article::getStatus, ArticleStatus.PUBLISHED)
            .set(Article::getPublishTime, LocalDateTime.now())
            .eq(Article::getId, id)
            .update();
        // 发布动态
        SpringUtil.publishEvent(new ActivityPublishedEvent(this, ActivityBO.convertFromArticle(article)));
    }

    @Override
    public void toggleTop(Long id) {
        Article article = this.lambdaQuery()
            .select(Article::getId, Article::getTop)
            .eq(Article::getId, id)
            .oneOpt()
            .orElseThrow(() -> new ServiceException("切换置顶状态失败！文章[{}]不存在！", id));
        this.lambdaUpdate()
            .set(Article::getTop, !article.getTop())
            .eq(Article::getId, id)
            .update();
    }

    @Override
    public void toggleRecommend(Long id) {
        Article article = this.lambdaQuery()
            .select(Article::getId, Article::getRecommended)
            .eq(Article::getId, id)
            .oneOpt()
            .orElseThrow(() -> new ServiceException("切换推荐状态失败！文章[{}]不存在！", id));
        this.lambdaUpdate()
            .set(Article::getRecommended, !article.getRecommended())
            .eq(Article::getId, id)
            .update();
    }
}