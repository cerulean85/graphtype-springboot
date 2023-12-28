package com.graphtype.model;

import lombok.Data;

import java.util.List;

@Data
public class ArticleResponse {
    private int totalItemCount;
    private int totalPageCount;
    private int currentPageNo;
    private List<Article> list;
}
