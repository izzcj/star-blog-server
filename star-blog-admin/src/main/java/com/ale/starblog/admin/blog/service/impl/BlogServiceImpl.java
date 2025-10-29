package com.ale.starblog.admin.blog.service.impl;

import com.ale.starblog.admin.blog.domain.entity.Blog;
import com.ale.starblog.admin.blog.domain.pojo.blog.BlogBO;
import com.ale.starblog.admin.blog.domain.pojo.blog.CreateBlogDTO;
import com.ale.starblog.admin.blog.domain.pojo.blog.ModifyBlogDTO;
import com.ale.starblog.admin.blog.enums.BlogStatus;
import com.ale.starblog.admin.blog.mapper.BlogMapper;
import com.ale.starblog.admin.blog.service.IBlogService;
import com.ale.starblog.admin.blog.service.IBlogTagService;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 博客服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
@RequiredArgsConstructor
public class BlogServiceImpl extends AbstractCrudServiceImpl<BlogMapper, Blog, BlogBO, CreateBlogDTO, ModifyBlogDTO> implements IBlogService {

    /**
     * 博客标签关联服务
     */
    private final IBlogTagService blogTagService;

    @Override
    public void beforeCreate(Blog entity, HookContext context) {
        if (entity.getStatus() == null) {
            entity.setStatus(BlogStatus.DRAFT);
        }
    }

    @Override
    public void afterCreate(Blog entity, HookContext context) {
        CreateBlogDTO createDTO = CastUtils.cast(context.get(HookConstants.CREATE_DTO_KEY));
        // 保存博客标签关联关系
        if (createDTO.getTagIds() != null && !createDTO.getTagIds().isEmpty()) {
            this.blogTagService.updateBlogTags(entity.getId(), createDTO.getTagIds());
        }
    }

    @Override
    public void afterModify(Blog entity, HookContext context) {
        ModifyBlogDTO modifyBlogDTO = CastUtils.cast(context.get(HookConstants.MODIFY_DTO_KEY));
        // 更新博客标签关联关系
        this.blogTagService.updateBlogTags(entity.getId(), modifyBlogDTO.getTagIds());
    }

    @Override
    public void incrementViewCount(Long id) {
        Blog blog = this.getById(id);
        if (blog != null) {
            blog.setViewCount(blog.getViewCount() + 1);
            this.update(blog, null);
        }
    }
}