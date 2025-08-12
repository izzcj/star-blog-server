package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.BlogTag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 博客标签服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
public interface IBlogTagService extends IService<BlogTag> {

    /**
     * 根据博客id获取标签
     *
     * @param blogId 博客id
     * @return 标签列表
     */
    List<TagBO> getTagsByBlogId(Long blogId);

    /**
     * 更新博客标签
     *
     * @param blogId 博客id
     * @param tagIds 标签id列表
     */
    void updateBlogTags(Long blogId, List<Long> tagIds);

}