package com.ale.starblog.admin.blog.service.impl;

import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.ale.starblog.admin.blog.domain.pojo.tag.TagBO;
import com.ale.starblog.admin.blog.domain.pojo.tag.CreateTagDTO;
import com.ale.starblog.admin.blog.domain.pojo.tag.ModifyTagDTO;
import com.ale.starblog.admin.blog.mapper.TagMapper;
import com.ale.starblog.admin.blog.service.ITagService;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 博客标签服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Service
public class TagServiceImpl extends AbstractCrudServiceImpl<TagMapper, Tag, TagBO, CreateTagDTO, ModifyTagDTO> implements ITagService {

}