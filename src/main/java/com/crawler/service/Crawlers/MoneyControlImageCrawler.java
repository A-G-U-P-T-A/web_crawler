package com.crawler.service.Crawlers;

import com.crawler.service.Objects.MoneyControl.MoneyControlWebImages;
import com.crawler.service.Objects.MoneyControl.MoneyControlWebImagesRepositories;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class MoneyControlImageCrawler extends WebCrawler {
    private static final Pattern filters = Pattern.compile(
            ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");
    private final String baseUrl;

    @Autowired MoneyControlWebImagesRepositories moneyControlWebImagesRepositories;

    public MoneyControlImageCrawler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (filters.matcher(href).matches()) {
            return false;
        }
        if (imgPatterns.matcher(href).matches()) {
            return true;
        }
        if (href.contains(baseUrl)) {
            return true;
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        if (!imgPatterns.matcher(url).matches() ||
                !(page.getParseData() instanceof BinaryParseData) ) {
            return;
        }
        Binary image = saveImage(url);
        if(image!=null) {
            moneyControlWebImagesRepositories.insert(new MoneyControlWebImages(page.getWebURL().getURL(), page.getWebURL().getPath(), image, page.getWebURL().getDepth()));
        }
    }

    private Binary saveImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return new Binary(BsonBinarySubType.BINARY, url.getFile().getBytes());
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
