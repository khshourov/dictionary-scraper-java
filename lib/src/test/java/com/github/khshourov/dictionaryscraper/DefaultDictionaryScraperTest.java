package com.github.khshourov.dictionaryscraper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.khshourov.dictionaryscraper.enums.BaseSource;
import com.github.khshourov.dictionaryscraper.enums.Region;
import com.github.khshourov.dictionaryscraper.mocks.MockScraper;
import com.github.khshourov.dictionaryscraper.mocks.MockSource;
import com.github.khshourov.dictionaryscraper.models.CategoryMeaningEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryWord;
import com.github.khshourov.dictionaryscraper.models.IpaInfo;
import com.github.khshourov.dictionaryscraper.models.WordMeaning;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DefaultDictionaryScraperTest {
  private DefaultDictionaryScraper dictionaryScraper;

  @BeforeEach
  void init() {
    dictionaryScraper = new DefaultDictionaryScraper();
  }

  @Test
  void scraperClassesUnderScrapersPackageShouldBeLoadedOnInitializationTime() {
    assertEquals(1, this.dictionaryScraper.getRegisteredSources().size());
    assertEquals(BaseSource.CAMBRIDGE, this.dictionaryScraper.getRegisteredSources().get(0));
  }

  @Nested
  class WhenRegisterScraper {
    @Test
    void scraperCanBeManuallyRegistered() {
      MockScraper scraper = new MockScraper();

      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_1, scraper);

      assertEquals(2, dictionaryScraper.getRegisteredSources().size());
      assertEquals(BaseSource.CAMBRIDGE, dictionaryScraper.getRegisteredSources().get(0));
      assertEquals(MockSource.MOCK_SOURCE_1, dictionaryScraper.getRegisteredSources().get(1));
    }

    @Test
    void sourceCanNotBeNull() {
      MockScraper scraper = new MockScraper();

      Exception exception =
          assertThrows(
              IllegalArgumentException.class,
              () -> dictionaryScraper.registerScraper(null, scraper));
      assertEquals("source can not be null", exception.getMessage());
    }

    @Test
    void scraperCanNotBeNull() {
      Exception exception =
          assertThrows(
              IllegalArgumentException.class,
              () -> dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_1, null));
      assertEquals("scraper can not be null", exception.getMessage());
    }
  }

  @Nested
  class WhenGetRegisteredSources {
    @Test
    void emptyListShouldReturnIfNoScraperIsRegistered() {
      assertEquals(1, dictionaryScraper.getRegisteredSources().size());
    }

    @Test
    void sourcesListShouldMatchTheRegisteredScrapers() {
      MockScraper mockScraper1 = new MockScraper();

      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_1, mockScraper1);

      assertEquals(2, dictionaryScraper.getRegisteredSources().size());
      assertEquals(BaseSource.CAMBRIDGE, dictionaryScraper.getRegisteredSources().get(0));
      assertEquals(MockSource.MOCK_SOURCE_1, dictionaryScraper.getRegisteredSources().get(1));

      MockScraper mockScraper2 = new MockScraper();

      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_2, mockScraper2);

      assertEquals(3, dictionaryScraper.getRegisteredSources().size());
      assertEquals(BaseSource.CAMBRIDGE, dictionaryScraper.getRegisteredSources().get(0));
      assertEquals(MockSource.MOCK_SOURCE_1, dictionaryScraper.getRegisteredSources().get(1));
      assertEquals(MockSource.MOCK_SOURCE_2, dictionaryScraper.getRegisteredSources().get(2));
    }

    @Test
    void duplicateSourcesShouldNotBeIncludedMultipleTimes() {
      MockScraper mockScraper = new MockScraper();

      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_2, mockScraper);
      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_1, mockScraper);
      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_1, mockScraper);
      dictionaryScraper.registerScraper(MockSource.MOCK_SOURCE_2, mockScraper);

      assertEquals(3, dictionaryScraper.getRegisteredSources().size());
      assertEquals(BaseSource.CAMBRIDGE, dictionaryScraper.getRegisteredSources().get(0));
      assertEquals(MockSource.MOCK_SOURCE_2, dictionaryScraper.getRegisteredSources().get(1));
      assertEquals(MockSource.MOCK_SOURCE_1, dictionaryScraper.getRegisteredSources().get(2));
    }
  }

  @Nested
  class WhenSearch {
    private final String searchWord = "hello";

    @Test
    void sourceHasToBeRegistered() {
      Exception exception =
          assertThrows(
              IllegalArgumentException.class,
              () -> dictionaryScraper.search(searchWord, MockSource.MOCK_SOURCE_1));
      assertEquals(
          "No scraper has been registered for this source: " + MockSource.MOCK_SOURCE_1.getValue(),
          exception.getMessage());
    }

    @Test
    void nullShouldBeReturnIfWordIsNotFound() {
      String notFoundWord = "not-found-word";
      MockScraper mockScraper = new MockScraper();
      mockScraper.setSource(MockSource.MOCK_SOURCE_1);
      mockScraper.register(dictionaryScraper);
      mockScraper.setDictionaryEntry(null);

      assertNull(dictionaryScraper.search(notFoundWord, MockSource.MOCK_SOURCE_1));
    }

    @Test
    void dictionaryWordShouldBeReturnForValidWord() {
      DictionaryEntry expectedDictionaryEntry =
          new DictionaryEntry(
              List.of(
                  String.format("http://example.com/pronunciation/%s", searchWord),
                  String.format("http://example.com/meaning/%s", searchWord)),
              Map.of(
                  Region.US,
                  List.of(
                      new IpaInfo(
                          "",
                          "/heˈloʊ/",
                          "https://dictionary.cambridge.org/media/english/us_pron/h/hel/hello/hello.mp3")),
                  Region.UK,
                  List.of(
                      new IpaInfo(
                          "",
                          "/heˈləʊ/",
                          "https://dictionary.cambridge.org/media/english/uk_pron/u/ukh/ukhef/ukheft_029.mp3"))),
              List.of(
                  new WordMeaning(
                      "exclamation, noun",
                      List.of(
                          new CategoryMeaningEntry(
                              "something that is said at the beginning of a phone conversation:",
                              List.of(
                                  "\"Hello, I'd like some information about flights to the US, please.\""))))));

      MockScraper mockScraper = new MockScraper();
      mockScraper.setSource(MockSource.MOCK_SOURCE_1);
      mockScraper.register(dictionaryScraper);
      mockScraper.setDictionaryEntry(expectedDictionaryEntry);

      DictionaryWord dictionaryWord =
          dictionaryScraper.search(searchWord, MockSource.MOCK_SOURCE_1);

      assertNotNull(dictionaryWord);
      assertEquals(
          new DictionaryWord(MockSource.MOCK_SOURCE_1, searchWord, expectedDictionaryEntry),
          dictionaryWord);
    }
  }
}
