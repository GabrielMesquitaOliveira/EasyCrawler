package org.example.strategies;

public class TextSearchStrategy implements SearchStrategy {
    private final String text;

    public TextSearchStrategy(String text) {
        this.text = text;
    }

    @Override
    public void search(String html, String url) {
        if (html.contains(text)) {
            System.out.println("Texto encontrado em: " + url);
            System.out.println("→ Trecho: " + text);
        }
        else {
            System.out.println("Texto não encontrado em: " + url);
        }
    }
}

