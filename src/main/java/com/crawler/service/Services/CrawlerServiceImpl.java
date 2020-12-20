package com.crawler.service.Services;

import com.crawler.service.Crawlers.IndianExpressImageCrawler;
import com.crawler.service.Crawlers.IndianExpressWebCrawler;
import com.crawler.service.Crawlers.MoneyControlImageCrawler;
import com.crawler.service.Crawlers.MoneyControlWebCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service public class CrawlerServiceImpl implements CrawlerService {

    @Autowired ApplicationContext applicationContext;

    @Override public void startCrawler(String jobName, String baseUrl, List<String>seeds) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                startHttpCrawler(jobName, baseUrl, seeds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                startImageCrawler(jobName, baseUrl, seeds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    private void startImageCrawler(String jobName, String baseUrl, List<String> seeds) throws Exception {
        File crawlStorage = new File("src/main/resources/crawler4j_images");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        config.setMaxDepthOfCrawling(10);
        config.setIncludeBinaryContentInCrawling(true);
        int numCrawlers = 12;
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed: seeds) {
            crawlController.addSeed(seed);
        }
        if(jobName.equals("moneycontrol")) {
            MoneyControlImageCrawler moneyControlImageCrawler = new MoneyControlImageCrawler(baseUrl);
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            autowireCapableBeanFactory.autowireBean(moneyControlImageCrawler);
            CrawlController.WebCrawlerFactory<MoneyControlImageCrawler> factory = () -> moneyControlImageCrawler;
            crawlController.start(factory, numCrawlers);
        } else if (jobName.equals("indianexpress")) {
            IndianExpressImageCrawler indianExpressImageCrawler = new IndianExpressImageCrawler(baseUrl);
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            autowireCapableBeanFactory.autowireBean(indianExpressImageCrawler);
            CrawlController.WebCrawlerFactory<IndianExpressImageCrawler> factory = () -> indianExpressImageCrawler;
            crawlController.start(factory, numCrawlers);
        }
    }


    void startHttpCrawler(String jobName, String baseUrl, List<String> seeds) throws Exception {
        File crawlStorage = new File("src/main/resources/crawler4j");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        config.setMaxDepthOfCrawling(10);
        int numCrawlers = 12;
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed: seeds) {
            crawlController.addSeed(seed);
        }
        if(jobName.equals("moneycontrol")) {
            MoneyControlWebCrawler moneyControlWebCrawler = new MoneyControlWebCrawler(baseUrl);
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            autowireCapableBeanFactory.autowireBean(moneyControlWebCrawler);
            CrawlController.WebCrawlerFactory<MoneyControlWebCrawler> factory = () -> moneyControlWebCrawler;
            crawlController.start(factory, numCrawlers);
        } else if(jobName.equals("indianexpress")) {
            IndianExpressWebCrawler indianExpressWebCrawler = new IndianExpressWebCrawler(baseUrl);
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            autowireCapableBeanFactory.autowireBean(indianExpressWebCrawler);
            CrawlController.WebCrawlerFactory<IndianExpressWebCrawler> factory = () -> indianExpressWebCrawler;
            crawlController.start(factory, numCrawlers);
        }
    }
}
