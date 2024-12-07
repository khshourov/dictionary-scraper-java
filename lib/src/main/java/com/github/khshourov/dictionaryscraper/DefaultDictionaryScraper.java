package com.github.khshourov.dictionaryscraper;

import com.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import com.github.khshourov.dictionaryscraper.interfaces.Scraper;
import com.github.khshourov.dictionaryscraper.interfaces.Source;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryWord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultDictionaryScraper implements DictionaryScraper {
  private final Map<Source, Scraper> scrapers = new HashMap<>();
  private final List<Source> sources = new ArrayList<>();

  @Override
  public List<Source> getRegisteredSources() {
    return this.sources;
  }

  @Override
  public void registerScraper(Source source, Scraper scraper) {
    if (source == null) {
      throw new IllegalArgumentException("source can not be null");
    }

    if (scraper == null) {
      throw new IllegalArgumentException("scraper can not be null");
    }

    if (!this.scrapers.containsKey(source)) {
      this.sources.add(source);
    }
    this.scrapers.put(source, scraper);
  }

  @Override
  public DictionaryWord search(String word, Source source) {
    if (!this.scrapers.containsKey(source)) {
      throw new IllegalArgumentException(
          "No scraper has been registered for this source: " + source.getValue());
    }

    try {
      DictionaryEntry dictionaryEntry = this.scrapers.get(source).scrape(word);
      if (dictionaryEntry == null) {
        return null;
      }

      return new DictionaryWord(source, word, dictionaryEntry);
    } catch (IOException ignored) {
    }

    return null;
  }
}
