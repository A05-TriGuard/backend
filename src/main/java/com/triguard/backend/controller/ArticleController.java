package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Article;
import com.triguard.backend.entity.dto.ArticleFavorites;
import com.triguard.backend.entity.vo.response.Article.ArticleInfoVO;
import com.triguard.backend.entity.vo.response.Article.SimpleArticleInfoVO;
import com.triguard.backend.service.ArticleFavoritesService;
import com.triguard.backend.service.ArticleService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/article")
@Tag(name = "疾病预防和科普文章相关接口")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @Resource
    ArticleFavoritesService articleFavoritesService;

    /**
     * 分页获取疾病预防文章列表
     * @param page 页数
     * @param size 每页大小
     * @return 疾病预防文章列表
     */
    @GetMapping("/disease/list")
    @Operation(summary = "分页获取疾病预防文章列表")
    public RestBean<List<SimpleArticleInfoVO>> getDiseaseArticle(@RequestParam(defaultValue = "1") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer size){
        List<Article> articles = articleService.getDiseaseArticle(page, size);
        List<SimpleArticleInfoVO> simpleArticleInfoVOS = articles.stream().map(article -> {
            SimpleArticleInfoVO simpleArticleInfoVO = new SimpleArticleInfoVO();
            BeanUtils.copyProperties(article, simpleArticleInfoVO);
            return simpleArticleInfoVO;
        }).toList();
        return RestBean.success(simpleArticleInfoVOS);
    }

    /**
     * 分页获取科普文章列表
     * @param page 页数
     * @param size 每页大小
     * @return 科普文章列表
     */
    @GetMapping("/science/list")
    @Operation(summary = "分页获取科普文章列表")
    public RestBean<List<SimpleArticleInfoVO>> getScienceArticle(@RequestParam(defaultValue = "1") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer size){
        List<Article> articles = articleService.getScienceArticle(page, size);
        List<SimpleArticleInfoVO> simpleArticleInfoVOS = articles.stream().map(article -> {
            SimpleArticleInfoVO simpleArticleInfoVO = new SimpleArticleInfoVO();
            BeanUtils.copyProperties(article, simpleArticleInfoVO);
            return simpleArticleInfoVO;
        }).toList();
        return RestBean.success(simpleArticleInfoVOS);
    }

    /**
     * 根据id获取文章
     * @param id 文章id
     * @return 文章
     */
    @GetMapping("/get")
    @Operation(summary = "根据id获取文章")
    public RestBean<ArticleInfoVO> getArticle(@RequestParam Integer id,
                                              HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Article article = articleService.getById(id);
        if (article == null) {
            return RestBean.failure(400, "文章不存在");
        }
        ArticleInfoVO articleInfoVO = new ArticleInfoVO();
        BeanUtils.copyProperties(article, articleInfoVO);
        ArticleFavorites articleFavorites = articleFavoritesService.getArticleFavorites(accountId, id);
        articleInfoVO.setIsFavorite(articleFavorites != null);
        return RestBean.success(articleInfoVO);
    }

    /**
     * 根据id收藏文章
     * @param articleId 文章id
     * @return 是否收藏成功
     */
    @PostMapping("/favorites/add")
    @Operation(summary = "根据id收藏文章")
    public RestBean<ArticleFavorites> addArticleFavorites(@RequestParam Integer articleId,
                                                          HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        ArticleFavorites articleFavorites = articleFavoritesService.addArticleFavorites(accountId, articleId);
        return articleFavorites != null ? RestBean.success(articleFavorites) : RestBean.failure(400, "收藏失败");
    }

    /**
     * 根据id取消收藏文章
     * @param articleId 文章id
     * @return 是否取消成功
     */
    @GetMapping("/favorites/delete")
    @Operation(summary = "根据id取消收藏文章")
    public RestBean<Void> deleteArticleFavorites(@RequestParam Integer articleId,
                                                 HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        boolean isSuccess = articleFavoritesService.deleteArticleFavorites(accountId, articleId);
        return isSuccess ? RestBean.success() : RestBean.failure(400, "取消收藏失败");
    }

    /**
     * 获取已收藏的疾病预防文章列表
     * @return 文章列表
     */
    @GetMapping("/favorites/disease/list")
    @Operation(summary = "获取已收藏的疾病预防文章列表")
    public RestBean<List<SimpleArticleInfoVO>> getArticleFavoritesList(@RequestParam Integer page,
                                                                       @RequestParam Integer size,
                                                                       HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<ArticleFavorites> articleFavorites = articleFavoritesService.getDiseaseArticleFavorites(accountId, page, size);
        if (articleFavorites == null) {
            return RestBean.success();
        }
        List<SimpleArticleInfoVO> simpleArticleInfoVOS = new java.util.ArrayList<>(articleFavorites.stream().map(articleFavorite -> {
            SimpleArticleInfoVO simpleArticleInfoVO = new SimpleArticleInfoVO();
            Article article = articleService.getById(articleFavorite.getArticleId());
            BeanUtils.copyProperties(article, simpleArticleInfoVO);
            return simpleArticleInfoVO;
        }).toList());
        simpleArticleInfoVOS.sort((o1, o2) -> o2.getId() - o1.getId());
        return RestBean.success(simpleArticleInfoVOS);
    }

    /**
     * 获取已收藏的科普文章列表
     * @return 响应结果
     */
    @GetMapping("/favorites/science/list")
    @Operation(summary = "获取已收藏的科普文章列表")
    public RestBean<List<SimpleArticleInfoVO>> getScienceArticleFavoritesList(@RequestParam Integer page,
                                                                              @RequestParam Integer size,
                                                                              HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<ArticleFavorites> articleFavorites = articleFavoritesService.getScienceArticleFavorites(accountId, page, size);
        if (articleFavorites == null) {
            return RestBean.success();
        }
        List<SimpleArticleInfoVO> simpleArticleInfoVOS = new java.util.ArrayList<>(articleFavorites.stream().map(articleFavorite -> {
            SimpleArticleInfoVO simpleArticleInfoVO = new SimpleArticleInfoVO();
            Article article = articleService.getById(articleFavorite.getArticleId());
            BeanUtils.copyProperties(article, simpleArticleInfoVO);
            return simpleArticleInfoVO;
        }).toList());
        simpleArticleInfoVOS.sort((o1, o2) -> o2.getId() - o1.getId());
        return RestBean.success(simpleArticleInfoVOS);
    }

}
