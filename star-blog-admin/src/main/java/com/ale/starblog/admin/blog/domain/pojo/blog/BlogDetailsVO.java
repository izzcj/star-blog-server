package com.ale.starblog.admin.blog.domain.pojo.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 博客看详情VO
 *
 * @author Ale
 * @version 1.0.0 2025/9/22 16:19
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlogDetailsVO extends BlogVO {

    /**
     * 博客内容
     */
    private String content;

}
