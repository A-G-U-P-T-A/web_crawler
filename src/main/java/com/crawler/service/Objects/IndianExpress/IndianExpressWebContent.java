package com.crawler.service.Objects.IndianExpress;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data @Document(collection = "indianexpresswebcontent") public class IndianExpressWebContent {
    @Id private String id;
    private String url;
    private String path = "";
    private List<Object> content;
    private int depth;

    public IndianExpressWebContent() {}
    public IndianExpressWebContent(String url, String path, List<Object> content, int depth) {
        this.url = url;
        this.path = path;
        this.content = content;
        this.depth = depth;
    }
}
