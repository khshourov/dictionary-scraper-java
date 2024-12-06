package com.github.khshourov.dictionaryscraper.models;

import java.util.List;
import java.util.Map;

public record DictionaryEntry(
    List<String> sourceLinks, Map<String, List<IpaInfo>> ipaListings, List<WordMeaning> meanings) {}
