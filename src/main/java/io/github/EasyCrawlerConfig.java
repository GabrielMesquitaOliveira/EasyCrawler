package io.github;

import org.jsoup.nodes.Element;

import io.github.strategies.SearchStrategy;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * Configuration record for EasyCrawler.
 * <p>
 * This record encapsulates all configuration options for the EasyCrawler, including:
 * <ul>
 *     <li>Domain restriction</li>
 *     <li>URI allow/deny patterns</li>
 *     <li>Search strategy for extracting elements</li>
 *     <li>Persistence function for handling extracted data</li>
 * </ul>
 *
 * @param domain           The base domain to restrict crawling (e.g., "https://www.olx.com.br").
 * @param restrictDomain   If true, restricts crawling to the specified domain.
 * @param uriAllowPatterns List of regex patterns for URIs to allow (if empty or null, all are allowed).
 * @param uriDenyPatterns  List of regex patterns for URIs to deny (checked before allow).
 * @param strategy         The {@link SearchStrategy} implementation to extract elements from HTML.
 * @param persistFunc      A function to persist or process the extracted elements and their source URL.
 */
public record EasyCrawlerConfig(
        String domain,
        boolean restrictDomain,
        List<Pattern> uriAllowPatterns,
        List<Pattern> uriDenyPatterns,
        SearchStrategy strategy,
        BiFunction<List<Element>, String, Void> persistFunc
){}