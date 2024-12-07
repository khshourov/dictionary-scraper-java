package com.github.khshourov.dictionaryscraper.scrapers;

import com.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import com.github.khshourov.dictionaryscraper.enums.Region;
import com.github.khshourov.dictionaryscraper.enums.Source;
import com.github.khshourov.dictionaryscraper.interfaces.DictionaryScraper;
import com.github.khshourov.dictionaryscraper.interfaces.Reader;
import com.github.khshourov.dictionaryscraper.interfaces.Scraper;
import com.github.khshourov.dictionaryscraper.models.CategoryMeaningEntry;
import com.github.khshourov.dictionaryscraper.models.DictionaryEntry;
import com.github.khshourov.dictionaryscraper.models.IpaInfo;
import com.github.khshourov.dictionaryscraper.models.ReaderResponse;
import com.github.khshourov.dictionaryscraper.models.WordMeaning;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CambridgeScraper implements Scraper {
  private Reader reader;

  @Override
  public void register(DictionaryScraper dictionaryScraper) {
    dictionaryScraper.registerScraper(Source.CAMBRIDGE, this);
  }

  @Override
  public void setReader(Reader reader) {
    if (reader == null) {
      throw new NullPointerException("reader can not be null");
    }

    reader.afterPropertiesSet();

    this.reader = reader;
  }

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
    }

    List<WordMeaning> meanings = new ArrayList<>();
    if (response != null) {
      sourceLinks.add(response.link());
      meanings = this.extractMeanings(response.data());
    }

    return new DictionaryEntry(sourceLinks, ipaListings, meanings);
  }

  @Override
  public String cleanWord(String word) {
    if (word == null) {
      return "";
    }

    return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
  }

  private Map<Region, List<IpaInfo>> extractIpaListings(String data) {
    Map<Region, List<IpaInfo>> ipaListings = new HashMap<>();

    Document document = Jsoup.parse(data);
    document
        .body()
        .select(".pron-block")
        .forEach(
            (pronunciationBlock) -> {
              List<String> partsOfSpeeches = this.extractPartsOfSpeeches(pronunciationBlock);
              List<RegionWiseIpaInfo> regionWiseIpaInfo =
                  this.extractRegionWiseIpaInfo(pronunciationBlock);

              for (RegionWiseIpaInfo ipaInfo : regionWiseIpaInfo) {
                for (String partsOfSpeech : partsOfSpeeches) {
                  Region region = Region.fromValue(ipaInfo.region);

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
            (regionBlock) -> {
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
