package io.github.khshourov.dictionaryscraper.models;

import java.util.List;

/**
 * Represents an entry for a specific meaning of a word within a particular category.
 *
 * @param meaning A textual representation of the specific meaning or definition of the word.
 * @param examples A list of example sentences or phrases that demonstrate the word's usage in the
 *     given context.
 */
public record CategoryMeaningEntry(String meaning, List<String> examples) {}
