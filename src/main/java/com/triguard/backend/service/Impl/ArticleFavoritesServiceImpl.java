package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.ArticleFavorites;
import com.triguard.backend.mapper.ArticleFavoritesMapper;
import com.triguard.backend.service.ArticleFavoritesService;
import com.triguard.backend.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleFavoritesServiceImpl extends ServiceImpl<ArticleFavoritesMapper, ArticleFavorites> implements ArticleFavoritesService {

    @Resource
    ArticleService articleService;

    /**
     * 添加文章收藏
     * @param accountId 用户id
     * @param articleId 文章id
     * @return 更新后的文章收藏实体类
     */
    public ArticleFavorites addArticleFavorites(Integer accountId, Integer articleId) {
        ArticleFavorites articleFavorites = new ArticleFavorites();
        articleFavorites.setAccountId(accountId);
        articleFavorites.setArticleId(articleId);
        articleFavorites.setArticleType(articleService.getById(articleId).getType());
        return this.save(articleFavorites) ? articleFavorites : null;
    }

    /**
     * 根据用户id获取疾病预防文章收藏列表
     * @param accountId 用户id
     * @return 文章收藏列表
     */
    public List<ArticleFavorites> getDiseaseArticleFavorites(Integer accountId, Integer page, Integer size) {
        Page<ArticleFavorites> articleFavoritesPage = new Page<>(page, size);
        return this.query()
                .eq("account_id", accountId)
                .eq("article_type", "disease")
                .page(articleFavoritesPage)
                .getRecords();
    }

    /**
     * 根据用户id获取科普文章收藏列表
     * @param accountId 用户id
     * @return 文章收藏列表
     */
    public List<ArticleFavorites> getScienceArticleFavorites(Integer accountId, Integer page, Integer size) {
        Page<ArticleFavorites> articleFavoritesPage = new Page<>(page, size);
        return this.query()
                .eq("account_id", accountId)
                .eq("article_type", "science")
                .page(articleFavoritesPage)
                .getRecords();
    }

    /**
     * 根据用户id和文章id获取文章收藏
     * @param accountId 用户id
     * @param articleId 文章id
     * @return 文章收藏
     */
    public ArticleFavorites getArticleFavorites(Integer accountId, Integer articleId) {
        return this.query()
                .eq("account_id", accountId)
                .eq("article_id", articleId)
                .one();
    }

    /**
     * 根据用户id和文章id删除文章收藏
     * @param accountId 用户id
     * @param articleId 文章id
     * @return 是否删除成功
     */
    public boolean deleteArticleFavorites(Integer accountId, Integer articleId) {
        return this.removeById(this.getArticleFavorites(accountId, articleId).getId());
    }
}
