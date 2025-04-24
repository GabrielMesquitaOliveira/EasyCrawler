package org.example.strategies;

import org.jsoup.nodes.Element;

import java.util.List;

public interface SearchStrategy {
    
    /**
     * Searches the provided HTML content for specific elements or text based on the implementation
     * of the search strategy.
     *
     * @param html the HTML content to be analyzed
     * @return a SearchResults object containing the matching HTML elements, or null if no matches are found
     */
    List<Element> search(String html);

}
