package com.github.khshourov.dictionaryscraper.scrapers;

import com.github.khshourov.dictionaryscraper.interfaces.Reader;
import com.github.khshourov.dictionaryscraper.interfaces.Scraper;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;

public class CambridgeScraper implements Scraper {
  @Override
  public void setReader(Reader reader) {
    if (reader == null) {
      throw new NullPointerException("reader can not be null");
    }

    reader.afterPropertiesSet();
  }

  @Override
  public DictionaryEntry scrape(String word) {
    return null;
  }
}
