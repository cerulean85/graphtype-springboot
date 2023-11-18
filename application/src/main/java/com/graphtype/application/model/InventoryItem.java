package com.graphtype.application.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class InventoryItem {
    @DynamoDBAttribute(attributeName = "no")
    public int no = 0;

    @DynamoDBAttribute(attributeName = "contents")
    public String contents = "";

    @DynamoDBAttribute(attributeName = "text")
    public String type = "text";
}