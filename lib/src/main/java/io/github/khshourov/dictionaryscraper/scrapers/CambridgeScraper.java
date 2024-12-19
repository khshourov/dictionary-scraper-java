package io.github.khshourov.dictionaryscraper.scrapers;

import io.github.khshourov.dictionaryscraper.enums.BaseSource;
import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.enums.Region;
import io.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import io.github.khshourov.dictionaryscraper.interfaces.Reader;
import io.github.khshourov.dictionaryscraper.interfaces.Scraper;
import io.github.khshourov.dictionaryscraper.models.CategoryMeaningEntry;
import io.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import io.github.khshourov.dictionaryscraper.models.IpaInfo;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import io.github.khshourov.dictionaryscraper.models.WordMeaning;
import io.github.khshourov.dictionaryscraper.readers.CambridgeReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * The CambridgeScraper class is an implementation of the Scraper interface that scrapes data from
 * the Cambridge Dictionary website. It primarily extracts word pronunciations, meanings, and
 * associated data, organizing it into a structured DictionaryEntry format for further use.
 */
public class CambridgeScraper implements Scraper {
  private Reader reader;

  /** Initializes with default {@link CambridgeReader} instance. */
  public CambridgeScraper() {
    this.reader = new CambridgeReader("https://dictionary.cambridge.org");
  }

  /**
   * Registers the CambridgeScraper with the provided DictionaryScraper under the
   * BaseSource.CAMBRIDGE source.
   *
   * @param dictionaryScraper the DictionaryScraper instance to register the scraper with
   */
  @Override
  public void register(DictionaryScraper dictionaryScraper) {
    dictionaryScraper.registerScraper(BaseSource.CAMBRIDGE, this);
  }

  /**
   * Sets the {@code Reader} instance to be used by the scraper. The {@code Reader} is responsible
   * for handling the data fetching and must be properly configured before being set. The method
   * ensures that the provided {@code Reader} is not null and its properties are correctly
   * initialized.
   *
   * @param reader the {@code Reader} instance to be set; must not be {@code null} and must have a
   *     valid {@code baseUri}.
   * @throws NullPointerException if the provided {@code reader} is {@code null}.
   * @throws IllegalStateException if the {@code reader}'s {@code baseUri} is null or empty.
   */
  @Override
  public void setReader(Reader reader) {
    if (reader == null) {
      throw new NullPointerException("reader can not be null");
    }

    reader.afterPropertiesSet();

    this.reader = reader;
  }

  /**
   * Scrapes dictionary data for a given word. The process involves obtaining the word's
   * pronunciation and meaning by interacting with a reader. If pronunciation data is successfully
   * retrieved but meaning data cannot be fetched, the method will still proceed with the
   * pronunciation data alone. Ensures the input word is cleaned and valid before processing.
   *
   * @param word the word to be scraped; must be a non-null, non-empty string containing alphabetic
   *     characters only after cleaning
   * @return a {@code DictionaryEntry} containing source links, IPA (pronunciation) listings, and
   *     meanings of the word, or {@code null} if no valid data is retrieved
   * @throws NullPointerException if the reader is not initialized
   * @throws IllegalArgumentException if the cleaned word is empty or invalid
   * @throws IOException if there is an error during data fetching operations
   */
  @Override
  public DictionaryEntry scrape(String word) throws IOException {
    if (this.reader == null) {
      throw new NullPointerException("reader can not be null");
    }

    String cleanedWord = this.cleanWord(word);
    if (cleanedWord.isEmpty()) {
      throw new IllegalArgumentException(String.format("`%s` is not valid word", word));
    }

    ReaderResponse response = this.reader.read(cleanedWord, ReadingPurpose.PRONUNCIATION);
    if (response == null) {
      return null;
    }

    List<String> sourceLinks = new ArrayList<>();
    sourceLinks.add(response.link());

    Map<Region, List<IpaInfo>> ipaListings = this.extractIpaListings(response.data());
    if (ipaListings.isEmpty()) {
      return null;
    }

    try {
      response = this.reader.read(cleanedWord, ReadingPurpose.MEANING);
    } catch (IOException ignored) {
      // Pronunciation is the main priority. So, even if we can't read
      // the "meaning" doc, we can still proceed.
      response = null;
    }

    List<WordMeaning> meanings = new ArrayList<>();
    if (response != null) {
      sourceLinks.add(response.link());
      meanings = this.extractMeanings(response.data());
    }

    return new DictionaryEntry(sourceLinks, ipaListings, meanings);
  }

  /**
   * Cleans the input word by removing all non-alphabetic characters and converting it to lowercase.
   * If the input word is null, an empty string is returned.
   *
   * @param word the input word that needs to be cleaned; may be null
   * @return the cleaned version of the word containing only lowercase alphabetic characters, or an
   *     empty string if the input is null
   */
  @Override
  public String cleanWord(String word) {
    if (word == null) {
      return "";
    }

    return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
  }

  private Map<Region, List<IpaInfo>> extractIpaListings(String data) {
    Map<Region, List<IpaInfo>> ipaListings = new EnumMap<>(Region.class);

    Document document = Jsoup.parse(data);
    document
        .body()
        .select(".pron-block")
        .forEach(
            pronunciationBlock -> {
              List<String> partsOfSpeeches = this.extractPartsOfSpeeches(pronunciationBlock);
              List<RegionWiseIpaInfo> regionWiseIpaInfo =
                  this.extractRegionWiseIpaInfo(pronunciationBlock);

              for (RegionWiseIpaInfo ipaInfo : regionWiseIpaInfo) {
                for (String partsOfSpeech : partsOfSpeeches) {
                  Region region = Region.valueOf(ipaInfo.region.toUpperCase());

                  if (!ipaListings.containsKey(region)) {
                    ipaListings.put(region, new ArrayList<>());
                  }

                  ipaListings
                      .get(region)
                      .add(
                          new IpaInfo(
                              partsOfSpeech,
                              ipaInfo.ipa,
                              String.format("%s%s", this.reader.getBaseUri(), ipaInfo.audioLink)));
                }
              }
            });

    return ipaListings;
  }

  private List<WordMeaning> extractMeanings(String data) {
    Document document = Jsoup.parse(data);

    // Selecting UK dictionary section; currently we're ignoring American and business English
    Element ukDictionary = document.body().selectFirst(".pr.dictionary .di-body");
    if (ukDictionary == null) {
      throw new IllegalArgumentException("No UK dictionary section found");
    }

    return ukDictionary.select(".pr.entry-body__el").stream().map(this::extractCategory).toList();
  }

  private List<String> extractPartsOfSpeeches(Element pronunciationBlock) {
    List<String> partsOfSpeeches =
        pronunciationBlock.select(".posgram > .ti").stream()
            .map((element -> Optional.of(element.text()).orElse("")))
            .toList();
    if (partsOfSpeeches.isEmpty()) {
      return List.of("");
    }

    return partsOfSpeeches;
  }

  private List<RegionWiseIpaInfo> extractRegionWiseIpaInfo(Element pronunciationBlock) {
    return pronunciationBlock.select(".region-block .pron-info").stream()
        .map(
            regionBlock -> {
              Optional<String> region = Optional.of(regionBlock.attr("data-pron-region"));
              Optional<String> ipa =
                  Optional.ofNullable(
                          regionBlock.selectFirst(".pron[data-title=\"Written pronunciation\"]"))
                      .map(Element::text);
              Optional<String> audioLink =
                  Optional.ofNullable(
                          regionBlock.selectFirst(".soundfile audio source[type=\"audio/mpeg\"]"))
                      .map((element -> element.attr("src")));

              if (ipa.isPresent() && audioLink.isPresent()) {
                return new RegionWiseIpaInfo(
                    region.get().toLowerCase(), ipa.get(), audioLink.get());
              }

              return null;
            })
        .filter(Objects::nonNull)
        .toList();
  }

  private WordMeaning extractCategory(Element categoryBlock) {
    return new WordMeaning(
        Optional.ofNullable(categoryBlock.selectFirst(".pos-header .posgram"))
            .map(Element::text)
            .orElse(""),
        categoryBlock.select(".pos-body .pr.dsense .def-block").stream()
            .map((this::extractMeaning))
            .toList());
  }

  private CategoryMeaningEntry extractMeaning(Element meaningBlock) {
    return new CategoryMeaningEntry(
        Optional.ofNullable(meaningBlock.selectFirst(".ddef_h .def"))
            .map(Element::text)
            .map(String::trim)
            .orElse(""),
        meaningBlock.select(".def-body .examp").stream()
            .map((element -> element.text().trim()))
            .toList());
  }

  private record RegionWiseIpaInfo(String region, String ipa, String audioLink) {}
}
