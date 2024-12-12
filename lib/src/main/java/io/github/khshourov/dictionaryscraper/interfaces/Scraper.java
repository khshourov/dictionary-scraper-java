package io.github.khshourov.dictionaryscraper.interfaces;

import io.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import java.io.IOException;

public interface Scraper {
  void register(DictionaryScraper dictionaryScraper);

  default void setReader(Reader reader) {}

  DictionaryEntry scrape(String word) throws IOException;

  default String cleanWord(String word) {
    return word;
  }
}
