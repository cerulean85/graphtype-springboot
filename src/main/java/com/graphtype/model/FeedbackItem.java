package com.graphtype.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.Data;

import java.util.List;

@Data
@DynamoDBDocument
public class FeedbackItem {
    @DynamoDBAttribute(attributeName = "bot")
    public String bot = "";

    @DynamoDBAttribute(attributeName = "updatedAt")
    public String updatedAt = "";

    private List<InventoryItem> inventory;

    @DynamoDBTypeConverted(converter = InventoryItemConverter.class)
    public List<InventoryItem> getInventory() { return this.inventory; }
    public void setInventory(List<InventoryItem> objects) { this.inventory = objects; }
}
