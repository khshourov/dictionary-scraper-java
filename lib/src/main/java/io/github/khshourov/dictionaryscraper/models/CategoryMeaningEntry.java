package io.github.khshourov.dictionaryscraper.models;

import java.util.List;

/**
 * Represents an entry for a specific meaning of a word within a particular category.
 *
 * <p>This record defines a single meaning of a word along with relevant examples to illustrate its
 * contextual usage.
 *
 * <p>Fields: - `meaning`: A textual representation of the specific meaning or definition of the
 * word. - `examples`: A list of example sentences or phrases that demonstrate the word's usage in
 * the given context.
 */
public record CategoryMeaningEntry(String meaning, List<String> examples) {}
