package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import lombok.AllArgsConstructor;
import org.example.strategies.SearchStrategy;

@AllArgsConstructor
public class EasyCrawlerFactory implements CrawlController.WebCrawlerFactory<EasyCrawler> {

    /**
     * Represents the strategy to be applied when processing HTML content during the crawling process.
     *
     * The strategy defines how HTML pages should be searched or analyzed for specific content, based on
     * the implementation of the {@link SearchStrategy} interface. It ensures flexibility and allows
     * for customized processing logic, such as searching for tags, specific text, or other content
     * criteria within the crawled HTML pages.
     *
     * This variable is final and initialized during the creation of the {@link EasyCrawlerFactory},
     * ensuring that every instance of the crawler adheres to the defined search strategy.
     */
    private final EasyCrawlerConfig config;

    /**
     * The domain on which the crawler will focus its operations.
     * This variable defines the base URL that serves as a seed for web crawling and
     * acts as a filter to restrict crawling to URLs starting with this domain.
     */
    private final String domain;

    /**
     * Creates and returns a new instance of the EasyCrawler class, configured with the
     * provided search strategy and domain.
     *
     * @return a new EasyCrawler instance initialized with the specified search strategy and domain
     */
    @Override
    public EasyCrawler newInstance() {
        return new EasyCrawler(config);
    }
}
