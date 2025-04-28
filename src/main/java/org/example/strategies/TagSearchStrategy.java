package org.example.strategies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TagSearchStrategy implements SearchStrategy {
    private final String tag;

    public TagSearchStrategy(String tag) {
        this.tag = tag;
    }

    @Override
    public void search(String html, String url) {
        Document doc = Jsoup.parse(html);
        Elements tags = doc.getElementsByTag(tag);
        if (!tags.isEmpty()) {
            System.out.println("Tag <" + tag + "> encontrada em: " + url);
            tags.forEach(t -> System.out.println("→ " + t.text()));
        }
    }
}

