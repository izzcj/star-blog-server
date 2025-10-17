package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.ale.starblog.admin.blog.domain.pojo.tag.CreateTagDTO;
import com.ale.starblog.admin.blog.domain.pojo.tag.ModifyTagDTO;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 标签服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
public interface ITagService extends ICrudService<Tag, TagBO, CreateTagDTO, ModifyTagDTO> {
}