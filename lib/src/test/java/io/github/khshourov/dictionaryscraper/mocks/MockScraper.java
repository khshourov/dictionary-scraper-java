package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import io.github.khshourov.dictionaryscraper.interfaces.Scraper;
import io.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import java.io.IOException;

/**
 * MockScraper is a mock implementation of the {@code Scraper} interface, intended for testing
 * purposes. It simulates the behavior of a dictionary scraper by providing predefined dictionary
 * entries.
 *
 * <p>Features: - Supports registration with a {@code DictionaryScraper}, associating this scraper
 * with a specific mock source. - Provides a simulated dictionary entry retrieval for a specified
 * word. - Allows configuration of the mock source and the predefined dictionary entry for testing
 * scenarios.
 */
public class MockScraper implements Scraper {
  private MockSource source;
  private DictionaryEntry dictionaryEntry;

  /**
   * Registers this mock scraper instance with the provided {@code DictionaryScraper}. The
   * registration associates this scraper with its configured {@code MockSource}, allowing the
   * dictionary scraper to utilize this mock scraper for the designated source.
   *
   * @param dictionaryScraper the instance of {@code DictionaryScraper} with which this scraper will
   *     be registered.
   */
  @Override
  public void register(DictionaryScraper dictionaryScraper) {
    dictionaryScraper.registerScraper(this.source, this);
  }

  /**
   * Simulates scraping and retrieving a predefined dictionary entry for the specified word.
   *
   * @param word the word to retrieve the dictionary entry for
   * @return a {@code DictionaryEntry} object, which contains the predefined data for the specified
   *     word
   * @throws IOException if an error occurs during the scraping process
   */
  @Override
  public DictionaryEntry scrape(String word) throws IOException {
    return this.dictionaryEntry;
  }

  public void setSource(MockSource source) {
    this.source = source;
  }

  public void setDictionaryEntry(DictionaryEntry dictionaryEntry) {
    this.dictionaryEntry = dictionaryEntry;
  }
}
