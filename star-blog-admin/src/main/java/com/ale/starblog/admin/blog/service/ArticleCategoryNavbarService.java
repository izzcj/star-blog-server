package com.ale.starblog.admin.blog.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.blog.domain.pojo.article.ArticleCategoryNavbarVO;
import com.ale.starblog.admin.system.constants.DictTypeConstants;
import com.ale.starblog.admin.system.constants.SystemConfigConstants;
import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.service.DictDataService;
import com.ale.starblog.admin.system.service.SystemConfigService;
import com.ale.starblog.framework.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 博客分类导航栏服务
 *
 * @author Ale
 * @version 1.0.0 2026/2/9 13:50
 */
@Service
@RequiredArgsConstructor
public class ArticleCategoryNavbarService {

    /**
     * 文章服务
     */
    private final ArticleService articleService;

    /**
     * 系统配置服务
     */
    private final SystemConfigService systemConfigService;

    /**
     * 字典数据服务
     */
    private final DictDataService dictDataService;

    /**
     * 获取文章分类导航栏
     *
     * @return 文章分类导航集合
     */
    public List<ArticleCategoryNavbarVO> fetchCategoryNavbar() {
        List<String> homeArticleCategoryNavbarConfig = this.systemConfigService.fetchValueByKey(SystemConfigConstants.HOME_ARTICLE_CATEGORY_NAVBAR);
        if (CollectionUtil.isEmpty(homeArticleCategoryNavbarConfig)) {
            throw new ServiceException("系统未配置文章分类导航栏！");
        }
        List<DictData> articleCategories = this.dictDataService.lambdaQuery()
            .eq(DictData::getDictKey, DictTypeConstants.DICT_TYPE_ARTICLE_CATEGORY)
            .in(DictData::getDictValue, homeArticleCategoryNavbarConfig)
            .orderByAsc(DictData::getSort)
            .list();
        if (CollectionUtil.isEmpty(articleCategories)) {
            throw new ServiceException("文章类型不存在！");
        }
        Map<String, Long> categoryCountMapping = this.articleService.fetchCategoryCountMapping(homeArticleCategoryNavbarConfig);
        return articleCategories
            .stream()
            .sorted(Comparator.comparingInt(DictData::getSort))
            .map(articleCategory ->
                ArticleCategoryNavbarVO.builder()
                    .categoryLabel(articleCategory.getDictLabel())
                    .categoryValue(articleCategory.getDictValue())
                    .articleCount(categoryCountMapping.getOrDefault(articleCategory.getDictValue(), 0L).intValue())
                    .cssClass(articleCategory.getCssClass())
                    .build()
            )
            .collect(Collectors.toList());
    }

}
