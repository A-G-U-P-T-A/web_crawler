package com.crawler.service.Objects.IndianExpress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndianExpressWebUrlRepositories extends MongoRepository<IndianExpressWebUrl, String> {
    Page<IndianExpressWebUrl> findAll(Pageable pageable);
}
