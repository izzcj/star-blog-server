package com.ale.starblog.admin.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.blog.domain.entity.Activity;
import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityQuery;
import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityVO;
import com.ale.starblog.admin.blog.service.ActivityService;
import com.ale.starblog.admin.blog.service.ArticleService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.core.query.QueryConditionResolver;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * /博客管理/动态管理
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/activity")
public class ActivityController {

    /**
     * 动态服务
     */
    private final ActivityService activityService;

    /**
     * 文章服务
     */
    private final ArticleService articleService;

    /**
     * 分页查询动态
     *
     * @param pageable 分页参数
     * @param query    查询参数
     * @return 动态分页数据
     */
    @GetMapping("/page")
    public JsonPageResult<ActivityVO> page(Pageable pageable, ActivityQuery query) {
        Page<Activity> activityPage = this.activityService.page(
            new Page<>(pageable.getPageNumber(), pageable.getPageSize()),
            QueryConditionResolver.resolveLambda(query)
        );
        return JsonPageResult.of(activityPage, activities -> {
            List<ActivityVO> result = BeanUtil.copyToList(activities, ActivityVO.class);
            Map<Long, String> articleTitleMapping = this.articleService.fetchTitleMapping(
                result.stream()
                    .map(ActivityVO::getArticleId)
                    .toList()
            );
            result.forEach(activity -> {
                activity.setArticleTitle(articleTitleMapping.get(activity.getArticleId()));
                GenericTranslationSupport.translate(activity);
            });
            return result;
        });
    }

}
