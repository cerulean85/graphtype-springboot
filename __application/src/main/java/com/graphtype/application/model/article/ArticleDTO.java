package com.graphtype.application.model.article;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {

    private String articleId;
    private String articleType;

    private String author;
    private String createdAt;
    private String updatedAt;
    private boolean hidden;
    private String title;
    private List<String> contents;

}
