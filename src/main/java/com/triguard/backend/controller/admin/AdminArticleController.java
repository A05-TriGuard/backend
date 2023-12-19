package com.triguard.backend.controller.admin;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Article;
import com.triguard.backend.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/admin/article")
@Tag(name = "管理员文章相关接口")
public class AdminArticleController {

    @Resource
    ArticleService articleService;

    /**
     * 发布文章
     *
     * @param article 文章
     * @return Article
     */
    @PostMapping("/create")
    @Operation(summary = "发布文章")
    public RestBean<Article> createArticle(@RequestBody Article article) {
        Article savedArticle = articleService.createArticle(article);
        return savedArticle == null ? RestBean.failure(400, "发布失败") : RestBean.success(savedArticle);
    }

    /**
     * 删除文章
     *
     * @param articleId 文章id
     * @return 是否删除成功
     */
    @GetMapping("/delete")
    @Operation(summary = "删除文章")
    public RestBean<Boolean> deleteArticle(@RequestParam Integer articleId) {
        return articleService.removeById(articleId) ? RestBean.success(true) : RestBean.failure(400, "删除失败");
    }
}
