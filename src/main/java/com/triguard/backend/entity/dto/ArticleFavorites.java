package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_article_favorites")
@AllArgsConstructor
public class ArticleFavorites {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer articleId;
    String articleType;
    Date createdAt;

    public ArticleFavorites() {
        this.id = null;
        this.accountId = null;
        this.articleId = null;
        this.createdAt = new Date();
    }
}
