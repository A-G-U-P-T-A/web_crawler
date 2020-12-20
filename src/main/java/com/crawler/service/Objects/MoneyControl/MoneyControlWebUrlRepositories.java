package com.crawler.service.Objects.MoneyControl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MoneyControlWebUrlRepositories extends MongoRepository<MoneyControlWebUrl, String> {
    Page<MoneyControlWebUrl> findAll(Pageable pageable);
    @Query("{url:'?0'}") MoneyControlWebUrl findOne(String url);
}
