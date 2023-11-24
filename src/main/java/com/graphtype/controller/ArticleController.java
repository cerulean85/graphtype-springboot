package com.graphtype.controller;

import com.graphtype.component.AwsS3Uploader;
import com.graphtype.model.Article;
import com.graphtype.model.File;
import com.graphtype.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    private final AwsS3Uploader awsS3Uploader;

    @GetMapping("/article/{articleId}/{articleType}")
    Mono<ResponseEntity<Article>> getItem (
            @PathVariable("articleId") String articleId,
            @PathVariable("articleType") String articleType
    ) {
        Mono<Article> res = articleService.getItem(articleId, articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/article")
    Mono<ResponseEntity<Article>> createItem(@RequestBody Article article)
    {
        Mono<Article> res = articleService.createItem(article);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/article_to_text")
    Mono<ResponseEntity<Article>> createItemToText(
            @RequestBody Article article,
            @RequestPart("file") MultipartFile multipartFile)
    {
        Mono<Article> res = articleService.createItem(article);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/articles/{articleType}")
    public Mono<ResponseEntity<List<Article>>> getItemsByArticleType(
            @PathVariable("articleType") String articleType)
    {
        Mono<List<Article>> res = articleService.getItemsByArticleType(articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/article/{articleId}/{articleType}")
    Mono<ResponseEntity<Boolean>> deleteItem(
            @PathVariable("articleId") String articleId,
            @PathVariable("articleType") String articleType)
    {
        Mono<Boolean> res = articleService.deleteItem(articleId, articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/article/{articleId}")
    public Mono<ResponseEntity<String>> updateItem(
            @PathVariable("articleId") String articleId,
            @RequestBody Article article)
    {
        Mono<String> res = articleService.updateItem(articleId, article);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public Mono<ResponseEntity<File>> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = awsS3Uploader.upload(multipartFile, "images");
        File file = new File();
        file.url = fileName;
        Mono<File> res = Mono.just(file);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteFile")
    public Mono<ResponseEntity<File>> deleteFile(@RequestBody File file) {
        awsS3Uploader.delete(file.url);
        Mono<File> res = Mono.just(file);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}