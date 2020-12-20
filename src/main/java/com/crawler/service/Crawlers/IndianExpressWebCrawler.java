package com.crawler.service.Crawlers;

import com.crawler.service.Objects.IndianExpress.IndianExpressWebContent;
import com.crawler.service.Objects.IndianExpress.IndianExpressWebContentRepositories;
import com.crawler.service.Objects.IndianExpress.IndianExpressWebUrl;
import com.crawler.service.Objects.IndianExpress.IndianExpressWebUrlRepositories;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class IndianExpressWebCrawler extends WebCrawler {
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private final String baseUrl;

    @Autowired IndianExpressWebUrlRepositories indianExpressWebUrlRepositories;
    @Autowired IndianExpressWebContentRepositories indianExpressWebContentRepositories;

    public IndianExpressWebCrawler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        return !EXCLUSIONS.matcher(urlString).matches() && urlString.startsWith(baseUrl);
    }
    @Override public void visit(Page page) {
        //IndianExpressWebUrl indianExpressWebUrl = indianExpressWebUrlRepositories.findOne(page.getWebURL().getURL());
        //if(indianExpressWebUrl==null)
            indianExpressWebUrlRepositories.insert(new IndianExpressWebUrl(page.getWebURL().getURL() , page.getWebURL().getPath(), true, page.getWebURL().getDepth()));
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> webURLS = htmlParseData.getOutgoingUrls();
            //for (WebURL webURL:webURLS) {
                //IndianExpressWebUrl indianExpressSubWebUrl = indianExpressWebUrlRepositories.findOne(webURL.getURL());
                //if(indianExpressSubWebUrl==null)
                    //if(!EXCLUSIONS.matcher(webURL.getURL()).matches())
                        //indianExpressWebUrlRepositories.insert(new IndianExpressWebUrl(webURL.getURL() , webURL.getPath(), false, webURL.getDepth()));
            //}
            List<Object> content = Arrays.asList(title, text, html, webURLS);
            indianExpressWebContentRepositories.insert(new IndianExpressWebContent(page.getWebURL().getURL(), page.getWebURL().getPath(), content, page.getWebURL().getDepth()));
        }
    }
}

