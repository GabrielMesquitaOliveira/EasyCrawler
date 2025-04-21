package org.example;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.example.strategies.SearchStrategy;

import java.util.regex.Pattern;

public class EasyCrawler extends WebCrawler {

    // Ignorar arquivos que não sejam HTML
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|json|xml|png|jpg|jpeg|gif|svg|pdf|ttf))$");

    private final SearchStrategy strategy;

    public EasyCrawler(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && href.startsWith("https://ameisegroup.com.br/");
    }


    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData htmlData) {
            String html = htmlData.getHtml();
            String url = page.getWebURL().getURL();
            strategy.search(html, url);
        }
    }

}
