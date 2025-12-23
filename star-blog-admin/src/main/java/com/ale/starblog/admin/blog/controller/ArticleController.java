package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.pojo.article.*;
import com.ale.starblog.admin.blog.domain.pojo.tag.ArticleTagVO;
import com.ale.starblog.admin.blog.service.IArticleService;
import com.ale.starblog.admin.blog.service.IArticleTagService;
import com.ale.starblog.admin.system.constants.DictTypeConstants;
import com.ale.starblog.admin.system.constants.SystemConfigConstants;
import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.service.IDictDataService;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.core.controller.BaseController;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
public class ArticleController extends BaseController<Article, IArticleService, ArticleVO, ArticleBO, ArticleQuery, CreateArticleDTO, ModifyArticleDTO> {

    /**
     * 文章标签关联服务
     */
    private final IArticleTagService articleTagService;

    /**
     * 系统配置服务
     */
    private final ISystemConfigService systemConfigService;

    /**
     * 字典数据服务
     */
    private final IDictDataService dictDataService;

    /**
     * 获取文章详情
     *
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping("/detail/{id}")
    public JsonResult<ArticleDetailsVO> fetchDetail(@PathVariable(name = "id") Long id) {
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
     * 获取文章分类导航栏
     *
     * @return 文章分类导航栏
     */
    @GetMapping("/category-navbar")
    public JsonResult<List<ArticleCategoryNavbarVO>> fetchCategoryNavbar() {
        List<String> homeArticleCategoryNavbarConfig = this.systemConfigService.fetchValueByKey(SystemConfigConstants.HOME_ARTICLE_CATEGORY_NAVBAR);
        if (CollectionUtil.isEmpty(homeArticleCategoryNavbarConfig)) {
            throw new ServiceException("系统未配置文章分类导航栏！");
        }
        List<DictData> articleCategories = this.dictDataService.lambdaQuery()
            .eq(DictData::getDictKey, DictTypeConstants.DICT_TYPE_ARTICLE_CATEGORY)
            .in(DictData::getDictValue, homeArticleCategoryNavbarConfig)
            .list();
        if (CollectionUtil.isEmpty(articleCategories)) {
            throw new ServiceException("文章类型不存在！");
        }
        Map<String, Long> categoryCountMapping = this.service.fetchCategoryCountMapping(homeArticleCategoryNavbarConfig);
        return JsonResult.success(
            articleCategories
                .stream()
                .map(articleCategory ->
                    ArticleCategoryNavbarVO.builder()
                        .categoryLabel(articleCategory.getDictLabel())
                        .categoryValue(articleCategory.getDictValue())
                        .articleCount(categoryCountMapping.getOrDefault(articleCategory.getDictValue(), 0L).intValue())
                        .cssClass(articleCategory.getCssClass())
                        .build()
                )
                .collect(Collectors.toList())
        );
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

    /**
     * 发布文章
     *
     * @param id 文章id
     * @return Void
     */
    @PutMapping("/{id}/publish")
    public JsonResult<Void> publish(@PathVariable(name = "id") Long id) {
        this.service.publish(id);
        return JsonResult.success();
    }

    /**
     * 切换置顶状态
     *
     * @param id 文章id
     * @return Void
     */
    @PutMapping("/{id}/toggle-top")
    public JsonResult<Void> toggleTop(@PathVariable(name = "id") Long id) {
        this.service.toggleTop(id);
        return JsonResult.success();
    }

    /**
     * 切换推荐状态
     *
     * @param id 文章id
     * @return Void
     */
    @PutMapping("/{id}/toggle-recommend")
    public JsonResult<Void> toggleRecommend(@PathVariable(name = "id") Long id) {
        this.service.toggleRecommend(id);
        return JsonResult.success();
    }
}