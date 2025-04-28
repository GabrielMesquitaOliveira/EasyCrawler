package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EasyCrawlerFactory implements CrawlController.WebCrawlerFactory<EasyCrawler> {
    private final EasyCrawlerConfig config;

    @Override
    public EasyCrawler newInstance() {
        return new EasyCrawler(config);
    }
}
