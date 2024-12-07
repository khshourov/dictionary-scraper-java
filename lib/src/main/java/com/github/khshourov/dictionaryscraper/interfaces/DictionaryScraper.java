package com.github.khshourov.dictionaryscraper.interfaces;

import com.github.khshourov.dictionaryscraper.models.DictionaryWord;
import java.util.List;

public interface DictionaryScraper {
  default List<Source> getRegisteredSources() {
    return List.of();
  }

  void registerScraper(Source source, Scraper scraper);

  DictionaryWord search(String word, Source source);
}
