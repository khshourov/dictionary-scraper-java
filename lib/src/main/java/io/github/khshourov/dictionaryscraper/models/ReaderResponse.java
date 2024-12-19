package io.github.khshourov.dictionaryscraper.models;

/**
 * Represents the response from a reader after fetching a resource or content.
 *
 * <p>This class is a record that contains the following fields: - `link`: The URL or source link
 * where the content was retrieved. - `data`: The actual content or data retrieved from the
 * specified link.
 *
 * <p>It encapsulates the result of a read operation, providing both the source of the data and the
 * data itself. This record can be used to handle responses from different types of readers.
 */
public record ReaderResponse(String link, String data) {}
