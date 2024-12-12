package io.github.khshourov.dictionaryscraper.models;

import io.github.khshourov.dictionaryscraper.enums.Region;
import java.util.List;
import java.util.Map;

public record DictionaryEntry(
    List<String> sourceLinks, Map<Region, List<IpaInfo>> ipaListings, List<WordMeaning> meanings) {}
