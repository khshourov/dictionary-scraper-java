package com.github.khshourov.dictionaryscraper.interfaces;

import com.github.khshourov.dictionaryscraper.enums.Source;
import com.github.khshourov.dictionaryscraper.models.DictionaryWord;

public interface DictionaryScraper {
  void registerScraper(Source source, Scraper scraper);

  DictionaryWord search(String word, Source source);
}
