package com.graphtype.repository;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.graphtype.etc.DateTimeUtil;
import com.graphtype.model.Article;
import com.graphtype.model.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticleRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Article createItem(Article article) {
        article.createdAt = DateTimeUtil.getCurrentDateTimeKR();
        article.updatedAt = DateTimeUtil.getCurrentDateTimeKR();
        dynamoDBMapper.save(article);
        return article;
    }
    public Article getItem(String author, String articleId) {
        return dynamoDBMapper.load(Article.class, author, articleId);
    }
    public ArticleResponse searchItems(String searchText, int pageNo) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1", new AttributeValue().withS(searchText));
        eav.put(":v2", new AttributeValue().withS(searchText));

        DynamoDBScanExpression scanExp = new DynamoDBScanExpression()
                .withFilterExpression("contains(title, :v1) AND contains(inventory, :v2)")
                .withExpressionAttributeValues(eav);
        int totalItemCount = dynamoDBMapper.count(Article.class, scanExp);
        int pageUnitCount = 10;
        int totalSegmentCount = totalItemCount / pageUnitCount + (totalItemCount % pageUnitCount == 0 ?  0 : 1);

        ScanResultPage<Article> page = dynamoDBMapper.scanPage(Article.class, scanExp.withTotalSegments(totalSegmentCount).withSegment(pageNo - 1));
        List<Article> items = page.getResults();

        ArticleResponse result = new ArticleResponse();
        result.setTotalItemCount(totalItemCount);
        result.setTotalPageCount(totalSegmentCount);
        result.setCurrentPageNo(pageNo);
        result.setList(page.getResults());
        return result;
    }
    public ArticleResponse getItems(String author, int pageNo) {
//        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
//        eav.put(":v1", new AttributeValue().withS(author));


//        String indexName = "displayYn-createdAt-index";
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":author", new AttributeValue().withS(author));
//        eav.put(":version", new AttributeValue().withS("v1"));
        String keyConditionExpression = "author = :author";
//        String filterConditionExpression = "version = :version";

        // hash key, 다른 필드로 조회하기 때문에 query +filterExpression 사용
        DynamoDBQueryExpression<Article> scanExp =
                new DynamoDBQueryExpression<Article>()
                        .withKeyConditionExpression(keyConditionExpression)
//                        .withFilterExpression(filterConditionExpression)
//                        .withIndexName(indexName)
                        .withExpressionAttributeValues(eav);
//                        .withConsistentRead(false)
//                        .withScanIndexForward(false);
//                        .withLimit(limit)
//                        .withExclusiveStartKey(lastEvaluatedKey);

//        DynamoDBScanExpression scanExp = new DynamoDBScanExpression()
//                .withFilterExpression("author = :v1")
//                .withExpressionAttributeValues(eav);
        int totalItemCount = dynamoDBMapper.count(Article.class, scanExp);
        int pageUnitCount = 10;
        int totalSegmentCount = totalItemCount / pageUnitCount + (totalItemCount % pageUnitCount == 0 ?  0 : 1);

        QueryResultPage<Article> page = dynamoDBMapper.queryPage(Article.class, scanExp);
//        scanExp.with
//        ScanResultPage<Article> page = dynamoDBMapper.scanPage(Article.class, scanExp); //.withTotalSegments(totalSegmentCount).withSegment(pageNo - 1));
//        ScanResultPage<Article> page2 = dynamoDBMapper.scanPage(Article.class, scanExp.withTotalSegments(3).withSegment(1).withLimit(2));
//        ScanResultPage<Article> page3 = dynamoDBMapper.scanPage(Article.class, scanExp.withTotalSegments(3).withSegment(2).withLimit(2));
//        ScanResultPage<Article> page41 = dynamoDBMapper.scanPage(Article.class, scanExp.withTotalSegments(3).withSegment(3));
        List<Article> items = page.getResults();
        Collections.sort(items, Comparator.comparing(Article::getUpdatedAt).reversed());

        ArticleResponse result = new ArticleResponse();
        result.setTotalItemCount(totalItemCount);
        result.setTotalPageCount(totalSegmentCount);
        result.setCurrentPageNo(pageNo);
        result.setList(page.getResults());
//        List<Article> items = dynamoDBMapper.scanPage(Article.class, scanExp);
//        List<Article> items= dynamoDBMapper.scan(Article.class, scanExp);
        return result;

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

    public boolean deleteItem(String articleId, String author) {
        try {
            dynamoDBMapper.delete(dynamoDBMapper.load(Article.class, articleId, author));
        } catch (Exception err) {
            return false;
        }
        return true;
    }
    public String updateItem(Article article) {
        String articleId = article.getArticleId();
        article.updatedAt = DateTimeUtil.getCurrentDateTimeKR();
        dynamoDBMapper.save(article,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("articleId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(articleId)
                                )));
        return articleId;
    }
}