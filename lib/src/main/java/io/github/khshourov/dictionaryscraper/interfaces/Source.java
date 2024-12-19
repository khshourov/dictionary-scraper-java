package io.github.khshourov.dictionaryscraper.interfaces;

/**
 * Represents a source that can be used within the dictionary scraper framework. A source provides a
 * unique identifier that is used as a reference in the registration, retrieval, and management of
 * scraping operations.
 *
 * <p>Sources are used in conjunction with other components, such as scrapers, to facilitate the
 * retrieval of data from specific dictionaries or regions.
 *
 * <p>Implementations of this interface should define how the identifier is obtained. The identifier
 * represents an online dictionary source.
 *
 * <p>Methods: - getValue: Retrieves the unique identifier associated with the source.
 */
public interface Source {
  /**
   * Retrieves the unique identifier associated with the source.
   *
   * @return the identifier of the source
   */
  String getValue();
}
