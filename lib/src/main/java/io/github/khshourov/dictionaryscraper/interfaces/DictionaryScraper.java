package io.github.khshourov.dictionaryscraper.interfaces;

import io.github.khshourov.dictionaryscraper.models.DictionaryWord;
import java.util.List;

/**
 * Provides a framework for managing and utilizing scrapers to retrieve dictionary information from
 * various sources. The DictionaryScraper interface defines methods to register scrapers, access
 * registered sources, and search for words using specific scrapers.
 *
 * <p>Primary Responsibilities: 1. Maintain a registry of dictionary sources and their associated
 * scrapers. 2. Facilitate the search of words through registered scrapers for a specific source. 3.
 * Provide access to the list of sources registered within the framework.
 *
 * <p>Methods: - `getRegisteredSources`: Retrieves a list of all sources currently registered with
 * the scraper. - `registerScraper`: Associates a scraper with a specific source within the
 * framework. - `search`: Performs a word search in a specific source using the associated scraper.
 *
 * <p>This interface can be implemented to define custom behaviors for managing scrapers and
 * sources, as well as specific logic for handling word lookup operations.
 */
public interface DictionaryScraper {
  /**
   * Retrieves a list of all sources currently registered with.
   *
   * @return a list of registered {@link Source} objects.
   */
  default List<Source> getRegisteredSources() {
    return List.of();
  }

  /**
   * Associates a scraper with a specific source within the framework.
   *
   * @param source the source for which the scraper is registered; must not be null
   * @param scraper the scraper to register for the specified source; must not be null
   * @throws IllegalArgumentException if the source or scraper is null
   */
  void registerScraper(Source source, Scraper scraper);

  /**
   * Searches for a word in the specified source using a registered scraper.
   *
   * @param word the word to search for
   * @param source the source from which the word is retrieved
   * @return a {@link DictionaryWord} containing the search results, or {@code null} if the word
   *     cannot be found or an error occurs
   * @throws IllegalArgumentException if no scraper is registered for the specified source
   */
  DictionaryWord search(String word, Source source);
}
