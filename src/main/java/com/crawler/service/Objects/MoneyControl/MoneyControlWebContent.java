package com.crawler.service.Objects.MoneyControl;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data @Document(collection = "moneycontrolwebcontent") public class MoneyControlWebContent {
    @Id private String id;
    private String url;
    private String path = "";
    private List<Object> content;
    private int depth;

    public MoneyControlWebContent() {}
    public MoneyControlWebContent(String url, String path, List<Object> content, int depth) {
        this.url = url;
        this.path = path;
        this.content = content;
        this.depth = depth;
    }
}
