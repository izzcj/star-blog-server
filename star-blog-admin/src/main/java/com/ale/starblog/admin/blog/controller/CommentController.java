package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.ale.starblog.admin.blog.domain.pojo.comment.*;
import com.ale.starblog.admin.blog.service.CommentService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.controller.BaseController;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
public class CommentController extends BaseController<Comment, CommentService, CommentVO, CommentBO, CommentQuery, CreateCommentDTO, ModifyCommentDTO> {

    @Override
    public JsonPageResult<CommentVO> fetchPage(Pageable pageable, CommentQuery query) {
        IPage<CommentBO> commentPage = this.service.fetchPage(pageable, query);
        return JsonPageResult.of(commentPage, comments -> {
            List<CommentVO> result = BeanUtil.copyToList(comments, CommentVO.class);
            return result.stream()
                .peek(GenericTranslationSupport::translate)
                .toList();
            }
        );
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
