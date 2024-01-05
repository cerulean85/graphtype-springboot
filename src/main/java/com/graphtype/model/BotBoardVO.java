package com.graphtype.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class BotBoardVO {
    private int idx;
    private String articleId;
    private String state;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String author;
    private String type;
}
