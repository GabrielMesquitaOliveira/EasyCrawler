package org.example.strategies;

import org.jsoup.nodes.Element;

import java.util.List;

public interface SearchStrategy {
    List<Element> search(String html);
}
