package com.graphtype.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class InventoryItemConverter implements DynamoDBTypeConverter<String, List<InventoryItem>> {

    @Override
    public String convert(List<InventoryItem> objects) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String objsStr = mapper.writeValueAsString(objects);
            return objsStr;
        } catch (JsonProcessingException e) {
            //do something
        }
        return null;
    }

    @Override
    public List<InventoryItem> unconvert(String objsStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<InventoryItem> objects = objectMapper.readValue(objsStr, new TypeReference<List<InventoryItem>>(){});
            return objects;
        } catch (JsonParseException e) {
            //do something
        } catch (JsonMappingException e) {
            //do something
        } catch (IOException e) {
            //do something
        }
        return null;
    }
}
