package com.graphtype.application.controller;

import com.graphtype.application.model.Article;
import com.graphtype.application.repository.ArticleRepository;
import com.graphtype.application.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired private ArticleService articleService;

    @GetMapping("/article/{articleId}/{articleType}")
    Mono<ResponseEntity<Article>> getItem (
            @PathVariable("articleId") String articleId,
            @PathVariable("articleType") String articleType
    ) {
        Mono<Article> res = articleService.getItem(articleId, articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping("/article")
    Mono<ResponseEntity<Article>> createItem(@RequestBody Article article) {
        Mono<Article> res = articleService.createItem(article);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/articles/{articleType}")
    public Mono<ResponseEntity<List<Article>>> getItemsByArticleType(
            @PathVariable("articleType") String articleType
    ) {
        Mono<List<Article>> res = articleService.getItemsByArticleType(articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/article/{articleId}/{articleType}")
    Mono<ResponseEntity<Boolean>> deleteItem(
            @PathVariable("articleId") String articleId,
            @PathVariable("articleType") String articleType
    ) {
        Mono<Boolean> res = articleService.deleteItem(articleId, articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
//
    @PutMapping("/article/{articleId}")
    public Mono<ResponseEntity<String>> updateItem(
            @PathVariable("articleId") String articleId,
            @RequestBody Article article)
    {
        Mono<String> res = articleService.updateItem(articleId, article);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
