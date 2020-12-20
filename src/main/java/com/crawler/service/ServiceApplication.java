package com.crawler.service;

import com.crawler.service.Services.CrawlerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication public class ServiceApplication implements CommandLineRunner {

	@Autowired ServiceConfiguration serviceConfiguration;
	@Autowired
	CrawlerServiceImpl crawlerServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Override public void run(String... args) throws Exception {
		if(args[0] == null && args[1] == null)
			return;
		String jobName = args[0];
		System.out.println("Starting crawling for: " + jobName);
		String initConfig = args[1];
		serviceConfiguration.createNewCollections(initConfig, jobName);
		String baseUrl = serviceConfiguration.getBaseUrl(jobName);
		List<String> seeds = serviceConfiguration.getSeeds(jobName);
		if(baseUrl!=null&&seeds!=null) {
			crawlerServiceImpl.startCrawler(jobName, baseUrl, seeds);
		}
		else {
			System.out.println("PARAMETER MISMATCH");
			System.exit(-1);
		}
	}


}
