package org.example;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.example.strategies.SearchStrategy;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class EasyCrawler extends WebCrawler {

    // Ignorar arquivos que não sejam HTML
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|json|xml|png|jpg|jpeg|gif|svg|pdf|ttf))$");

    private final SearchStrategy strategy;
    private final String domain;
    private final boolean restrictDomain;
    private final BiFunction<List<Element>, String, Void> persistFunc;

    public EasyCrawler(EasyCrawlerConfig config) {
        this.strategy = config.strategy();
        this.domain = config.domain();
        this.restrictDomain = config.restrictDomain();
        this.persistFunc = config.persistFunc();
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && (!restrictDomain || href.startsWith(domain));
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData htmlData) {
            String html = htmlData.getHtml();
            String url = page.getWebURL().getURL();

            // Exemplo de uso da função persistFunc
            List<Element> elements = strategy.search(html);
            persistFunc.apply(elements, url);
        }
    }
}
