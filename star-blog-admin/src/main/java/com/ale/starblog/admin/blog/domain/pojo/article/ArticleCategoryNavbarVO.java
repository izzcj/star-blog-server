package com.ale.starblog.admin.blog.domain.pojo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文章分类导航栏VO
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 10:55
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryNavbarVO {

    /**
     * 分类标签
     */
    private String categoryLabel;

    /**
     * 分类键值
     */
    private String categoryValue;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 样式
     */
    private String cssClass;

}
