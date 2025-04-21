package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.example.strategies.SearchStrategy;

public class EasyCrawlerFactory implements CrawlController.WebCrawlerFactory<EasyCrawler> {
    private final SearchStrategy strategy;
    private final String domain;

    public EasyCrawlerFactory(SearchStrategy strategy, String domain) {
        this.strategy = strategy;
        this.domain = domain;
    }

    @Override
    public EasyCrawler newInstance() {
        return new EasyCrawler(strategy,domain);
    }
}
