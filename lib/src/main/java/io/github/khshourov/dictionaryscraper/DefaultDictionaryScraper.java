package io.github.khshourov.dictionaryscraper;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import io.github.khshourov.dictionaryscraper.interfaces.Scraper;
import io.github.khshourov.dictionaryscraper.interfaces.Source;
import io.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import io.github.khshourov.dictionaryscraper.models.DictionaryWord;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DefaultDictionaryScraper is a concrete implementation of the DictionaryScraper interface. It
 * facilitates the registration of scrapers and the retrieval of dictionary entries for a given word
 * and source.
 *
 * <p>This class automatically detects and registers all scraper implementations in the
 * "io.github.khshourov.dictionaryscraper.scrapers" package using ClassGraph. Scrapers must
 * implement the Scraper interface and define a method to register themselves with this
 * DefaultDictionaryScraper instance.
 *
 * <p>Core responsibilities include: - Dynamically detecting and initializing available Scraper
 * implementations. - Managing registered sources and their associated scrapers. - Handling
 * dictionary lookups by utilizing registered scrapers.
 *
 * <p>The class maintains a mapping of sources to associated scrapers, and ensures that only one
 * scraper is registered per source. Additionally, scrapers can only be used if they have been
 * registered to the relevant source.
 */
public class DefaultDictionaryScraper implements DictionaryScraper {
  private final Map<Source, Scraper> scrapers = new HashMap<>();
  private final List<Source> sources = new ArrayList<>();

  /**
   * Initializes a new instance of the {@code DefaultDictionaryScraper} class. This constructor
   * automatically scans and loads all classes within the
   * "io.github.khshourov.dictionaryscraper.scrapers" package that implement the {@code Scraper}
   * interface. For each identified class, an instance of the scraper is created and registered with
   * the current {@code DefaultDictionaryScraper} instance.
   *
   * <p>The initialization process involves the following: - Scans the specified package for classes
   * implementing the {@code Scraper} interface. - Instantiates each scraper using its first
   * declared constructor. - Registers instantiated scrapers with the current dictionary scraper via
   * their {@code register} method.
   *
   * <p>This constructor leverages the ClassGraph library for classpath scanning and dynamic
   * loading. Any errors encountered during scraper instantiation or registration result in an
   * {@link IllegalStateException}.
   *
   * @throws IllegalStateException if an error occurs during scraper instantiation or registration.
   */
  public DefaultDictionaryScraper() {
    try (ScanResult scanResult =
        new ClassGraph()
            .enableClassInfo()
            .acceptPackages("io.github.khshourov.dictionaryscraper.scrapers")
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
              throw new IllegalStateException(e);
            }
          });
    }
  }

  /**
   * Retrieves the list of sources that have been registered with the dictionary scraper.
   *
   * @return a list of registered {@link Source} objects.
   */
  @Override
  public List<Source> getRegisteredSources() {
    return this.sources;
  }

  /**
   * Registers a scraper for a specific source. If the source is not already registered, it will be
   * added to the list of sources. The scraper is mapped to the provided source.
   *
   * @param source the source for which the scraper is registered; must not be null
   * @param scraper the scraper to register for the specified source; must not be null
   * @throws IllegalArgumentException if the source or scraper is null
   */
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

  /**
   * Searches for a word in the specified source using a registered scraper. If the source does not
   * have a registered scraper, an {@link IllegalArgumentException} is thrown. If the word cannot be
   * found or an exception occurs during scraping, {@code null} is returned.
   *
   * @param word the word to search for
   * @param source the source from which the word is retrieved
   * @return a {@link DictionaryWord} containing the search results, or {@code null} if the word
   *     cannot be found or an error occurs
   * @throws IllegalArgumentException if no scraper is registered for the specified source
   */
  @Override
  public DictionaryWord search(String word, Source source) {
    if (!this.scrapers.containsKey(source)) {
      throw new IllegalArgumentException(
          "No scraper has been registered for this source: " + source);
    }

    try {
      DictionaryEntry dictionaryEntry = this.scrapers.get(source).scrape(word);
      if (dictionaryEntry == null) {
        return null;
      }

      return new DictionaryWord(source, word, dictionaryEntry);
    } catch (IOException ignored) {
      return null;
    }
  }
}
