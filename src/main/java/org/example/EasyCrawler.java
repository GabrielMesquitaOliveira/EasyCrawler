package org.example;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.nodes.Element;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class EasyCrawler extends WebCrawler {

    // Ignore non HTML files
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|json|xml|png|jpg|jpeg|gif|svg|pdf|ttf))$");

    // Configuration
    private final EasyCrawlerConfig config;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        // ignore non HTML content
        if (FILTERS.matcher(href).matches())
            return false;

        // Domain restriction (compare only host, ignore URI)
        if (config.restrictDomain()) {
            try {
                URI targetUri = new URI(href);
                URI configUri = new URI(config.domain());
                String targetHost = targetUri.getHost();
                String configHost = configUri.getHost();
                if (targetHost == null || configHost == null || !targetHost.equals(configHost)) {
                    return false;
                }
            } catch (URISyntaxException e) {
                return false;
            }
        }

        // URI filters (allow)
        if (config.uriAllowPatterns() != null && !config.uriAllowPatterns().isEmpty()) {
            return config.uriAllowPatterns().stream().anyMatch(p -> p.matcher(href).find());
        }

        // URI filters (deny)
        if (config.uriDenyPatterns() != null
                && config.uriDenyPatterns().stream().anyMatch(p -> p.matcher(href).find())) {
            return false;
        }

        return true;
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData htmlData) {
            String html = htmlData.getHtml();
            String url = page.getWebURL().getURL();
            List<Element> elements = config.strategy().search(html);
            config.persistFunc().apply(elements, url);
        }
    }
}
