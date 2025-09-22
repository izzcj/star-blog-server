package com.ale.starblog.admin.blog.service.impl;

import com.ale.starblog.admin.blog.domain.entity.BlogTag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.ale.starblog.admin.blog.mapper.BlogTagMapper;
import com.ale.starblog.admin.blog.service.IBlogTagService;
import com.ale.starblog.admin.blog.service.ITagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客标签关联服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
@RequiredArgsConstructor
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements IBlogTagService {

    /**
     * 博客标签服务
     */
    private final ITagService blogTagService;

    @Override
    public List<TagBO> getTagsByBlogId(Long blogId) {
        // 查询博客关联的标签ID列表
        List<Long> tagIds = this.lambdaQuery()
            .eq(BlogTag::getBlogId, blogId)
            .list()
            .stream()
            .map(BlogTag::getTagId)
            .collect(Collectors.toList());

        // 根据标签ID列表查询标签信息
        if (!tagIds.isEmpty()) {
            return blogTagService.listByIds(tagIds)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlogTags(Long blogId, List<Long> tagIds) {
        // 删除原有的关联关系
        this.remove(new LambdaQueryWrapper<BlogTag>()
                .eq(BlogTag::getBlogId, blogId));

        // 添加新的关联关系
        if (tagIds != null && !tagIds.isEmpty()) {
            List<BlogTag> relations = tagIds.stream()
                    .map(tagId ->
                        BlogTag.builder()
                            .blogId(blogId)
                            .tagId(tagId)
                            .build()
                    )
                    .collect(Collectors.toList());
            this.saveBatch(relations);
        }
    }
}