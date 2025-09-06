package org.example;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import org.example.strategies.ClassSearchStrategy;
import org.example.strategies.PriceRangeSearchStrategy;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

        // Timeouts otimizados para reduzir tempo de espera
        crawlConfig.setConnectionTimeout(3000);     // 3s (era 500ms - muito baixo)
        crawlConfig.setSocketTimeout(5000);         // 5s timeout para leitura
        crawlConfig.setMaxConnectionsPerHost(16);    // Mais conexões simultâneas
        crawlConfig.setMaxTotalConnections(200);    // Pool maior de conexões
        crawlConfig.setMaxOutgoingLinksToFollow(0); // Não seguir links externos

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
                    if (!elements.isEmpty()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
                            writer.write("URL: " + url + "\n");
                            writer.write("Found elements:\n");
                            for (Element element : elements) {
                                writer.write(element.toString() + "\n");
                            }
                            writer.write("\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                });

        // 5. Start crawling using EasyCrawlerFactory
        long startTime = System.currentTimeMillis();
        int numberOfCrawlers = 16;
        controller.start(new EasyCrawlerFactory(easyConfig), numberOfCrawlers);

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + ((endTime - startTime) / 1000.0) + " seconds");
    }
}