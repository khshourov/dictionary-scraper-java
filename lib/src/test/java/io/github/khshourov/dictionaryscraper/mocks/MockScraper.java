package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import io.github.khshourov.dictionaryscraper.interfaces.Scraper;
import io.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import java.io.IOException;

public class MockScraper implements Scraper {
  private MockSource source;
  private DictionaryEntry dictionaryEntry;

  @Override
  public void register(DictionaryScraper dictionaryScraper) {
    dictionaryScraper.registerScraper(this.source, this);
  }

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
