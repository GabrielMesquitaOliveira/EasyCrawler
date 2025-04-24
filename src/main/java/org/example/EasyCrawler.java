package org.example;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.regex.Pattern;

public class EasyCrawler extends WebCrawler {

    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|xml|png|jpg|jpeg|gif|svg|pdf|ttf))$");

    private final EasyCrawlerConfig config;

    public EasyCrawler(EasyCrawlerConfig config) {
        this.config = config;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean validExtension = !FILTERS.matcher(href).matches();
        boolean inDomain = href.startsWith(config.domain());
        return validExtension && (!config.restrictToDomain() || inDomain);
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData htmlData) {
            String html = htmlData.getHtml();
            String url = page.getWebURL().getURL();

            List<Element> elements = config.extractionFunction().apply(html);
            if (elements != null && !elements.isEmpty()) {
                config.persistenceFunction().accept(url, elements);
            }
        }
    }
}
