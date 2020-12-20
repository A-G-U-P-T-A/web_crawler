package com.crawler.service.Objects.MoneyControl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoneyControlWebContentRepositories extends MongoRepository<MoneyControlWebContent, String> {
    Page<MoneyControlWebContent> findAll(Pageable pageable);
}


