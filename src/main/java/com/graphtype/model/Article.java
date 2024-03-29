package com.graphtype.model;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "article")
public class Article {

    @DynamoDBRangeKey(attributeName="articleId") // sort key
    @DynamoDBAutoGeneratedKey
    @DynamoDBAttribute
    private String articleId;

    @DynamoDBAttribute
    private String articleType;

    @DynamoDBHashKey(attributeName = "author")
    private String author;
    @DynamoDBAttribute public String createdAt;
    @DynamoDBAttribute public String updatedAt;
    @DynamoDBAttribute private boolean hidden;
    @DynamoDBAttribute private String title;
    @DynamoDBAttribute private String thumbnail;
    @DynamoDBAttribute private String state;
    private List<InventoryItem> inventory;

    @DynamoDBTypeConverted(converter = InventoryItemConverter.class)
    public List<InventoryItem> getInventory() { return this.inventory; }
    public void setInventory(List<InventoryItem> objects) { this.inventory = objects; }

    private List<FeedbackItem> feedback;
    @DynamoDBTypeConverted(converter = FeedbackItemConverter.class)
    public List<FeedbackItem> getFeedback() { return this.feedback; }
    public void setFeedback(List<FeedbackItem> objects) { this.feedback = objects; }

    private String searchText;
}
