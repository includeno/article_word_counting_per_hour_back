package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.Article;
import org.example.entity.ArticleVersion;
import org.example.service.logic.ArticleLogicService;
import org.example.utils.JsonResult;
import org.example.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleLogicService articleLogicService;

    @GetMapping("/article")
    public JsonResult<ArticleVO> getArticleById(Integer id) {
        ArticleVO result = articleLogicService.getArticleById(id);
        return JsonResult.ok(result);
    }

    @PostMapping("/article")
    public JsonResult<Boolean> addArticle(String title, String content) {
        Boolean result = articleLogicService.addArticle(title, content);
        return JsonResult.ok(result);
    }

    @PutMapping("/article")
    public JsonResult<Boolean> updateArticle(Integer id, String title, String content) {
        Boolean result = articleLogicService.updateArticle(id, title, content);
        return JsonResult.ok(result);
    }

    @GetMapping("/articles")
    public JsonResult<IPage<ArticleVO>> getArticles(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        Page<Article> articlePage = new Page<>(page, size);
        IPage<ArticleVO> articleVOPage = articleLogicService.getArticlesWithLatestVersion(articlePage);
        return JsonResult.ok(articleVOPage);
    }

    @GetMapping("/article/{articleId}/versions")
    public JsonResult<IPage<ArticleVersion>> getArticleVersions(@PathVariable Long articleId,
                                                                @RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        Page<ArticleVersion> articleVersionPage = new Page<>(page, size);
        IPage<ArticleVersion> articleVersionVOPage = articleLogicService.getArticleVersions(articleId, articleVersionPage);
        return JsonResult.ok(articleVersionVOPage);
    }
}

