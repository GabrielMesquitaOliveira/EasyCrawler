package org.example;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public record EasyCrawlerConfig(String domain, boolean restrictToDomain,
                                Function<String, List<Element>> extractionFunction,
                                BiConsumer<String, List<Element>> persistenceFunction) {
}
