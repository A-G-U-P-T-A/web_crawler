package com.crawler.service.Objects.MoneyControl;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Document(collection = "moneycontrolwebimages") public class MoneyControlWebImages {
    @Id private String id;
    private String url;
    private String path;
    private Binary image;
    private int depth;

    public MoneyControlWebImages(String url, String path, Binary image, int depth) {
        this.url = url;
        this.path = path;
        this.image = image;
        this.depth = depth;
    }
}
