package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.pojo.article.*;
import com.ale.starblog.admin.blog.domain.pojo.tag.ArticleTagVO;
import com.ale.starblog.admin.blog.service.IArticleService;
import com.ale.starblog.admin.blog.service.IArticleTagService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.core.controller.BaseController;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * /博客管理/文章管理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/article")
public class ArticleController extends BaseController<Article, IArticleService, ArticleVO, ArticleBO, CreateArticleDTO, ModifyArticleDTO> {

    /**
     * 文章标签关联服务
     */
    private final IArticleTagService articleTagService;

    /**
     * 通过id获取文章
     *
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping("/{id}")
    public JsonResult<ArticleDetailsVO> fetchDetails(@PathVariable(name = "id") Long id) {
        Article article = Optional.ofNullable(this.service.getById(id))
            .orElseThrow(() -> new ServiceException("文章不存在"));
        ArticleDetailsVO result = BeanUtil.copyProperties(article, ArticleDetailsVO.class);
        GenericTranslationSupport.translate(result);
        // 获取文章关联的标签
        List<ArticleTagVO> tags = this.articleTagService.fetchTagsByArticleId(id)
            .stream()
            .map(tagBO ->
                ArticleTagVO.builder()
                    .id(tagBO.getId())
                    .name(tagBO.getName())
                    .color(tagBO.getColor())
                    .build()
            )
            .collect(Collectors.toList());
        result.setTags(tags);
        return JsonResult.success(result);
    }

    /**
     * 分页获取文章
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 文章分页数据
     */
    @GetMapping("/page")
    public JsonPageResult<ArticleVO> fetchPage(Pageable pageable, ArticleQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 获取热门文章
     *
     * @return 热门文章
     */
    @GetMapping("/hot")
    public JsonResult<List<ArticleVO>> fetchHotBlogs() {
        return JsonResult.success(
            this.service.fetchHotArticles()
                .stream()
                .map(articleBO -> BeanUtil.copyProperties(articleBO, ArticleVO.class))
                .peek(GenericTranslationSupport::translate)
                .collect(Collectors.toList())
        );
    }

    /**
     * 创建文章
     *
     * @param createArticleDTO 创建文章dto
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateArticleDTO createArticleDTO) {
        return this.createEntity(createArticleDTO);
    }

    /**
     * 修改文章
     *
     * @param modifyArticleDTO 修改文章dto
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyArticleDTO modifyArticleDTO) {
        return this.modifyEntity(modifyArticleDTO);
    }

    /**
     * 删除文章
     *
     * @param id 文章id
     * @return Void
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }

    /**
     * 增加文章浏览量
     *
     * @param id 文章id
     * @return Void
     */
    @PutMapping("/{id}/view-count")
    public JsonResult<Void> incrementViewCount(@PathVariable(name = "id") Long id) {
        this.service.incrementViewCount(id);
        return JsonResult.success();
    }
}