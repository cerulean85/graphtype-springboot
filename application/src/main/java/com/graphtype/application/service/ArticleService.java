package com.graphtype.application.service;

import com.graphtype.application.model.Article;
import com.graphtype.application.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Mono<Article> getItem(String articleId, String articleType) {
        return Mono.just(articleRepository.getItem(articleId, articleType));
    }
    public Mono<Article> createItem(Article article) {
        return Mono.just(articleRepository.createItem(article));
    }
    public Mono<List<Article>> getItemsByArticleType(String articleType) {
        return Mono.just(articleRepository.getItemsByArticleType(articleType));
    }
    public Mono<Boolean> deleteItem(String articleId, String articleType) {
        return  Mono.just(articleRepository.deleteItem(articleId, articleType));
    }
    public Mono<String> updateItem(String articleId, Article article) {
        return Mono.just(articleRepository.updateItem(articleId, article));
    }
}
