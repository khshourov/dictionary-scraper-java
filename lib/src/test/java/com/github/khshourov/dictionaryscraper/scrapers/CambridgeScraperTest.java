package com.github.khshourov.dictionaryscraper.scrapers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import com.github.khshourov.dictionaryscraper.interfaces.Reader;
import com.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CambridgeScraperTest {
  private CambridgeScraper scraper;

  @BeforeEach
  void init() {
    scraper = new CambridgeScraper();
  }

  @Nested
  class WhenSetReader {
    @Test
    void readerCanNotBeNull() {
      Exception exception = assertThrows(NullPointerException.class, () -> scraper.setReader(null));
      assertEquals("reader can not be null", exception.getMessage());
    }

    @Test
    void baseUriCanNotBeNull() {
      Exception exception =
          assertThrows(
              IllegalStateException.class,
              () ->
                  scraper.setReader(
                      new Reader() {
                        @Override
                        public ReaderResponse read(String word, ReadingPurpose purpose)
                            throws IOException {
                          return null;
                        }
                      }));
      assertEquals("reader.baseUri can not be null or empty", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidBaseUri")
    void readerShouldHaveValidBaseUri(String invalidBaseUri) {
      Exception exception =
          assertThrows(
              IllegalStateException.class,
              () ->
                  scraper.setReader(
                      new Reader() {
                        {
                          this.baseUri = invalidBaseUri;
                        }

                        @Override
                        public ReaderResponse read(String word, ReadingPurpose purpose)
                            throws IOException {
                          return null;
                        }
                      }));
      assertEquals("reader.baseUri can not be null or empty", exception.getMessage());
    }

    static Stream<Arguments> invalidBaseUri() {
      return Stream.of(arguments(""), arguments(" "), arguments("  "));
    }
  }
}
