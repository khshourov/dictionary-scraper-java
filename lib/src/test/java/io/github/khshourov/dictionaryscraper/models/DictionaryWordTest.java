package io.github.khshourov.dictionaryscraper.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import io.github.khshourov.dictionaryscraper.enums.BaseSource;
import io.github.khshourov.dictionaryscraper.enums.Region;
import io.github.khshourov.dictionaryscraper.interfaces.Source;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DictionaryWordTest {
  private static final Source source = BaseSource.CAMBRIDGE;
  private static final String SEARCH_WORD = "word";
  private static final DictionaryEntry dictionaryEntryWithNoExample =
      new DictionaryEntry(
          List.of("http://example.com/pronunciation/word", "http://example.com/meaning/word"),
          Map.of(
              Region.US,
              List.of(
                  new IpaInfo(
                      "",
                      "/heˈloʊ/",
                      "https://dictionary.cambridge.org/media/english/us_pron/h/hel/hello/hello.mp3"))),
          List.of());
  private static final DictionaryEntry dictionaryEntryWithExamples =
      new DictionaryEntry(
          List.of("http://example.com/pronunciation/word", "http://example.com/meaning/word"),
          Map.of(
              Region.US,
              List.of(
                  new IpaInfo(
                      "",
                      "/heˈloʊ/",
                      "https://dictionary.cambridge.org/media/english/us_pron/h/hel/hello/hello.mp3"))),
          List.of(
              new WordMeaning(
                  "noun",
                  List.of(
                      new CategoryMeaningEntry(
                          "something that you are given, without asking for it, "
                              + "on a special occasion, especially to show friendship, "
                              + "or to say thank you:",
                          List.of(
                              "a birthday/Christmas/wedding present",
                              "They gave me theatre tickets as a present."))))));

  @ParameterizedTest
  @MethodSource("dictionaryWordInstances")
  void instanceCanBeSerializedToJson(DictionaryWord word, String expected) {
    assertEquals(expected, word.toJson());
  }

  static Stream<Arguments> dictionaryWordInstances() {
    return Stream.of(
        arguments(
            new DictionaryWord(source, SEARCH_WORD, dictionaryEntryWithNoExample),
            "{\"source\":\"CAMBRIDGE\",\"searchWord\":\"word\",\"entry\":{\"sourceLinks\":[\"http://example.com/pronunciation/word\",\"http://example.com/meaning/word\"],\"ipaListings\":{\"US\":[{\"category\":\"\",\"ipa\":\"/heˈloʊ/\",\"audio\":\"https://dictionary.cambridge.org/media/english/us_pron/h/hel/hello/hello.mp3\"}]},\"meanings\":[]}}"),
        arguments(
            new DictionaryWord(source, SEARCH_WORD, dictionaryEntryWithExamples),
            "{\"source\":\"CAMBRIDGE\",\"searchWord\":\"word\",\"entry\":{\"sourceLinks\":[\"http://example.com/pronunciation/word\",\"http://example.com/meaning/word\"],\"ipaListings\":{\"US\":[{\"category\":\"\",\"ipa\":\"/heˈloʊ/\",\"audio\":\"https://dictionary.cambridge.org/media/english/us_pron/h/hel/hello/hello.mp3\"}]},\"meanings\":[{\"categories\":\"noun\",\"entries\":[{\"meaning\":\"something that you are given, without asking for it, on a special occasion, especially to show friendship, or to say thank you:\",\"examples\":[\"a birthday/Christmas/wedding present\",\"They gave me theatre tickets as a present.\"]}]}]}}"));
  }
}
