package com.graphtype.service;


import com.graphtype.model.Article;
import com.graphtype.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Mono<String> updateItem(Article article) {
        return Mono.just(articleRepository.updateItem(article));
    }
}
