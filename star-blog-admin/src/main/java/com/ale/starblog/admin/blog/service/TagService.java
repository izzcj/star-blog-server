package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagQuery;
import com.ale.starblog.admin.blog.mapper.TagMapper;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * 标签服务
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
public class TagService extends AbstractCrudService<TagMapper, Tag, TagBO, TagQuery> {
}