package io.github.khshourov.dictionaryscraper.interfaces;

import io.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import java.io.IOException;

/**
 * The {@code Scraper} interface provides a generic blueprint for implementing dictionary scrapers
 * capable of retrieving and processing word information such as definitions, pronunciations, and
 * other associated metadata.
 *
 * <p>Implementing classes should define the mechanisms for interacting with external data sources
 * to fetch dictionary entries.
 *
 * <p>Primary Features: 1. Registration with a {@code DictionaryScraper} to associate the scraper
 * with a dictionary source. 2. Scraping and processing word data to obtain structured dictionary
 * entries. 3. Optional customization of data retrieval and input cleaning functionality.
 */
public interface Scraper {
  /**
   * Registers this Scraper with the DictionaryScraper.
   *
   * @param dictionaryScraper the DictionaryScraper instance to register the scraper with
   */
  void register(DictionaryScraper dictionaryScraper);

  /**
   * Sets the {@code Reader} instance to be used by the scraper for fetching pronunciation/meaning.
   *
   * @param reader the {@code Reader} instance to be set
   */
  default void setReader(Reader reader) {}

  /**
   * Scrapes dictionary data for a given word.
   *
   * @param word the word to be scraped;
   * @return a {@code DictionaryEntry} containing source links, IPA (pronunciation) listings, and
   *     meanings of the word, or {@code null} if no valid data is retrieved
   * @throws IOException if there is an error during data fetching operations
   */
  DictionaryEntry scrape(String word) throws IOException;

  /**
   * Cleans the input word.
   *
   * @param word the input word that needs to be cleaned; may be null
   * @return the cleaned version of the word
   */
  default String cleanWord(String word) {
    return word;
  }
}
