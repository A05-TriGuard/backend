package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.ArticleFavorites;

import java.util.List;

public interface ArticleFavoritesService extends IService<ArticleFavorites> {

    ArticleFavorites addArticleFavorites(Integer accountId, Integer articleId);

    List<ArticleFavorites> getDiseaseArticleFavorites(Integer accountId, Integer page, Integer size);

    List<ArticleFavorites> getScienceArticleFavorites(Integer accountId, Integer page, Integer size);

    ArticleFavorites getArticleFavorites(Integer accountId, Integer articleId);

    boolean deleteArticleFavorites(Integer accountId, Integer articleId);

}
