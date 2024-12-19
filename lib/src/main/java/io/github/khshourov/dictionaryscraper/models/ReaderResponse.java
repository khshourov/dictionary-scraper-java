package io.github.khshourov.dictionaryscraper.models;

/**
 * Represents the response from a reader after fetching a resource or content.
 *
 * @param link The URL or source link where the content was retrieved.
 * @param data The actual content or data retrieved from the specified link.
 */
public record ReaderResponse(String link, String data) {}
