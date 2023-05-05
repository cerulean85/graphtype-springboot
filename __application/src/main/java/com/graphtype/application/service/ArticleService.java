package com.graphtype.application.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.graphtype.application.config.DynamoDbConfig;
import com.graphtype.application.model.article.Article;
import com.graphtype.application.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class ArticleService {
//    private final DynamoDBMapper dynamoDBMapper;
//    private final DynamoDbConfig dynamoDbConfig;

    @Autowired
    private ArticleRepository articleRepository;

//    public ArticleService(DynamoDBMapper dynamoDBMapper, DynamoDbConfig dynamoDbConfig) {
//        this.dynamoDBMapper = dynamoDBMapper;
//        this.dynamoDbConfig = dynamoDbConfig;
//    }

//    public Article createArticle(Article member) {
//        Article savedMember = articleRepository.save(member);  // JpaRepository에서 제공하는 save() 함수
//        return savedMember;
//    }
//    public List<Article> getArticles() {
//        return articleRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
//    }


//    public ArticleDTO createNewArticle(ArticleDTO dto) {
//
//        Article article = new Article();
//        BeanUtils.copyProperties(dto, article);
//
//        dynamoDBMapper.save(article);
//
//        ArticleDTO response = new ArticleDTO();
//        BeanUtils.copyProperties(article, response);
//
//        return response;
//    }
}
