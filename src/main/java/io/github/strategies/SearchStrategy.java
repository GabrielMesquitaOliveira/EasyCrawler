package io.github.strategies;

import org.jsoup.nodes.Element;
import java.util.List;

/**
 * Strategy interface for searching and extracting data from HTML content.
 */
public interface SearchStrategy {
    List<Element> search(String html);
}
