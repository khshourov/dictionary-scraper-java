package com.github.khshourov.dictionaryscraper.models;

import com.github.khshourov.dictionaryscraper.interfaces.Source;

public record DictionaryWord(Source source, String searchWord, DictionaryEntry entry) {}
