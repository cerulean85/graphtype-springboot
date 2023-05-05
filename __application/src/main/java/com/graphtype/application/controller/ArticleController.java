package com.graphtype.application.controller;

import com.graphtype.application.model.article.Article;
import com.graphtype.application.model.article.ArticleDTO;
import com.graphtype.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

//    public ArticleController(ArticleService articleService) {
//        this.articleService = articleService;
//    }

//    @PostMapping("/")
//    public Article createNewArticle(@RequestBody ArticleDTO dto){
//        return articleService.createArticle(dto);
//    }

//    @PostMapping("create")
//    public ResponseEntity<Article> createMember() throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = format.parse("2011-12-03");
//        Article member = Article.builder()
//                .articleType("adult")
//                .title("herojoon432@gmail.com")
//                .build();
//        Article savedMember = articleService.createArticle(member);
//        return new ResponseEntity<>(savedMember, HttpStatus.OK);
//    }
//
//    @GetMapping("list")
//    public ResponseEntity<List<Article>> getMembers() {
//        List<Article> members = articleService.getArticles();
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }

}
