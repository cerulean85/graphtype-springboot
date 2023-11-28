package com.graphtype.repository;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.graphtype.etc.DateTimeUtil;
import com.graphtype.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Article createItem(Article article) {
        article.createdAt = DateTimeUtil.getCurrentDateTime();
        article.updatedAt = article.createdAt;
        dynamoDBMapper.save(article);
        return article;
    }
    public Article getItem(String articleId, String articleType) {
        return dynamoDBMapper.load(Article.class, articleId, articleType);
    }

    public List<Article> getItemsByArticleType(String articleType) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1", new AttributeValue().withS(articleType));

        DynamoDBScanExpression scanExp = new DynamoDBScanExpression()
                .withFilterExpression("articleType = :v1")
                .withExpressionAttributeValues(eav);
        List<Article> items= dynamoDBMapper.scan(Article.class, scanExp);
        return items;

//        do {

//            DynamoDBQueryExpression<Article> queryExp = new DynamoDBQueryExpression<Article>()
////                    .withKeyConditionExpression("articleId")
//                    .withIndexName("ArticleTypeNameIndex")
//                    .withConsistentRead(false)
//                    .withKeyConditionExpression("articleTypeName = :articleType")
//                    .withExpressionAttributeValues(eav);
//            List<Article> results= dynamoDBMapper.query(Article.class, queryExp);

//            ScanRequest scanRequest = new ScanRequest()
//                    .withTableName("Article")
//                    .withFilterExpression("articleType = :articleType")
//                    .withExpressionAttributeValues(eav)
//                    .withProjectionExpression("articleId");
//            ScanResult scanResult = dynamoDBMapper.scan(scanRequest);
//            scanResult.getItems().stream().forEach(System.out::println);
//            lastKeyEvaluated = scanResult.getLastEvaluatedKey();
//        }
//            .withTableName("customers")
//                .withExpressionAttributeValues(values) //2
//                .withFilterExpression("firstName = :val") //3
//                .withProjectionExpression("id"); //4
//
//        List<Article> items = dynamoDBMapper.query(Article.class, queryExpression);
//        return items;

    }

    public boolean deleteItem(String articleId, String articleType) {
        try {
            dynamoDBMapper.delete(dynamoDBMapper.load(Article.class, articleId, articleType));
        } catch (Exception err) {
            return false;
        }
        return true;
    }
    public String updateItem(Article article) {
        String articleId = article.getArticleId();
        article.updatedAt = DateTimeUtil.getCurrentDateTime();
        dynamoDBMapper.save(article,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("articleId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(articleId)
                                )));
        return articleId;
    }
}