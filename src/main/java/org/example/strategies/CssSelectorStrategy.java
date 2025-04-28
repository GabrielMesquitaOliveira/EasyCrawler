package org.example.strategies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CssSelectorStrategy implements SearchStrategy {

    private final String selector;

    public CssSelectorStrategy(String selector) {
        this.selector = selector;
    }

    @Override
    public void search(String html, String url) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(selector);

        if (!elements.isEmpty()) {
            System.out.println("Seletor '" + selector + "' encontrado em: " + url);
            elements.forEach(el -> System.out.println("→ " + el.text()));
        }
    }
}
