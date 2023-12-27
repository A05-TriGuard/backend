package com.triguard.backend.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Article;
import com.triguard.backend.service.ArticleService;
import com.triguard.backend.service.FileService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Validated
@RestController
@RequestMapping("/api/admin/article")
@Tag(name = "管理员文章相关接口")
public class AdminArticleController {

    @Resource
    ArticleService articleService;

    @Resource
    FileService fileService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 发布文章
     * @param type 文章类型
     * @param title 文章标题
     * @param subtitle 文章副标题
     * @param content 文章内容
     * @param cover 文章封面
     * @return 发布成功的文章
     */
    @PostMapping("/create")
    @Operation(summary = "发布文章")
    public RestBean<Article> createArticle(@RequestParam String type,
                                           @RequestParam String title,
                                           @RequestParam(required = false) String subtitle,
                                           @RequestParam String content,
                                           @RequestPart(required = false) MultipartFile cover,
                                           HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Article article = new Article();
        article.setType(type);
        article.setTitle(title);
        article.setSubtitle(subtitle);
        article.setContent(content);
        if (cover != null) {
            article.setCover(fileService.uploadMultipartFile(cover));
        }
        Article savedArticle = articleService.createArticle(article);
        if (savedArticle == null) {
            return RestBean.failure(400, "发布失败");
        } else {
            stringRedisTemplate.delete("article_draft_" + accountId);
            return RestBean.success(savedArticle);
        }
    }

    /**
     * 更新文章
     * @param articleId 文章id
     * @param type 文章类型
     * @param title 文章标题
     * @param subtitle 文章副标题
     * @param content 文章内容
     * @param cover 文章封面
     * @return 更新成功的文章
     */
    @PostMapping("/update")
    @Operation(summary = "更新文章")
    public RestBean<Article> updateArticle(@RequestParam Integer articleId,
                                           @RequestParam(required = false) String type,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String subtitle,
                                           @RequestParam String content,
                                           @RequestPart(required = false) MultipartFile cover) {
        Article article = articleService.getById(articleId);
        if (article == null) {
            return RestBean.failure(400, "文章不存在");
        }
        article.setType(type);
        article.setTitle(title);
        article.setSubtitle(subtitle);
        article.setContent(content);
        article.setCover(fileService.uploadMultipartFile(cover));
        return articleService.updateById(article) ? RestBean.success(article) : RestBean.failure(400, "更新失败");
    }

    /**
     * 创建文章草稿
     * @param type 文章类型
     * @param title 文章标题
     * @param subtitle 文章副标题
     * @param content 文章内容
     * @param request 请求
     * @return 创建成功的文章草稿
     */
    @PostMapping("/draft")
    @Operation(summary = "创建文章草稿，每个人只能有一个草稿，为了简化这里不能设置封面")
    public RestBean<Article> createArticleDraft(@RequestParam String type,
                                                @RequestParam String title,
                                                @RequestParam(required = false) String subtitle,
                                                @RequestParam String content,
                                                HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Article article = new Article();
        article.setType(type);
        article.setTitle(title);
        article.setSubtitle(subtitle);
        article.setContent(content);
        article.setCreatedAt(new Date());
        article.setUpdatedAt(new Date());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String articleDraft = objectMapper.writeValueAsString(article);
            stringRedisTemplate.opsForValue().set("article_draft_" + accountId, articleDraft);
        } catch (Exception e) {
            return RestBean.failure(400, "创建失败");
        }
        return RestBean.success();
    }

    /**
     * 获取文章草稿
     * @param request 请求
     * @return 文章草稿
     */
    @GetMapping("/draft")
    @Operation(summary = "获取文章草稿")
    public RestBean<Article> getArticleDraft(HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String articleDraft = stringRedisTemplate.opsForValue().get("article_draft_" + accountId);
        if (articleDraft == null) {
            return RestBean.failure(400, "草稿不存在");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Article article = objectMapper.readValue(articleDraft, Article.class);
            return RestBean.success(article);
        } catch (Exception e) {
            return RestBean.failure(400, "获取失败");
        }
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
