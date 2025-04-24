package org.example.strategies;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * TextSearchStrategy is an implementation of the SearchStrategy interface that searches for
 * a specified text fragment in the HTML content of a page.
 *
 * This strategy is designed to scan the HTML content and determine whether the specified
 * text is present. If the text is found, the URL of the page and the matching text fragment
 * are printed to the console. If the text is not found, an appropriate message is printed.
 *
 * It is particularly useful when searching for exact text matches in web pages during
 * web crawling processes.
 */
@AllArgsConstructor
public class TextSearchStrategy implements SearchStrategy {
    private final String text;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements allElements = doc.getAllElements();
        List<Element> matchedElements = new ArrayList<>();

        for (Element el : allElements) {
            if (el.ownText().contains(text)) {
                matchedElements.add(el);
            }
        }

        return matchedElements;

    }

}

