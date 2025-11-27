package com.ale.starblog.admin.blog.controller;

import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.ale.starblog.admin.blog.domain.pojo.tag.*;
import com.ale.starblog.admin.blog.service.ITagService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class TagController extends BaseController<Tag, ITagService, TagVO, TagBO, CreateTagDTO, ModifyTagDTO> {

    /**
     * 通过id获取标签
     *
     * @param id 标签id
     * @return 标签信息
     */
    @GetMapping("/{id}")
    public JsonResult<TagVO> fetchDetails(@PathVariable(name = "id") Long id) {
        return this.queryById(id);
    }

    /**
     * 分页获取标签
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 标签分页数据
     */
    @GetMapping("/page")
    public JsonPageResult<TagVO> fetchPage(Pageable pageable, TagQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 创建标签
     *
     * @param createTagDTO 创建标签dto
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateTagDTO createTagDTO) {
        return this.createEntity(createTagDTO);
    }

    /**
     * 修改标签
     *
     * @param modifyTagDTO 修改标签dto
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyTagDTO modifyTagDTO) {
        return this.modifyEntity(modifyTagDTO);
    }

    /**
     * 删除标签
     *
     * @param id 标签id
     * @return Void
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }
}