package com.github.khshourov.dictionaryscraper.models;

import com.github.khshourov.dictionaryscraper.enums.Source;

public record DictionaryWord(
    Source source, String[] sourceLinks, String name, DictionaryEntry entry) {}
