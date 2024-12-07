package com.github.khshourov.dictionaryscraper.scrapers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import com.github.khshourov.dictionaryscraper.enums.Region;
import com.github.khshourov.dictionaryscraper.interfaces.Reader;
import com.github.khshourov.dictionaryscraper.mocks.MockCambridgeReader;
import com.github.khshourov.dictionaryscraper.mocks.TimeoutReader;
import com.github.khshourov.dictionaryscraper.models.CategoryMeaningEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import com.github.khshourov.dictionaryscraper.models.IpaInfo;
import com.github.khshourov.dictionaryscraper.models.ReaderResponse;
import com.github.khshourov.dictionaryscraper.models.WordMeaning;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CambridgeScraperTest {
  private CambridgeScraper scraper;

  @BeforeEach
  void init() {
    scraper = new CambridgeScraper();
  }

  @Nested
  class WhenScrape {
    private static final String sourceDomain = "https://dictionary.cambridge.org";
    private static final String validSingleCategoryWord = "hello";

    @BeforeEach
    void init() {
      scraper.setReader(new MockCambridgeReader(sourceDomain));
    }

    @Test
    void afterCleaningWordShouldNotBeEmpty() {
      String invalidWord = "123 456";
      Exception exception =
          assertThrows(IllegalArgumentException.class, () -> scraper.scrape(invalidWord));
      assertEquals(String.format("`%s` is not valid word", invalidWord), exception.getMessage());
    }

    @Test
    void scrapeShouldReturnExpectedDataForValidSingleCategoryWord() throws IOException {
      DictionaryEntry expectedDictionaryEntry =
          new DictionaryEntry(
              List.of(
                  String.format("%s/pronunciation/%s", sourceDomain, validSingleCategoryWord),
                  String.format("%s/meaning/%s", sourceDomain, validSingleCategoryWord)),
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
                              "used when meeting or greeting someone:",
                              List.of(
                                  "Hello, Paul. I haven't seen you for ages.",
                                  "I know her vaguely - we've exchanged hellos a few times.",
                                  "say hello I just thought I'd call by and say hello.",
                                  "a big hello And a big hello (= welcome) to all the parents who've come to see the show.")),
                          new CategoryMeaningEntry(
                              "something that is said at the beginning of a phone conversation:",
                              List.of(
                                  "\"Hello, I'd like some information about flights to the US, please.\"")),
                          new CategoryMeaningEntry(
                              "something that is said to attract someone's attention:",
                              List.of(
                                  "The front door was open so she walked inside and called out, \"Hello! Is there anybody in?\"")),
                          new CategoryMeaningEntry(
                              "said to someone who has just said or done something stupid, especially something that shows they are not noticing what is happening:",
                              List.of(
                                  "She asked me if I'd just arrived and I was like \"Hello, I've been here for an hour.\"")),
                          new CategoryMeaningEntry(
                              "an expression of surprise:",
                              List.of("Hello, this is very strange - I know that man."))))));

      DictionaryEntry actualDictionaryEntry = scraper.scrape(validSingleCategoryWord);

      assertNotNull(actualDictionaryEntry);

      // We can assertEquals(expectedDictionaryEntry, actualDictionaryEntry) and that would
      // also work but because the object is so big, it's difficult to understand where the
      // problem lies.
      assertEquals(expectedDictionaryEntry.sourceLinks(), actualDictionaryEntry.sourceLinks());
      assertEquals(expectedDictionaryEntry.ipaListings(), actualDictionaryEntry.ipaListings());
      assertEquals(expectedDictionaryEntry.meanings(), actualDictionaryEntry.meanings());
    }

    @Test
    void scrapeShouldReturnExpectedDataForSingleRegion() throws IOException {
      DictionaryEntry expectedDictionaryEntry =
          new DictionaryEntry(
              List.of(
                  String.format("%s/pronunciation/%sus", sourceDomain, validSingleCategoryWord),
                  String.format("%s/meaning/%sus", sourceDomain, validSingleCategoryWord)),
              Map.of(
                  Region.US,
                  List.of(
                      new IpaInfo(
                          "",
                          "/heˈloʊ/",
                          "https://dictionary.cambridge.org/media/english/us_pron/h/hel/hello/hello.mp3"))),
              List.of());

      DictionaryEntry actualDictionaryEntry = scraper.scrape(validSingleCategoryWord + "-us");

      assertNotNull(actualDictionaryEntry);

      assertEquals(expectedDictionaryEntry.sourceLinks(), actualDictionaryEntry.sourceLinks());
      assertEquals(expectedDictionaryEntry.ipaListings(), actualDictionaryEntry.ipaListings());
      assertEquals(expectedDictionaryEntry.meanings(), actualDictionaryEntry.meanings());
    }

    @Test
    void scrapeShouldReturnExpectedDataForMultiCategoryWord() throws IOException {
      String validMultiCategoryWord = "present";
      DictionaryEntry expectedDictionaryEntry =
          new DictionaryEntry(
              List.of(
                  String.format("%s/pronunciation/%s", sourceDomain, validMultiCategoryWord),
                  String.format("%s/meaning/%s", sourceDomain, validMultiCategoryWord)),
              Map.of(
                  Region.US,
                  List.of(
                      new IpaInfo(
                          "noun",
                          "/ˈprez.ənt/",
                          "https://dictionary.cambridge.org/media/english/us_pron/p/pre/prese/present_01_00.mp3"),
                      new IpaInfo(
                          "adjective",
                          "/ˈprez.ənt/",
                          "https://dictionary.cambridge.org/media/english/us_pron/p/pre/prese/present_01_00.mp3"),
                      new IpaInfo(
                          "verb",
                          "/prɪˈzent/",
                          "https://dictionary.cambridge.org/media/english/us_pron/p/pre/prese/present_02_00.mp3")),
                  Region.UK,
                  List.of(
                      new IpaInfo(
                          "noun",
                          "/ˈprez.ənt/",
                          "https://dictionary.cambridge.org/media/english/uk_pron/u/ukp/ukpre/ukprepo020.mp3"),
                      new IpaInfo(
                          "adjective",
                          "/ˈprez.ənt/",
                          "https://dictionary.cambridge.org/media/english/uk_pron/u/ukp/ukpre/ukprepo020.mp3"),
                      new IpaInfo(
                          "verb",
                          "/prɪˈzent/",
                          "https://dictionary.cambridge.org/media/english/uk_pron/u/ukp/ukpre/ukprepo021.mp3"))),
              List.of(
                  new WordMeaning(
                      "noun",
                      List.of(
                          new CategoryMeaningEntry(
                              "something that you are given, without asking for it, on a special occasion, especially to show friendship, or to say thank you:",
                              List.of(
                                  "a birthday/Christmas/wedding present",
                                  "They gave me theatre tickets as a present.")),
                          new CategoryMeaningEntry(
                              "the period of time that is happening now, not the past or the future:",
                              List.of(
                                  "That's all for the present.",
                                  "in the present The play is set in the present.")),
                          new CategoryMeaningEntry(
                              "the form of the verb that is used to show what happens or exists now:",
                              List.of(
                                  "in the present The verb in this sentence is in the present.",
                                  "His English is basic, and he always speaks in the present.",
                                  "It is interesting that she uses the present, not the past, when talking about her relationship with Brad.")),
                          new CategoryMeaningEntry(
                              "now:", List.of("\"Are you busy?\" \"Not at present.\"")))),
                  new WordMeaning(
                      "adjective",
                      List.of(
                          new CategoryMeaningEntry(
                              "in a particular place:",
                              List.of(
                                  "The whole family was present.",
                                  "There were no children present.")),
                          new CategoryMeaningEntry(
                              "happening or existing now:",
                              List.of(
                                  "I don't have her present address.",
                                  "Please state your present occupation and salary.")))),
                  new WordMeaning(
                      "verb",
                      List.of(
                          new CategoryMeaningEntry(
                              "to give, provide, or make something known:",
                              List.of(
                                  "be presented with The winners were presented with medals.",
                                  "present someone with a problem The letter presented the family with a problem that would be difficult to solve.",
                                  "present someone with something The documentary presented us with a balanced view of the issue.",
                                  "present something to someone He presented the report to his colleagues at the meeting.",
                                  "The classroom presented a cheerful busy atmosphere to the visitors (= appeared to them to have this).",
                                  "The school is presenting (= performing) \"West Side Story\" as its end-of-term production.")),
                          new CategoryMeaningEntry(
                              "to introduce a television or radio show:",
                              List.of("She presents the late-night news.")),
                          new CategoryMeaningEntry(
                              "to introduce a person:",
                              List.of(
                                  "May I present Professor Carter?",
                                  "present someone to someone Later on I'd like to present you to the headteacher.")),
                          new CategoryMeaningEntry(
                              "to arrive somewhere and introduce yourself:",
                              List.of(
                                  "He presented himself at the doctor's at 9.30 a.m. as arranged.",
                                  "You should present yourselves at the front desk at 8 o'clock in the morning.",
                                  "He presented himself to the Criminal Tribunal in The Hague.",
                                  "She presented herself at the abbey to begin her life as a nun.",
                                  "I presented myself to the supervisor, eager to begin work.")),
                          new CategoryMeaningEntry(
                              "If something presents itself, it happens:",
                              List.of(
                                  "An opportunity suddenly presented itself.",
                                  "When a challenge to his authority presented itself, he dealt with it firmly.",
                                  "She opted for cash over comfort whenever the choice presented itself.",
                                  "I would be open to taking up a directorship should the right opportunity present itself.",
                                  "After a couple of successful years, a new hurdle presented itself.")),
                          new CategoryMeaningEntry(
                              "to show or describe someone or something in a particular way:",
                              List.of(
                                  "present someone/something as something EU leaders presented the agreement as a victory for all.",
                                  "He presented himself as someone who knows his rights.",
                                  "Make sure you present your complaint in a way that sounds constructive, not critical.")),
                          new CategoryMeaningEntry(
                              "to see a doctor, etc. when showing particular signs of an illness or medical condition :",
                              List.of(
                                  "present with Children presenting with acute respiratory infections were referred for a chest X-ray.",
                                  "present with When patients present with chest pain, clinical examination and patient history are fundamental to determine the probable cause of pain.")),
                          new CategoryMeaningEntry(
                              "(of an illness) to show itself in a particular way:",
                              List.of(
                                  "Medical conditions might present differently during pregnancy.",
                                  "present as Bone cancer generally presents as pain in the area of the tumour."))))));

      DictionaryEntry actualDictionaryEntry = scraper.scrape(validMultiCategoryWord);

      assertNotNull(actualDictionaryEntry);

      assertEquals(expectedDictionaryEntry.sourceLinks(), actualDictionaryEntry.sourceLinks());
      assertEquals(expectedDictionaryEntry.ipaListings(), actualDictionaryEntry.ipaListings());
      assertEquals(expectedDictionaryEntry.meanings(), actualDictionaryEntry.meanings());
    }

    @Test
    void scrapeShouldReturnNullForNonsensicalWord() throws IOException {
      String nonsensicalWord = "prisencolinensinainciusol";

      DictionaryEntry dictionaryEntry = scraper.scrape(nonsensicalWord);

      assertNull(dictionaryEntry);
    }

    @Test
    void timeoutShouldThrowException() {
      scraper.setReader(new TimeoutReader("timeout/reader"));

      Exception exception =
          assertThrows(IOException.class, () -> scraper.scrape(validSingleCategoryWord));
      assertEquals("Read timeout", exception.getMessage());
    }
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

  @Nested
  class WhenCleanWord {
    @Test
    void nullWordShouldConvertToEmptyString() {
      assertEquals("", scraper.cleanWord(null));
    }

    @ParameterizedTest
    @MethodSource("words")
    void cleanShouldRetainJustLetters(String givenWord, String expected) {
      assertEquals(expected, scraper.cleanWord(givenWord));
    }

    static Stream<Arguments> words() {
      return Stream.of(
          arguments("Abc", "abc"),
          arguments("Abc's word", "abcsword"),
          arguments("1love!", "love"),
          arguments("Amélie", "amlie"));
    }
  }
}
