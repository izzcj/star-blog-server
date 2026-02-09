package com.ale.starblog.admin.blog.service;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.ArticleTag;
import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.ale.starblog.admin.blog.mapper.ArticleTagMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章标签关联服务
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
@RequiredArgsConstructor
public class ArticleTagService extends ServiceImpl<ArticleTagMapper, ArticleTag> {

    /**
     * 标签服务
     */
    private final TagService tagService;

    /**
     * 根据文章id获取标签
     *
     * @param articleId 文章id
     * @return 标签列表
     */
    public List<TagBO> fetchTagsByArticleId(Long articleId) {
        // 查询文章关联的标签ID列表
        List<Long> tagIds = this.lambdaQuery()
            .eq(ArticleTag::getArticleId, articleId)
            .list()
            .stream()
            .map(ArticleTag::getTagId)
            .collect(Collectors.toList());

        // 根据标签ID列表查询标签信息
        if (!tagIds.isEmpty()) {
            return this.tagService.listByIds(tagIds)
                    .stream()
                    .map(tag ->
                        TagBO.builder()
                            .id(tag.getId())
                            .name(tag.getName())
                            .description(tag.getDescription())
                            .color(tag.getColor())
                            .build()
                    )
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    /**
     * 获取热门标签
     *
     * @return 热门标签列表
     */
    public List<TagBO> fetchHotTags() {
        // 获取使用量前十的标签
        List<Long> tagIds = this.lambdaQuery()
            .select(ArticleTag::getTagId)
            .groupBy(ArticleTag::getTagId)
            .last("order by count(*) limit 10")
            .list()
            .stream()
            .map(ArticleTag::getTagId)
            .toList();
        if (tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        return BeanUtil.copyToList(
            this.tagService.lambdaQuery()
                .in(Tag::getId, tagIds)
                .list(),
            TagBO.class
        );
    }

    /**
     * 更新文章标签
     *
     * @param articleId 文章id
     * @param tagIds 标签id列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleTags(Long articleId, List<Long> tagIds) {
        // 删除原有的关联关系
        this.remove(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, articleId));

        // 添加新的关联关系
        if (tagIds != null && !tagIds.isEmpty()) {
            List<ArticleTag> relations = tagIds.stream()
                    .map(tagId ->
                        ArticleTag.builder()
                            .articleId(articleId)
                            .tagId(tagId)
                            .build()
                    )
                    .collect(Collectors.toList());
            this.saveBatch(relations);
        }
    }
}