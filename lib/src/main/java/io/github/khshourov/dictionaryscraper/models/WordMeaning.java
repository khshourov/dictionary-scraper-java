package io.github.khshourov.dictionaryscraper.models;

import java.util.List;

/**
 * Represents the meaning(s) of a word categorized by grammatical or contextual usage.
 *
 * <p>Each WordMeaning instance consists of a category or multiple categories (concatenated with
 * comma) that a word falls under and a collection of entries, where each entry provides a specific
 * meaning along with relevant examples.
 *
 * @param categories Represents the grammatical or contextual categories a word belongs to, such as
 *     noun, verb, or specific usage like "exclamation".
 * @param entries A list of {@link CategoryMeaningEntry}, where each entry contains a meaning and
 *     one or more examples illustrating the usage of the word in the given context.
 */
public record WordMeaning(String categories, List<CategoryMeaningEntry> entries) {}
