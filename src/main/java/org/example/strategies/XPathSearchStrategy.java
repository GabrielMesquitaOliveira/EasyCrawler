package org.example.strategies;

import lombok.AllArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * 
 * Search strategy that finds HTML elements using an XPath expression.
 * 
 * <p>
 * Example usage:
 * <pre>
 *   new XPathSearchStrategy("//a[@href]").search(html);
 * </pre>
 * </p>
 * 
 * Requires the jsoup-xpath library as a dependency.
 * 
 * @author Gabriel
 * @version 1.0
 * 
 */
@AllArgsConstructor
public class XPathSearchStrategy implements SearchStrategy {
    private final String xpath;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.selectXpath(xpath);
        return elements;
    }
}