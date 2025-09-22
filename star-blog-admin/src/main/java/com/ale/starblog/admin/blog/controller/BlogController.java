package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Blog;
import com.ale.starblog.admin.blog.domain.pojo.blog.*;
import com.ale.starblog.admin.blog.domain.pojo.tag.BlogTagVO;
import com.ale.starblog.admin.blog.service.IBlogService;
import com.ale.starblog.admin.blog.service.IBlogTagService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.core.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * /博客管理/博客管理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/blog")
public class BlogController extends BaseController<Blog, IBlogService, BlogVO, BlogBO, CreateBlogDTO, ModifyBlogDTO> {

    /**
     * 博客标签关联服务
     */
    private final IBlogTagService blogTagService;

    /**
     * 通过id获取博客
     *
     * @param id 博客id
     * @return 博客信息
     */
    @GetMapping("/{id}")
    public JsonResult<BlogDetailsVO> get(@PathVariable(name = "id") Long id) {
        Blog blog = Optional.ofNullable(this.service.getById(id))
            .orElseThrow(() -> new ServiceException("博客不存在"));
        BlogDetailsVO result = BeanUtil.copyProperties(blog, BlogDetailsVO.class);
        // 获取博客关联的标签
        List<BlogTagVO> tags = this.blogTagService.getTagsByBlogId(id)
            .stream()
            .map(tagBO ->
                BlogTagVO.builder()
                    .name(tagBO.getName())
                    .description(tagBO.getDescription())
                    .color(tagBO.getColor())
                    .build()
            )
            .collect(Collectors.toList());
        result.setTags(tags);
        return JsonResult.success(result);
    }

    /**
     * 分页获取博客
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 博客分页数据
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<BlogVO>> page(Pageable pageable, BlogQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 创建博客
     *
     * @param createBlogDTO 创建博客dto
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateBlogDTO createBlogDTO) {
        return this.createEntity(createBlogDTO);
    }

    /**
     * 修改博客
     *
     * @param modifyBlogDTO 修改博客dto
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyBlogDTO modifyBlogDTO) {
        return this.modifyEntity(modifyBlogDTO);
    }

    /**
     * 删除博客
     *
     * @param id 博客id
     * @return Void
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }

    /**
     * 增加博客浏览量
     *
     * @param id 博客id
     * @return Void
     */
    @PutMapping("/{id}/view-count")
    public JsonResult<Void> incrementViewCount(@PathVariable(name = "id") String id) {
        this.service.incrementViewCount(id);
        return JsonResult.success();
    }
}