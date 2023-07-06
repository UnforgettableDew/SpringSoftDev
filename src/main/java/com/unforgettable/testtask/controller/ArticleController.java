package com.unforgettable.testtask.controller;

import com.unforgettable.testtask.entity.Article;
import com.unforgettable.testtask.enums.RequestType;
import com.unforgettable.testtask.model.CommonResponse;
import com.unforgettable.testtask.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/site/{siteId}/list")
    public ResponseEntity<List<Article>> findArticlesBySiteId(@PathVariable Long siteId) {
        return ResponseEntity.ok(articleService.findArticlesBySiteId(siteId));
    }
    @PostMapping("/site/{siteId}")
    public ResponseEntity<CommonResponse> doArticlesBySiteId(@PathVariable Long siteId,
                                                             @RequestBody List<@Valid Article> articles,
                                                             @RequestHeader(name = "Request-type") RequestType requestType) {
        switch (requestType.name()){
            case "POST":
                return ResponseEntity.ok(articleService.addArticlesBySiteId(siteId, articles));
            case "PUT":
                return ResponseEntity.ok(articleService.updateArticlesBySiteId(siteId, articles));
            case "DELETE":
                return ResponseEntity.ok(articleService.deleteArticlesBySiteId(siteId, articles));
            default:
                return ResponseEntity.badRequest().body(new CommonResponse("Request-type header is invalid"));
        }
    }

    @PostMapping("/duplicates")
    public ResponseEntity<List<Article>> findDuplicateArticles(@RequestBody @Valid Article article) {
        return ResponseEntity.ok(articleService.findDuplicateArticles(article));
    }
}
