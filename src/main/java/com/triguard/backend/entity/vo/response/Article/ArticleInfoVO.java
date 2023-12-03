package com.triguard.backend.entity.vo.response.Article;

import lombok.Data;

import java.util.Date;

/**
 * 文章信息VO
 */
@Data
public class ArticleInfoVO {
    Integer id = null;
    String title = null;
    String subtitle = null;
    String content = null;
    Boolean isFavorite = null;
}
