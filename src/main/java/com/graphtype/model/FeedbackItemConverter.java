package com.graphtype.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class FeedbackItemConverter implements DynamoDBTypeConverter<String, List<FeedbackItem>> {

    @Override
    public String convert(List<FeedbackItem> objects) {
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
    public List<FeedbackItem> unconvert(String objsStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<FeedbackItem> objects = objectMapper.readValue(objsStr, new TypeReference<List<FeedbackItem>>(){});
            return objects;
        } catch (JsonParseException e) {
        } catch (JsonProcessingException e) {
        } catch (IOException e) {
        }
        return null;
    }
}
