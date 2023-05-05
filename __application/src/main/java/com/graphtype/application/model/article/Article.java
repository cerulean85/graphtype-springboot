package com.graphtype.application.model.article;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@DynamoDBTable(tableName = "article")
@Data
public class Article {

    @DynamoDBHashKey(attributeName = "articleId")
    private String articleId;

    @DynamoDBRangeKey(attributeName="articleType") // sort key
    private String articleType;

    @DynamoDBAttribute private String author;
    @DynamoDBAttribute private String createdAt;
    @DynamoDBAttribute private String updatedAt;
    @DynamoDBAttribute private boolean hidden;
    @DynamoDBAttribute private String title;
    @DynamoDBAttribute private List<String> contents;


}
