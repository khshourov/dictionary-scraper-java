package com.github.khshourov.dictionaryscraper.interfaces;

import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;

public interface Scraper {
  default void setReader(Reader reader) {}

  DictionaryEntry scrape(String word);
}
