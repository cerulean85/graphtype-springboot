package com.graphtype.controller;

import com.graphtype.component.AwsS3Uploader;
import com.graphtype.model.Article;
import com.graphtype.model.ArticleResponse;
import com.graphtype.model.BotBoardVO;
import com.graphtype.model.File;
import com.graphtype.repository.ArticleResourceDAO;
import com.graphtype.repository.BotBoardDAO;
import com.graphtype.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleResourceDAO articleResourceDAO;

    private final AwsS3Uploader awsS3Uploader;

    @GetMapping("/article/{author}/{articleId}")
    Mono<ResponseEntity<Article>> getItem (
            @PathVariable("author") String author,
            @PathVariable("articleId") String articleId)
    {
        Mono<Article> res = articleService.getItem(author, articleId);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/chat/feedback/{author}/{articleId}")
    Mono<ResponseEntity<Article>> getFeedback (
            @PathVariable("author") String author,
            @PathVariable("articleId") String articleId)
    {
        Mono<Article> res = articleService.getFeedback(author, articleId);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/article")
    Mono<ResponseEntity<Article>> createItem(@RequestBody Article article)
    {
//        if (article.getArticleId().isEmpty())
        article.setArticleId(null);
        Mono<Article> res = articleService.createItem(article);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/articles/{author}/{pageNo}")
    public Mono<ResponseEntity<ArticleResponse>> getItems(
            @PathVariable("author") String author,
            @PathVariable("pageNo") int pageNo)
    {
        Mono<ArticleResponse> res = articleService.getItems(author, pageNo);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/articles/search/{searchText}/{pageNo}")
    public Mono<ResponseEntity<ArticleResponse>> searchItems(
            @PathVariable("searchText") String searchText,
            @PathVariable("pageNo") int pageNo)
    {
        Mono<ArticleResponse> res = articleService.searchItems(searchText, pageNo);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/article")
    Mono<ResponseEntity<Boolean>> deleteItem(@RequestBody Article article)
    {
        Mono<Boolean> res = articleService.deleteItem(article, awsS3Uploader);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/article")
    public Mono<ResponseEntity<String>> updateItem(@RequestBody Article article)
    {
        article.setFeedback(null);
        Mono<String> res = articleService.updateItem(article);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/rechat")
    Mono<ResponseEntity<Boolean>> rechat(@RequestBody Article article)
    {
        Mono<Boolean> res = articleService.rechat(article.getArticleId());
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/uploadFile")
    public Mono<ResponseEntity<File>> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("articleId") String articleId
    ) throws IOException {
        String fileName = awsS3Uploader.upload(multipartFile, "images");
        File file = new File();
        file.url = fileName;
        Mono<File> res = Mono.just(file);
        articleService.updateResourceMeta(res.block(), articleId);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteFile")
    public Mono<ResponseEntity<File>> deleteFile(@RequestBody File file) {
        awsS3Uploader.delete(file.resourceId);
        Mono<File> res = Mono.just(file);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}