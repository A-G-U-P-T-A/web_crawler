package com.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import java.util.Arrays;
import java.util.List;

@Configuration public class ServiceConfiguration {
    @Autowired private MongoOperations mongoOperations;
    @Autowired private MongoTemplate mongoTemplate;

    public void createNewCollections(String createNewTables, String id) {
        if(createNewTables.equals("true")) {
            if(id.equals("moneycontrol"))
                initMoneyControl();
            if(id.equals("indianexpress"))
                initIndianExpress();
        }
    }
    public String getBaseUrl(String website) {
        switch (website) {
            case "moneycontrol":
                return "https://www.moneycontrol.com/";
            case "indianexpress":
                return "https://indianexpress.com/";
            default: return null;
        }
    }
    public List<String> getSeeds(String website) {
        switch (website) {
            case "moneycontrol":
                return Arrays.asList(
                    "https://www.moneycontrol.com/stocksmarketsindia/",
                    "https://www.moneycontrol.com/mutualfundindia/",
                    "https://www.moneycontrol.com/news/");
            case "indianexpress":
                return Arrays.asList(
                     "https://indianexpress.com/section/sports/",
                     "https://indianexpress.com/section/world/",
                     "https://indianexpress.com/section/cities/");
            default: return null;
        }
    }
    private void initIndianExpress() {
        //drop all
        mongoOperations.dropCollection("indianexpresswebcontent");
        mongoOperations.dropCollection("indianexpressweburl");
        mongoOperations.dropCollection("indianexpresswebimages");

        //create collections
        mongoOperations.createCollection("indianexpresswebcontent");
        mongoOperations.createCollection("indianexpressweburl");
        mongoOperations.createCollection("indianexpresswebimages");

        //create indexes
        mongoTemplate.indexOps("indianexpressweburl").dropAllIndexes();
        mongoTemplate.indexOps("indianexpressweburl").ensureIndex(new Index().on("path", Sort.Direction.ASC));
        mongoTemplate.indexOps("indianexpressweburl").ensureIndex(new Index().on("crawled", Sort.Direction.ASC));
        mongoTemplate.indexOps("indianexpressweburl").ensureIndex(new Index().on("depth", Sort.Direction.ASC));
    }
    private void initMoneyControl() {
        //drop all
        mongoOperations.dropCollection("moneycontrolwebcontent");
        mongoOperations.dropCollection("moneycontrolweburl");
        mongoOperations.dropCollection("moneycontrolwebimages");

        //create collections
        mongoOperations.createCollection("moneycontrolwebcontent");
        mongoOperations.createCollection("moneycontrolweburl");
        mongoOperations.createCollection("moneycontrolwebimages");

        //create indexes
        mongoTemplate.indexOps("moneycontrolweburl").dropAllIndexes();
        mongoTemplate.indexOps("moneycontrolweburl").ensureIndex(new Index().on("path", Sort.Direction.ASC));
        mongoTemplate.indexOps("moneycontrolweburl").ensureIndex(new Index().on("crawled", Sort.Direction.ASC));
        mongoTemplate.indexOps("moneycontrolweburl").ensureIndex(new Index().on("depth", Sort.Direction.ASC));
    }
}
