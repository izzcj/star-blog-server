package com.ale.starblog.admin.blog.domain.pojo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文章详情VO
 *
 * @author Ale
 * @version 1.0.0 2025/9/22 16:19
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ArticleDetailsVO extends ArticleVO {

    /**
     * 文章内容
     */
    private String content;

}
