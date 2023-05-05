package com.graphtype.application.repository;

import com.graphtype.application.model.article.Article;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@EnableScan
public interface  ArticleRepository extends JpaRepository<Article, String> {
}

//@Repository
//public class ArticleRepository {
//
//    @PersistenceContext    // EntityManagerFactory가 DI 할 수 있도록 어노테이션 설정
//    private EntityManager em;
//
////    public List<Article> findAll(){
////        String jpql = "SELECT gb FROM article gb ORDER BY gb.regDate DESC";
////        TypedQuery<Article> query = em.createQuery(jpql, Article.class);
//
//        return new List<Article>();
//    }
//}