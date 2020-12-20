package com.crawler.service.Services;

import java.util.List;

public interface CrawlerService {
    void startCrawler(String jobName, String baseUrl, List<String> seeds);
}
