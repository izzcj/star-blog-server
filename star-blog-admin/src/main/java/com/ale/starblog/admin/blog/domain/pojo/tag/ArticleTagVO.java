package com.ale.starblog.admin.blog.domain.pojo.tag;

import com.ale.starblog.framework.core.pojo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文章标签VO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ArticleTagVO extends BaseVO {

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 标签颜色
     */
    private String color;
}