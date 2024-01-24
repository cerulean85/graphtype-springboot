package com.graphtype.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class RssVO {
    private int no;
    private String idx;
    private String title;
    private String link;
    private String name;
    private String rssUrl;
    private String updatedAt;
    private int feedCount;
}
