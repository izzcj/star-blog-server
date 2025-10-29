package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Blog;
import com.ale.starblog.admin.blog.domain.pojo.blog.BlogBO;
import com.ale.starblog.admin.blog.domain.pojo.blog.CreateBlogDTO;
import com.ale.starblog.admin.blog.domain.pojo.blog.ModifyBlogDTO;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 博客服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
public interface IBlogService extends ICrudService<Blog, BlogBO, CreateBlogDTO, ModifyBlogDTO> {

    /**
     * 增加博客浏览量
     * 访问量比较大时可以使用Redis进行缓存
     *
     * @param id 博客ID
     */
    void incrementViewCount(Long id);
}