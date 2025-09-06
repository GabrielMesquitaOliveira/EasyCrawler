package org.example.strategies;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;

/**
 * Search strategy that finds HTML elements by their class name.
 * Example usage:
 *  new ClassSelectorSearchStrategy("my-class"); 
 *
 * @author Gabriel
 * @version 1.0
 */
@AllArgsConstructor
public class ClassSearchStrategy implements SearchStrategy {
    private final String className;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass(className);
        return elements;
    }
    
}
