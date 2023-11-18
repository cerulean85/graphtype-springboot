package com.graphtype.application.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "article")
public class Article {

    @DynamoDBHashKey(attributeName = "articleId")
    @DynamoDBAutoGeneratedKey
    private String articleId;

    @DynamoDBRangeKey(attributeName="articleType") // sort key
    @DynamoDBAttribute
    private String articleType;

    @DynamoDBAttribute private String author;
    @DynamoDBAttribute public String createdAt;
    @DynamoDBAttribute public String updatedAt;
    @DynamoDBAttribute private boolean hidden;
    @DynamoDBAttribute private String title;
    @DynamoDBAttribute private String thumbnail;

    private List<InventoryItem> inventory;


    @DynamoDBTypeConverted(converter = InventoryItemConverter.class)
    public List<InventoryItem> getInventory() { return this.inventory; }
    public void setInventory(List<InventoryItem> objects) { this.inventory = objects; }

}
