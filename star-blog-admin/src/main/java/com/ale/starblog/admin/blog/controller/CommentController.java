package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.ale.starblog.admin.blog.domain.pojo.comment.*;
import com.ale.starblog.admin.blog.service.ICommentService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.controller.BaseController;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * /博客管理/评论管理
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:55
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/comment")
public class CommentController extends BaseController<Comment, ICommentService, CommentVO, CommentBO, CreateCommentDTO, ModifyCommentDTO> {

    /**
     * 查询单条评论详情
     *
     * @param id 评论ID
     * @return 评论详情
     */
    @GetMapping("/{id}")
    public JsonResult<CommentVO> fetchDetails(@PathVariable Long id) {
        return this.queryById(id);
    }

    /**
     * 查询评论列表
     *
     * @param pageable 分页参数
     * @param query    查询参数
     * @return 评论列表
     */
    @GetMapping("/page")
    public JsonPageResult<CommentVO> fetchPage(Pageable pageable, CommentQuery query) {
        return JsonPageResult.of(
            this.service.fetchPage(pageable, query),
            comments -> {
                List<CommentVO> result = BeanUtil.copyToList(comments, CommentVO.class);
                result.forEach(GenericTranslationSupport::translate);
                return result;
            }
        );
    }

    /**
     * 创建评论
     *
     * @param createDTO 创建DTO
     * @return 结果
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateCommentDTO createDTO) {
        return this.createEntity(createDTO);
    }

    /**
     * 删除评论
     *
     * @param id 评论ID
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable Long id) {
        return this.deleteEntity(id);
    }

    /**
     * 点赞评论
     *
     * @param id 评论ID
     * @return 结果
     */
    @PostMapping("/{id}/like")
    public JsonResult<Void> like(@PathVariable Long id) {
        this.service.likeComment(id, SecurityUtils.getLoginUserId());
        return JsonResult.success();
    }

    /**
     * 取消点赞
     *
     * @param id 评论ID
     * @return 结果
     */
    @DeleteMapping("/{id}/like")
    public JsonResult<Void> unlike(@PathVariable Long id) {
        this.service.unlikeComment(id, SecurityUtils.getLoginUserId());
        return JsonResult.success();
    }

    /**
     * 批量审核
     *
     * @param batchAuditCommentDTO 批量审核DTO
     * @return 结果
     */
    @PutMapping("/batch/audit")
    public JsonResult<Void> batchAudit(@RequestBody @Validated BatchAuditCommentDTO batchAuditCommentDTO) {
        this.service.batchAuditComments(
            batchAuditCommentDTO.getIds(),
            batchAuditCommentDTO.getStatus(),
            batchAuditCommentDTO.getRejectReason()
        );
        return JsonResult.success();
    }
}
