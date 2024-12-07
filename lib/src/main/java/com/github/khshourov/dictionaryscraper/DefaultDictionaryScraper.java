package com.github.khshourov.dictionaryscraper;

import com.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import com.github.khshourov.dictionaryscraper.interfaces.Scraper;
import com.github.khshourov.dictionaryscraper.interfaces.Source;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryWord;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultDictionaryScraper implements DictionaryScraper {
  private final Map<Source, Scraper> scrapers = new HashMap<>();
  private final List<Source> sources = new ArrayList<>();

  public DefaultDictionaryScraper() {
    try (ScanResult scanResult =
        new ClassGraph()
            .enableClassInfo()
            .acceptPackages("com.github.khshourov.dictionaryscraper.scrapers")
            .scan()) {
      List<Class<?>> classes =
          scanResult.getClassesImplementing(Scraper.class.getName()).loadClasses();

      classes.forEach(
          cls -> {
            try {
              Optional<Constructor<?>> constructor =
                  Arrays.stream(cls.getDeclaredConstructors()).findFirst();
              if (constructor.isPresent()) {
                Scraper scraper = (Scraper) constructor.get().newInstance();
                scraper.register(this);
              }
            } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
              throw new RuntimeException(e);
            }
          });
    }
  }

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
