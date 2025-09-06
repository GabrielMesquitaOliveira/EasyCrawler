package io.github.strategies;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;

/**
 * Search strategy that finds HTML elements by their tag name.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * new TagSearchStrategy("img").search(html);
 * </pre>
 * </p>
 *
 * @Author Gabriel Mesquita
 * @version 1.0
 */
@AllArgsConstructor
public class TagSearchStrategy implements SearchStrategy {

    private final String tagName;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag(tagName);
        return elements;
    }
}