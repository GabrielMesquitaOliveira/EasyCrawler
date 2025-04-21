package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.example.strategies.SearchStrategy;

public class EasyCrawlerFactory implements CrawlController.WebCrawlerFactory<EasyCrawler> {
    private final SearchStrategy strategy;

    public EasyCrawlerFactory(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public EasyCrawler newInstance() {
        return new EasyCrawler(strategy);
    }
}
