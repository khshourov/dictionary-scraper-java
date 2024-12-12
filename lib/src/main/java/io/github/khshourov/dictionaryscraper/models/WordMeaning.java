package io.github.khshourov.dictionaryscraper.models;

import java.util.List;

public record WordMeaning(String categories, List<CategoryMeaningEntry> entries) {}
