package com.github.khshourov.dictionaryscraper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.github.khshourov.dictionaryscraper.enums.BaseSource;
import com.github.khshourov.dictionaryscraper.enums.Region;
import com.github.khshourov.dictionaryscraper.models.CategoryMeaningEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryWord;
import com.github.khshourov.dictionaryscraper.models.IpaInfo;
import com.github.khshourov.dictionaryscraper.models.WordMeaning;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultDictionaryScraperTest {
  private static final String sourceDomain = "https://dictionary.cambridge.org";
  private DefaultDictionaryScraper dictionaryScraper;

  @BeforeEach
  void init() {
    dictionaryScraper = new DefaultDictionaryScraper();
  }

  @Test
  void searchShouldReturnExpectedDataForValidSingleCategoryWord() throws IOException {
    String validSingleCategoryWord = "hello";

    DictionaryEntry expectedDictionaryEntry =
        new DictionaryEntry(
            List.of(
                String.format("%s/pronunciation/english/%s", sourceDomain, validSingleCategoryWord),
                String.format("%s/dictionary/english/%s", sourceDomain, validSingleCategoryWord)),
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

    DictionaryWord dictionaryWord =
        dictionaryScraper.search(validSingleCategoryWord, BaseSource.CAMBRIDGE);

    assertNotNull(dictionaryWord);

    assertEquals(BaseSource.CAMBRIDGE, dictionaryWord.source());
    assertEquals(validSingleCategoryWord, dictionaryWord.searchWord());
    assertEquals(expectedDictionaryEntry.sourceLinks(), dictionaryWord.entry().sourceLinks());
    assertEquals(expectedDictionaryEntry.ipaListings(), dictionaryWord.entry().ipaListings());
    assertEquals(expectedDictionaryEntry.meanings(), dictionaryWord.entry().meanings());
  }

  @Test
  void searchShouldReturnExpectedDataForMultiCategoryWord() throws IOException {
    String validMultiCategoryWord = "present";
    DictionaryEntry expectedDictionaryEntry =
        new DictionaryEntry(
            List.of(
                String.format("%s/pronunciation/english/%s", sourceDomain, validMultiCategoryWord),
                String.format("%s/dictionary/english/%s", sourceDomain, validMultiCategoryWord)),
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

    DictionaryWord dictionaryWord =
        dictionaryScraper.search(validMultiCategoryWord, BaseSource.CAMBRIDGE);

    assertNotNull(dictionaryWord);

    assertEquals(BaseSource.CAMBRIDGE, dictionaryWord.source());
    assertEquals(validMultiCategoryWord, dictionaryWord.searchWord());
    assertEquals(expectedDictionaryEntry.sourceLinks(), dictionaryWord.entry().sourceLinks());
    assertEquals(expectedDictionaryEntry.ipaListings(), dictionaryWord.entry().ipaListings());
    assertEquals(expectedDictionaryEntry.meanings(), dictionaryWord.entry().meanings());
  }

  @Test
  void searchShouldReturnNullForNonsensicalWord() throws IOException {
    String nonsensicalWord = "prisencolinensinainciusol";

    DictionaryWord dictionaryWord = dictionaryScraper.search(nonsensicalWord, BaseSource.CAMBRIDGE);

    assertNull(dictionaryWord);
  }
}
