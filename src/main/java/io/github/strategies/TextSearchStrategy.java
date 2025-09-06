package io.github.strategies;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * TextSearchStrategy is an implementation of the SearchStrategy interface that searches for
 * a specified text fragment in the HTML content of a page.
 *
 * <p>
 * This strategy scans the HTML content and returns all elements containing the specified text.
 * It is useful for finding exact text matches in web pages during crawling processes.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *   new TextSearchStrategy("example text").search(html);
 * </pre>
 * </p>
 *
 * @Author Gabriel Mesquita
 * @version 1.0
 */
@AllArgsConstructor
public class TextSearchStrategy implements SearchStrategy {
    private final String text;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsContainingOwnText(text);
        return elements;
    }
}
