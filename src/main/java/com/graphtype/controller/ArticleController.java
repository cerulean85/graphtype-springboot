package com.graphtype.controller;

import com.graphtype.component.AwsS3Uploader;
import com.graphtype.model.Article;
import com.graphtype.model.File;
import com.graphtype.model.InventoryItem;
import com.graphtype.repository.ArticleResourceDAO;
import com.graphtype.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping("/article/{articleId}/{articleType}")
    Mono<ResponseEntity<Article>> getItem (
            @PathVariable("articleId") String articleId,
            @PathVariable("articleType") String articleType)
    {
        Mono<Article> res = articleService.getItem(articleId, articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/article")
    Mono<ResponseEntity<Article>> createItem(@RequestBody Article article)
    {
        Mono<Article> res = articleService.createItem(article);
        Article resultArticle = res.block();

        assert resultArticle != null;
        List<InventoryItem> inventory = resultArticle.getInventory();
        List<File> imageSrcList = getResources(resultArticle, inventory);
        articleResourceDAO.insertArticleResource(imageSrcList);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private List<File> getResources(Article resultArticle, List<InventoryItem> inventory) {
        List<File> imageSrcList = new ArrayList<>();
        for (InventoryItem item: inventory) {
            if (item.getType().equals("image")) {
                File resource = new File();
                resource.setArticleId(resultArticle.getArticleId());
                String[] temp = item.getContents().split("/");
                 if (temp.length == 0)
                     continue;
                String resourceId = "images/" + temp[temp.length-1];
                resource.setResourceId(resourceId);
                imageSrcList.add(resource);
            }
        }
        return imageSrcList;
    }

    @GetMapping("/articles/{articleType}")
    public Mono<ResponseEntity<List<Article>>> getItemsByArticleType(
            @PathVariable("articleType") String articleType)
    {
        Mono<List<Article>> res = articleService.getItemsByArticleType(articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/article")
    Mono<ResponseEntity<Boolean>> deleteItem(@RequestBody Article article)
    {
        String articleId = article.getArticleId();
        String articleType = article.getArticleType();
        Mono<Article> selectRes = articleService.getItem(articleId, articleType);
        Article readArticle = selectRes.block();

        assert readArticle != null;
        List<InventoryItem> inventory = readArticle.getInventory();
        List<File> imageSrcList = getResources(readArticle, inventory);
        for (File src: imageSrcList) {
            awsS3Uploader.delete(src.resourceId);
        }
        articleResourceDAO.deleteArticleResource(imageSrcList);

        Mono<Boolean> res = articleService.deleteItem(articleId, articleType);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/article")
    public Mono<ResponseEntity<String>> updateItem(
            @RequestBody Article article)
    {
        Mono<String> res = articleService.updateItem(article);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/uploadFile")
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
        awsS3Uploader.delete(file.resourceId);
        Mono<File> res = Mono.just(file);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}