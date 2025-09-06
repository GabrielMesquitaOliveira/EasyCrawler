package io.github;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import io.github.strategies.ClassSearchStrategy;
import io.github.strategies.PriceRangeSearchStrategy;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Example of how to use EasyCrawler with Crawler4j.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Configure Crawler4j
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("/tmp/crawler4j/");
        crawlConfig.setPolitenessDelay(0);
        crawlConfig.setMaxDepthOfCrawling(0);
        crawlConfig.setMaxPagesToFetch(1000);
        crawlConfig.setIncludeBinaryContentInCrawling(false);
        crawlConfig.setResumableCrawling(false);

        // Timeouts and connection settings
        crawlConfig.setConnectionTimeout(3000); // 3s 
        crawlConfig.setSocketTimeout(3000); // 3s
        crawlConfig.setMaxConnectionsPerHost(16); // max 16 connections per host
        crawlConfig.setMaxTotalConnections(200); // max 200 total connections
        crawlConfig.setMaxOutgoingLinksToFollow(0); // do not follow outgoing links

        // 2. Setup Crawler4j controller
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);

        // 3. Define the domains or addresses to crawl and add as seeds
        String domain = "https://www.wimoveis.com.br/venda/imoveis/df/brasilia";
        // first 100 pages of real estate ads in Distrito Federal on OLX Brazil
        for (int i = 1; i <= 1000; i++) {
            String url = i == 1
                    ? "https://www.wimoveis.com.br/venda/imoveis/df/brasilia"
                    : "https://www.wimoveis.com.br/venda/imoveis/df/brasilia?page=" + i;
            controller.addSeed(url);
        }

        // 4. Create EasyCrawlerConfig with a CSS selector strategy and a persist
        // function
        EasyCrawlerConfig easyConfig = new EasyCrawlerConfig(
                domain,
                true,
                List.of(Pattern.compile("venda/imoveis/df/brasilia\\?page=\\d+")),
                List.of(), // deny none
                new PriceRangeSearchStrategy(
                        new ClassSearchStrategy("postingPrices-module__price"),
                        "BRL",
                        150000,
                        250000),
                (elements, url) -> {
                    // log
                    System.out.println("URL: " + url + " | Found: " + elements.size());
                    System.out.println("Thread: " + Thread.currentThread().getName() + " Page: " + url);
                    return null;
                });

        // 5. Start crawling using EasyCrawlerFactory
        int numberOfCrawlers = 16;
        controller.start(new EasyCrawlerFactory(easyConfig), numberOfCrawlers);
    }
}