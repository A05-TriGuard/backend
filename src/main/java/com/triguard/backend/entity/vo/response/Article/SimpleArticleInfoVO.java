package com.triguard.backend.entity.vo.response.Article;

import lombok.Data;

/**
 * 文章简单信息
 */
@Data
public class SimpleArticleInfoVO {
    Integer id;
    String title;
    String subtitle;
    String cover;
}
