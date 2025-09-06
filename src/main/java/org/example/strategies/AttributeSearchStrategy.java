package org.example.strategies;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * 
 * Search strategy that finds HTML elements by attribute name and (optionally) value.
 * 
 * <p>
 * Example usage:
 * <pre>
 *   new AttributeSearchStrategy("data-id", "123").search(html);
 *   new AttributeSearchStrategy("href", null).search(html); // any element with href
 * </pre>
 * </p>
 * 
 * @author Gabriel
 * @version 1.0
 * 
 */
@AllArgsConstructor
public class AttributeSearchStrategy implements SearchStrategy {
    private final String attributeName;
    private final String attributeValue; // can be null

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByAttribute(attributeName);
        if (attributeValue != null) {
            elements = doc.getElementsByAttributeValue(attributeName, attributeValue);
        }
        return elements;
    }
}