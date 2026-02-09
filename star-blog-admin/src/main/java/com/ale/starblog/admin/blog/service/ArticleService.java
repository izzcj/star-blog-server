package com.ale.starblog.admin.blog.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.entity.ArticleTag;
import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityBO;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleBO;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleQuery;
import com.ale.starblog.admin.blog.enums.ArticleStatus;
import com.ale.starblog.admin.blog.listener.ActivityPublishedEvent;
import com.ale.starblog.admin.blog.mapper.ArticleMapper;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文章服务
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
@RequiredArgsConstructor
public class ArticleService extends AbstractCrudService<ArticleMapper, Article, ArticleBO, ArticleQuery> {

    /**
     * 文章标签关联服务
     */
    private final ArticleTagService articleTagService;

    @Override
    public void beforeQuery(LambdaQueryWrapper<Article> queryWrapper, HookContext context) {
        ArticleQuery articleQuery = context.get(HookConstants.QUERY_KEY);
        if (articleQuery.getTagId() != null) {
            Set<Long> articleIds = this.articleTagService.lambdaQuery()
                .eq(ArticleTag::getTagId, articleQuery.getTagId())
                .list()
                .stream()
                .map(ArticleTag::getArticleId)
                .collect(Collectors.toSet());
            if (articleIds.isEmpty()) {
                context.setTermination(true);
                return;
            }
            queryWrapper.in(Article::getId, articleIds);
        }
    }

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

    /**
     * 增加文章浏览量
     * 访问量比较大时可以使用Redis进行缓存
     *
     * @param id 文章ID
     */
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

    /**
     * 获取文章标题映射
     *
     * @param ids 文章ID集合
     * @return 文章标题映射
     */
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

    /**
     * 获取文章分类数量映射
     *
     * @param categories 文章分类集合
     * @return 文章分类数量映射
     */
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

    /**
     * 发布文章
     *
     * @param id 文章ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long id) {
        Article article = this.lambdaQuery()
            .select(Article::getId, Article::getStatus, Article::getCreateBy, Article::getCreateTime)
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

    /**
     * 切换置顶状态
     *
     * @param id 文章ID
     */
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

    /**
     * 切换推荐状态
     *
     * @param id 文章ID
     */
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