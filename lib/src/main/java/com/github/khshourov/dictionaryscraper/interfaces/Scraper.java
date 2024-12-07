package com.github.khshourov.dictionaryscraper.interfaces;

import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import java.io.IOException;

public interface Scraper {
  default void setReader(Reader reader) {}

  DictionaryEntry scrape(String word) throws IOException;

  default String cleanWord(String word) {
    return word;
  }
}
