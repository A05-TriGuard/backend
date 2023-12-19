package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Article;
import com.triguard.backend.entity.dto.ArticleFavorites;
import com.triguard.backend.mapper.ArticleMapper;
import com.triguard.backend.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /**
     * 获取疾病预防文章
     * @param page 页数
     * @param size 每页大小
     * @return 疾病预防文章列表
     */
    public List<Article> getDiseaseArticle(Integer page, Integer size) {
        Page<Article> articlePage = new Page<>(page, size);
        return this.query()
                .eq("type", "disease")
                .page(articlePage)
                .getRecords();
    }

    /**
     * 获取科普文章
     * @param page 页数
     * @param size 每页大小
     * @return 科普文章列表
     */
    public List<Article> getScienceArticle(Integer page, Integer size) {
        Page<Article> articlePage = new Page<>(page, size);
        return this.query()
                .eq("type", "science")
                .page(articlePage)
                .getRecords();
    }

    /**
     * 发布文章
     * @param article
     * @return Article
     */
    public Article createArticle(Article article) {
        return this.save(article) ? article : null;
    }

}
