package org.example.strategies;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;

/**
 * 
 * Search strategy that finds HTML elements using a CSS selector.
 * 
 * <p>
 * Example usage:
 * <pre>
 *   new CssSelectorSearchStrategy("div.article > p");
 * </pre>
 * </p>
 * 
 * @Author Gabriel Mesquita
 * @version 1.0
 *
 */
@AllArgsConstructor
public class CssSelectorSearchStrategy implements SearchStrategy {

    private final String cssSelector;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(cssSelector);        
        return elements;
    }
}
