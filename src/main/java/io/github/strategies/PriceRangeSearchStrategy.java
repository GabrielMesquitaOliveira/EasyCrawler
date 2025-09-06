package io.github.strategies;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Element;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Search strategy that filters elements by price range using robust custom
 * parsing.
 *
 * Example usage:
 * new PriceRangeSearchStrategy(new CssSelectorSearchStrategy(".price"), "BRL",
 * 1000.0, 2000.0).search(html);
 * new PriceRangeSearchStrategy(new CssSelectorSearchStrategy(".price"), "USD",
 * 100.0, 500.0).search(html);
 *
 * @Author Gabriel Mesquita
 * @version 1.0
 */
@AllArgsConstructor
public class PriceRangeSearchStrategy implements SearchStrategy {
    private final SearchStrategy baseStrategy;
    private final String currencyCode;
    private final double minPrice;
    private final double maxPrice;

    private static final Pattern PRICE_PATTERN = Pattern.compile(
            "(?:R\\$|USD|\\$|€|£)?\\s*([\\d]{1,3}(?:[.,\\s][\\d]{3})*(?:[.,][\\d]{1,2})?|[\\d]+(?:[.,][\\d]{1,2})?)",
            Pattern.CASE_INSENSITIVE);

    @Override
    public List<Element> search(String html) {
        List<Element> priceElements = baseStrategy.search(html);

        return priceElements.stream()
                .filter(this::isInPriceRange)
                .collect(Collectors.toList());
    }

    private boolean isInPriceRange(Element element) {
        String text = element.text();
        Matcher matcher = PRICE_PATTERN.matcher(text);

        while (matcher.find()) {
            String priceText = matcher.group(1);
            Double price = parsePrice(priceText);

            if (price != null && price >= minPrice && price <= maxPrice) {
                return true;
            }
        }
        return false;
    }

    private Double parsePrice(String priceText) {
        if (priceText == null || priceText.trim().isEmpty()) {
            return null;
        }

        try {

            String numbers = priceText.replaceAll("[^\\d.,]", "").trim();
            if (numbers.isEmpty())
                return null;

            return parseNumericValue(numbers);

        } catch (Exception e) {
            return null;
        }
    }

    private double parseNumericValue(String numbers) {
        try {

            if (numbers.matches("\\d{1,3}(\\.\\d{3})+,\\d{1,2}")) {
                return Double.parseDouble(numbers.replace(".", "").replace(",", "."));
            }

            else if (numbers.matches("\\d{1,3}(\\.\\d{3})+")) {
                return Double.parseDouble(numbers.replace(".", ""));
            }

            else if (numbers.matches("\\d{1,3}(,\\d{3})+\\.\\d{1,2}")) {
                return Double.parseDouble(numbers.replace(",", ""));
            }

            else if (numbers.matches("\\d{1,3}(,\\d{3})+")) {
                return Double.parseDouble(numbers.replace(",", ""));
            }

            else if (numbers.matches("\\d+,\\d{1,2}")) {
                return Double.parseDouble(numbers.replace(",", "."));
            }

            else if (numbers.matches("\\d+\\.\\d{1,2}")) {
                return Double.parseDouble(numbers);
            }

            else if (numbers.matches("\\d{1,3}[.,]\\d{3}")) {
                return Double.parseDouble(numbers.replaceAll("[.,]", ""));
            }

            else if (numbers.matches("\\d+[.,]\\d{1,2}")) {
                if (isBrazilianCurrency()) {

                    return Double.parseDouble(numbers.replace(",", "."));
                } else {

                    return Double.parseDouble(numbers.replace(",", ""));
                }
            }

            else if (numbers.matches("\\d+")) {
                return Double.parseDouble(numbers);
            }

            else {

                try {
                    return Double.parseDouble(numbers.replace(",", ""));
                } catch (NumberFormatException e1) {

                    return Double.parseDouble(numbers.replace(",", "."));
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse price: " + numbers, e);
        }
    }

    private boolean isBrazilianCurrency() {
        return "BRL".equalsIgnoreCase(currencyCode);
    }
}