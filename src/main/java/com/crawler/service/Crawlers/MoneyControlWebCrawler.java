package com.crawler.service.Crawlers;

import com.crawler.service.Objects.MoneyControl.MoneyControlWebContent;
import com.crawler.service.Objects.MoneyControl.MoneyControlWebContentRepositories;
import com.crawler.service.Objects.MoneyControl.MoneyControlWebUrl;
import com.crawler.service.Objects.MoneyControl.MoneyControlWebUrlRepositories;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.regex.Pattern;

public class MoneyControlWebCrawler extends WebCrawler {
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private final String baseUrl;

    @Autowired MoneyControlWebUrlRepositories moneyControlWebUrlRepositories;
    @Autowired MoneyControlWebContentRepositories moneyControlWebContentRepositories;

    public MoneyControlWebCrawler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        return !EXCLUSIONS.matcher(urlString).matches() && urlString.startsWith(baseUrl);
    }
    @Override public void visit(Page page) {
        moneyControlWebUrlRepositories.insert(new MoneyControlWebUrl(page.getWebURL().getURL() , page.getWebURL().getPath(), true, page.getWebURL().getDepth()));
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> webURLS = htmlParseData.getOutgoingUrls();
            for (WebURL webURL:webURLS) {
                if(!EXCLUSIONS.matcher(webURL.getURL()).matches())
                    moneyControlWebUrlRepositories.insert(new MoneyControlWebUrl(webURL.getURL() , webURL.getPath(), false, webURL.getDepth()));
            }
            List<Object>content = Arrays.asList(title, text, html, webURLS);
            moneyControlWebContentRepositories.insert(new MoneyControlWebContent(page.getWebURL().getURL(), page.getWebURL().getPath(), content, page.getWebURL().getDepth()));
        }
    }

}
