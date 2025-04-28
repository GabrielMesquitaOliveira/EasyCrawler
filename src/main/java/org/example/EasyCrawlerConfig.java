package org.example;

import org.example.strategies.SearchStrategy;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.function.BiFunction;

public record EasyCrawlerConfig(
        String domain,
        boolean restrictDomain,
        SearchStrategy strategy,
        BiFunction<List<Element>, String, Void> persistFunc
){}