package org.example.strategies;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Search strategy that finds HTML elements whose own text matches a regular
 * expression.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * new RegexTextSearchStrategy("\\d{4}-\\d{2}-\\d{2}").search(html);
 * </pre>
 * </p>
 *
 * @Author Gabriel Mesquita
 * @version 1.0
 */
@AllArgsConstructor
public class RegexTextSearchStrategy implements SearchStrategy {
    private final String regex;

    @Override
    public List<Element> search(String html) {
        Document doc = Jsoup.parse(html);
        Elements allElements = doc.getAllElements();
        Pattern pattern = Pattern.compile(regex);

        return allElements.stream()
                .filter(el -> pattern.matcher(el.ownText()).find())
                .collect(Collectors.toList());
    }
}