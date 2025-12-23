package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.ale.starblog.admin.blog.domain.pojo.tag.*;
import com.ale.starblog.admin.blog.service.IArticleTagService;
import com.ale.starblog.admin.blog.service.ITagService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.support.Option;
import com.ale.starblog.framework.core.controller.BaseController;
import com.ale.starblog.framework.core.convert.OptionConvertible;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * /博客管理/标签管理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/tag")
public class TagController extends BaseController<Tag, ITagService, TagVO, TagBO, TagQuery, CreateTagDTO, ModifyTagDTO> {

    /**
     * 文章标签服务接口
     */
    private final IArticleTagService articleTagService;

    /**
     * 获取标签选项
     *
     * @param query 查询条件
     * @return 标签选项
     */
    @GetMapping("/options")
    public JsonResult<List<Option>> fetchOptions(TagQuery query) {
        List<TagBO> result = this.service.queryList(query);
        return JsonResult.success(
            result.stream()
                .map(OptionConvertible::convert)
                .toList()
        );
    }

    /**
     * 获取热门标签
     *
     * @return 热门标签
     */
    @GetMapping("/hot")
    public JsonResult<List<TagVO>> fetchHotTags() {
        List<TagBO> hotTags = this.articleTagService.fetchHotTags();
        return JsonResult.success(BeanUtil.copyToList(hotTags, TagVO.class));
    }

}