package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Article;
import com.triguard.backend.entity.dto.ArticleFavorites;

import java.util.List;

public interface ArticleService extends IService<Article> {

    List<Article> getDiseaseArticle(Integer page, Integer size);

    List<Article> getScienceArticle(Integer page, Integer size);

}
