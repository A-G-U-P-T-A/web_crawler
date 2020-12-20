package com.crawler.service.Objects.MoneyControl;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Document(collection = "moneycontrolweburl") public class MoneyControlWebUrl {
    @Id private String id;
    private String url;
    private String path = "";
    private boolean crawled;
    private int depth;

    public MoneyControlWebUrl() {}
    public MoneyControlWebUrl(String url, String path, boolean crawled, int depth) {
        this.url = url;
        this.path = path;
        this.crawled = crawled;
        this.depth = depth;
    }


}

